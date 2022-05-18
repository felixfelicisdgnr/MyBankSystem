package com.yeocak.proje3.ui;

import com.yeocak.proje3.db.BalanceRepository;
import com.yeocak.proje3.utils.UserInfo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class AddNewAccountController {
    @FXML
    private Label lblAdded;
    @FXML
    private ListView<String> lvAccountTypes;

    private String selectedItem = null;

    @FXML
    private void initialize() {
        ArrayList<String> exchangeList = BalanceRepository.getAllExchangeNames();
        lvAccountTypes.getItems().addAll(exchangeList);
        selectedItem = exchangeList.get(0);

        lvAccountTypes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    selectedItem = newValue;
                    lblAdded.setVisible(false);

                    if (selectedItem == null) {
                        UserInfo.currentAccount = BalanceRepository.getFirstAccount(UserInfo.currentUser.getTc());
                    }
                }
        );
    }

    @FXML
    private void onClickHesapOlustur() {
        boolean added = BalanceRepository.addNewAccount(UserInfo.currentUser.getTc(), 0, selectedItem);
        lblAdded.setVisible(added);

        if(UserInfo.currentAccount == null){
            UserInfo.currentAccount = BalanceRepository.getFirstAccount(UserInfo.currentUser.getTc());
        }
    }
}
