package com.yeocak.proje3.ui;

import com.yeocak.proje3.db.BalanceRepository;
import com.yeocak.proje3.model.Log;
import com.yeocak.proje3.model.Operation;
import com.yeocak.proje3.utils.BankInfo;
import com.yeocak.proje3.utils.UserInfo;
import com.yeocak.proje3.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MoneyTransferController {
    @FXML
    private TextField lblWithdraw;
    @FXML
    private Label lblBalance;

    @FXML
    private TextField lblDeposit;

    @FXML
    private void onClickYatir() {
        if (Integer.parseInt(lblDeposit.getText()) <= 0) return;
        BalanceRepository.setBalance(UserInfo.currentAccount.getAccId(),
                UserInfo.currentAccount.getBalance() + Integer.parseInt(lblDeposit.getText()));
        updateBalance();

        Log newLog = new Log(
                UserInfo.currentUser.getTc(),
                "BANKA",
                Operation.PARA_YATIRMA,
                Integer.parseInt(lblDeposit.getText()),
                UserInfo.currentAccount.getBalance(),
                -1,
                Utils.formatDate(BankInfo.currentMonth, BankInfo.currentYear)
        );

        BalanceRepository.createLog(newLog);

        BalanceRepository.addBankBalance(Integer.parseInt(lblDeposit.getText()));
    }

    @FXML
    private void onClickCek() {
        if (Integer.parseInt(lblWithdraw.getText()) <= 0) return;
        if (Integer.parseInt(lblWithdraw.getText()) > UserInfo.currentAccount.getBalance()) return;
        BalanceRepository.setBalance(UserInfo.currentAccount.getAccId(),
                UserInfo.currentAccount.getBalance() - Integer.parseInt(lblWithdraw.getText()));
        updateBalance();

        Log newLog = new Log(
                "BANKA",
                UserInfo.currentUser.getTc(),
                Operation.PARA_CEKME,
                Integer.parseInt(lblWithdraw.getText()),
                -1,
                UserInfo.currentAccount.getBalance(),
                Utils.formatDate(BankInfo.currentMonth, BankInfo.currentYear)
        );

        BalanceRepository.createLog(newLog);

        BalanceRepository.addBankBalance(-Integer.parseInt(lblDeposit.getText()));
    }

    private void updateBalance() {
        UserInfo.currentAccount.updateAccount();
        lblBalance.setText(String.valueOf(UserInfo.currentAccount.getBalance()));
    }

    @FXML
    private void initialize() {
        updateBalance();
    }
}
