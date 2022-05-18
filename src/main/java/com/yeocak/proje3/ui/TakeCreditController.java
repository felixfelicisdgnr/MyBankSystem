package com.yeocak.proje3.ui;

import com.yeocak.proje3.db.BalanceRepository;
import com.yeocak.proje3.model.Log;
import com.yeocak.proje3.model.Operation;
import com.yeocak.proje3.utils.BankInfo;
import com.yeocak.proje3.utils.UserInfo;
import com.yeocak.proje3.utils.Utils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Objects;

public class TakeCreditController {

    @FXML
    private Button btnSubmit;

    @FXML
    private ComboBox<String> comboSelections;

    @FXML
    private TextField edtAmount;

    @FXML
    private Label lblNoCredit;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblYesCredit;

    @FXML
    private void initialize() {
        if (!Objects.equals(UserInfo.currentAccount.getExchange(), "tl") ||
                BalanceRepository.hasCreditLog(UserInfo.currentAccount.getAccId())) {
            btnSubmit.setVisible(false);
            edtAmount.setVisible(false);
            lblNoCredit.setVisible(false);
            lblYesCredit.setVisible(false);
            comboSelections.setVisible(false);
            lblTitle.setText("Kredi çekebilmek için TL hesabınızda \nolmalısınız ve aktif krediniz olmamalıdır!");

            return;
        }

        String[] numbers = {"3", "12", "24", "36"};

        comboSelections.setItems(FXCollections
                .observableArrayList(numbers));
    }

    @FXML
    private void onClickKrediCek() {
        if (comboSelections.getValue() == null || edtAmount.getText().isBlank()) {
            return;
        }

        int debt = Integer.parseInt(edtAmount.getText());
        double interest = BankInfo.interest / 100;

        double newDebt = interest * (double) debt + debt;

        Utils.addTakeCredit(
                UserInfo.currentAccount.getAccId(),
                (int) newDebt,
                Integer.parseInt(comboSelections.getValue())
        );
        BalanceRepository.setBalance(UserInfo.currentAccount.getAccId(), UserInfo.currentAccount.getBalance() + Integer.parseInt(edtAmount.getText()));
        UserInfo.currentAccount.updateAccount();

        Log newLog = new Log(
                "BANKA",
                UserInfo.currentUser.getTc(),
                Operation.KREDI_CEKME,
                Integer.parseInt(edtAmount.getText()),
                -1,
                UserInfo.currentAccount.getBalance(),
                Utils.formatDate(BankInfo.currentMonth, BankInfo.currentYear)
        );

        BalanceRepository.createLog(newLog);

        BalanceRepository.addBankBalance(-Integer.parseInt(edtAmount.getText()));
    }
}
