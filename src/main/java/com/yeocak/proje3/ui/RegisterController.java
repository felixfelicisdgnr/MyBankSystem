package com.yeocak.proje3.ui;

import com.yeocak.proje3.utils.Utils;
import com.yeocak.proje3.db.AuthRepository;
import com.yeocak.proje3.model.Role;
import com.yeocak.proje3.model.User;
import com.yeocak.proje3.utils.validators.StringValidators;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Objects;

public class RegisterController {
    public TextField tfName;
    public TextField tfTc;
    public TextField tfPhoneNumber;
    public TextField tfAddress;
    public TextField tfEmail;
    public PasswordField tfPassword;
    public ComboBox<String> choiceRole;

    public static String fromWhere = "login"; // login - boss

    @FXML
    private void initialize(){
        ArrayList<String> roles = new ArrayList<>();
        roles.add(Role.MUSTERI.name());
        roles.add(Role.TEMSILCI.name());
        roles.add(Role.MUDUR.name());
        choiceRole.getItems().addAll(roles);
        choiceRole.setValue("MUSTERI");

        if(fromWhere.equals("login")){
            choiceRole.setVisible(false);
        }
    }
    public void onClickRegister() {
        if (checkFields()) {
            addUser();
            Utils.changePage(tfName, "/com/yeocak/proje3/login.fxml");
        }
    }

    private Boolean checkFields() {
        return choiceRole.getValue() != null && !StringValidators.validateAnyBlank(
                tfName.getText(),
                tfTc.getText(),
                tfPhoneNumber.getText(),
                tfAddress.getText(),
                tfEmail.getText(),
                tfPassword.getText()
        );
    }

    private void addUser() {
        User newUser = new User(
                tfName.getText(),
                tfPhoneNumber.getText(),
                tfTc.getText(),
                tfAddress.getText(),
                tfEmail.getText(),
                tfPassword.getText(),
                Role.MUSTERI,
                null
        );

        String repTc = AuthRepository.getTcOfLeastRep();

        newUser.setRep_tc(repTc);

        AuthRepository.addUser(newUser);
    }
}
