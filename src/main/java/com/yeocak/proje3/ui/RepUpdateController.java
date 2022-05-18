package com.yeocak.proje3.ui;

import com.yeocak.proje3.db.BalanceRepository;
import com.yeocak.proje3.model.User;
import com.yeocak.proje3.utils.UserInfo;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class RepUpdateController {

    @FXML
    private TextField edtAddress;

    @FXML
    private TextField edtEmail;

    @FXML
    private TextField edtName;

    @FXML
    private TextField edtPassword;

    @FXML
    private TextField edtPhoneNumber;

    @FXML
    private TextField edtTc;

    @FXML
    private ListView<User> lvUsers;

    private User currentUser;

    @FXML
    private void initialize() {
        ArrayList<User> userList = BalanceRepository.getUserViaRepTc(UserInfo.currentUser.getTc());

        lvUsers.setCellFactory(param -> new ListCell<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        lvUsers.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        currentUser = newValue;
                        updateAllInfo(newValue);
                    }
                }
        );

        if (userList != null) {
            lvUsers.getItems().addAll(userList);
        }
    }

    private void updateAllInfo(User user) {
        edtName.setText(user.getName());
        edtAddress.setText(user.getAddress());
        edtEmail.setText(user.getEmail());
        edtPassword.setText(user.getPassword());
        edtTc.setText(user.getTc());
        edtPhoneNumber.setText(user.getPhone());
    }

    @FXML
    private void onClickGuncelle() {
        if (currentUser != null) {
            User newUser = new User(
                    edtName.getText(),
                    edtPhoneNumber.getText(),
                    edtTc.getText(),
                    edtAddress.getText(),
                    edtEmail.getText(),
                    edtPassword.getText(),
                    currentUser.getRole(),
                    currentUser.getRep_tc()
            );
            BalanceRepository.updateUser(currentUser, newUser);
        }
    }

}
