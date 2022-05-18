package com.yeocak.proje3.ui;

import com.yeocak.proje3.db.AuthRepository;
import com.yeocak.proje3.db.BalanceRepository;
import com.yeocak.proje3.model.Account;
import com.yeocak.proje3.model.Log;
import com.yeocak.proje3.model.Operation;
import com.yeocak.proje3.utils.BankInfo;
import com.yeocak.proje3.utils.UserInfo;
import com.yeocak.proje3.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SendMoneyController {
    @FXML
    private Label lblSuccessful;
    @FXML
    private TextField edtpeopleTc;

    @FXML
    private TextField edtaccountId;

    @FXML
    private TextField edtamountSend;

    @FXML
    private void onClickGonder() {
        Account target = BalanceRepository.getAccount(Integer.parseInt(edtaccountId.getText()));
        if (target != null && target.getTc().equals(edtpeopleTc.getText())) {
            boolean result = BalanceRepository.sendMoneyBetweenAccounts(
                    UserInfo.currentAccount,
                    target,
                    Integer.parseInt(edtamountSend.getText())
            );

            lblSuccessful.setVisible(result);

            Log newLog = new Log(

                    UserInfo.currentUser.getTc(),
                    target.getTc(),
                    Operation.PARA_GONDERME,
                    Integer.parseInt(edtamountSend.getText()),
                    UserInfo.currentAccount.getBalance(),
                    target.getBalance(),
                    Utils.formatDate(BankInfo.currentMonth, BankInfo.currentYear)
            );

            BalanceRepository.createLog(newLog);
        }
    }

    @FXML
    private void onTextFieldsChanged(){
        lblSuccessful.setVisible(false);
    }
}
