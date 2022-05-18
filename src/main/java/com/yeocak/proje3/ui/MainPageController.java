package com.yeocak.proje3.ui;

import com.yeocak.proje3.utils.UserInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainPageController {
    @FXML
    private Label lblUserName;

    @FXML
    private Label lblBottomText;


    @FXML
    private void initialize(){
        if(UserInfo.currentAccount == null){
            lblBottomText.setText("Şuanda bir hesabınız yok.\nİşlem yapabilmek için önce hesap oluşturun.");
        }
        String userName = UserInfo.currentUser.getName();
        lblUserName.setText(userName);
    }
}
