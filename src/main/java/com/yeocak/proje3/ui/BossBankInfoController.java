package com.yeocak.proje3.ui;

import com.yeocak.proje3.db.BalanceRepository;
import com.yeocak.proje3.utils.BankInfo;
import com.yeocak.proje3.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BossBankInfoController {

    @FXML
    private TextField edtAddInterest;

    @FXML
    private TextField edtInterest;

    @FXML
    private TextField edtWorkerPay;

    @FXML
    private Label lblBankExpense;

    @FXML
    private Label lblBankGain;

    @FXML
    private Label lblBankIncome;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTotalMoney;

    @FXML
    private void initialize() {
        BalanceRepository.getBankInfo();
        lblBankExpense.setText(String.valueOf(BankInfo.expense));
        lblBankGain.setText(String.valueOf(BankInfo.gain));
        lblBankIncome.setText(String.valueOf(BankInfo.income));
        lblTotalMoney.setText(String.valueOf(BankInfo.total));
        lblDate.setText(Utils.formatDate(BankInfo.currentMonth, BankInfo.currentYear));

        edtAddInterest.setText(String.valueOf(BankInfo.addIntereset));
        edtInterest.setText(String.valueOf(BankInfo.interest));
        edtWorkerPay.setText(String.valueOf(BankInfo.workerPay));
    }

    @FXML
    private void onClickKaydet() {
        BalanceRepository.updateBankInfo(Double.parseDouble(edtInterest.getText()),
                Double.parseDouble(edtAddInterest.getText()),
                Integer.parseInt(edtWorkerPay.getText()));
    }

    @FXML
    private void onClickIleriAl(){
        BankInfo.runTheDateOneMonth();

        lblDate.setText(Utils.formatDate(BankInfo.currentMonth, BankInfo.currentYear));
    }
}
