package com.yeocak.proje3.ui;

import com.yeocak.proje3.db.BalanceRepository;
import com.yeocak.proje3.model.Log;
import com.yeocak.proje3.model.Months;
import com.yeocak.proje3.model.Operation;
import com.yeocak.proje3.utils.BankInfo;
import com.yeocak.proje3.utils.UserInfo;
import com.yeocak.proje3.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Objects;

public class PayCreditController {
    @FXML
    private Label lblResult;
    @FXML
    private Button btnPayCredit;

    @FXML
    private TextField edtPayAmount;

    @FXML
    private Label lblAllAmount;

    @FXML
    private Label lblThisMonth;

    @FXML
    private void initialize() {
        edtPayAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                lblResult.setVisible(false);
            }
        });

        updateUI();
    }

    @FXML
    private void onClickKrediOde() {
        if (edtPayAmount.getText().isBlank() || (Double.parseDouble(edtPayAmount.getText()) > UserInfo.currentAccount.getBalance())) {
            resultError("Uygun bir bakiye girin!");
            return;
        }

        double totalDebt = BalanceRepository.getTotalCreditAmount(UserInfo.currentAccount.getAccId());
        double payingCredit = Double.parseDouble(edtPayAmount.getText());
        if (payingCredit >= totalDebt) {
            BalanceRepository.deleteAllCredits(UserInfo.currentAccount.getAccId());
            decreaseAccount(totalDebt);
            return;
        }

        while (true) {
            double lastDebt = BalanceRepository.getLastCreditAmount(UserInfo.currentAccount.getAccId());
            Months lastMonth = BalanceRepository.getLastCreditMonth(UserInfo.currentAccount.getAccId());
            int lastYear = BalanceRepository.getLastCreditYear(UserInfo.currentAccount.getAccId());

            if (lastMonth == null) {
                decreaseAccount(Double.parseDouble(edtPayAmount.getText()) - payingCredit);
                break;
            }

            if (lastDebt > payingCredit) {
                double paying = lastDebt - payingCredit;
                BalanceRepository.setAmountCredit(UserInfo.currentAccount.getAccId(), paying, Objects.requireNonNull(lastMonth), lastYear);
                decreaseAccount(Double.parseDouble(edtPayAmount.getText()));
                break;
            }

            BalanceRepository.deleteCredit(UserInfo.currentAccount.getAccId(), Objects.requireNonNull(lastMonth), lastYear);
            payingCredit -= lastDebt;
        }
    }

    private void decreaseAccount(double amount) {
        updateUI();
        BalanceRepository.setBalance(UserInfo.currentAccount.getAccId(), UserInfo.currentAccount.getBalance() - (int) amount);
        UserInfo.currentAccount.updateAccount();
        BalanceRepository.addBankBalance((int) amount);
        resultSuccess("Başarıyla ödendi!");

        Log newLog = new Log(
                UserInfo.currentUser.getTc(),
                "BANKA",
                Operation.BORC_ODEME,
                (int) amount,
                UserInfo.currentAccount.getBalance(),
                -1,
                Utils.formatDate(BankInfo.currentMonth, BankInfo.currentYear)
        );

        BalanceRepository.createLog(newLog);
    }

    private void updateUI() {
        double lastMonthDebit = BalanceRepository.getLastCreditAmount(UserInfo.currentAccount.getAccId());
        double totalDebit = BalanceRepository.getTotalCreditAmount(UserInfo.currentAccount.getAccId());
        if (lastMonthDebit == -1 || totalDebit == -1) {
            lblThisMonth.setText("0");
            lblAllAmount.setText("0");
        } else {
            lblAllAmount.setText(String.valueOf(totalDebit));

            if (BalanceRepository.getLastCreditMonth(UserInfo.currentAccount.getAccId()) == BankInfo.currentMonth) {
                lblThisMonth.setText(String.valueOf(lastMonthDebit));
            } else {
                lblThisMonth.setText("0");
            }
        }

        edtPayAmount.clear();

        btnPayCredit.setDisable(
                !BalanceRepository.hasCreditLog(
                        UserInfo.currentAccount.getAccId()
                )
        );
    }

    private void resultSuccess(String message) {
        lblResult.setStyle("-fx-text-inner-color: green;");
        lblResult.setText(message);
        lblResult.setVisible(true);
    }

    private void resultError(String message) {
        lblResult.setStyle("-fx-text-inner-color: red;");
        lblResult.setText(message);
        lblResult.setVisible(true);
    }
}