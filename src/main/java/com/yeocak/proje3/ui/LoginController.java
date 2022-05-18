package com.yeocak.proje3.ui;

import com.yeocak.proje3.db.BalanceRepository;
import com.yeocak.proje3.utils.BankInfo;
import com.yeocak.proje3.utils.UserInfo;
import com.yeocak.proje3.utils.Utils;
import com.yeocak.proje3.db.AuthRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private Label ErrorMessage;
    @FXML
    private TextField TextFieldTc;
    @FXML
    private TextField TextFieldPassword;

    @FXML
    protected void onClickRegister() {
        changeSceneToRegister();
    }

    @FXML
    protected void onClickLogin() {
        if(checkIfUserExists()){
            changeSceneToNext();
        } else{
            ErrorMessage.setVisible(true);
        }
    }

    @FXML
    protected void onTextFieldsChanged() {
        ErrorMessage.setVisible(false);
    }

    private boolean checkIfUserExists(){
        return AuthRepository.checkIfUserExists(TextFieldTc.getText(), TextFieldPassword.getText());
    }
    private void changeSceneToRegister(){
        RegisterController.fromWhere = "login";
        Utils.changePage(ErrorMessage, "/com/yeocak/proje3/register.fxml");
    }

    private void changeSceneToNext(){
        UserInfo.currentUser = AuthRepository.getUser(TextFieldTc.getText());
        UserInfo.currentAccount = BalanceRepository.getFirstAccount(UserInfo.currentUser.getTc());
        BankInfo.currentMonth = BalanceRepository.getCurrentMonth();
        BankInfo.currentYear = BalanceRepository.getCurrentYear();
        BankInfo.updateBankInfo();
        Utils.changePage(ErrorMessage, "/com/yeocak/proje3/user_panel.fxml");
    }
}
