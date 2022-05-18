package com.yeocak.proje3.ui;

import com.yeocak.proje3.db.BalanceRepository;
import com.yeocak.proje3.model.Account;
import com.yeocak.proje3.utils.UserInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class AccListController {
    @FXML
    private Label lblResult;
    @FXML
    private ListView<Account> lvAcc;
    @FXML
    private Label lblBakiye;
    @FXML
    private Label lblHesapid;

    private Account selectedAcc;

    @FXML
    private void onClickHesapDegistir() {
        UserInfo.currentAccount = selectedAcc;
        updateLabelInfo();
    }

    @FXML
    private void initialize() {
        lvAcc.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Account item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getExchange() + " hesabı");
                    lblResult.setVisible(false);
                }

            }
        });

        lvAcc.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedAcc = newValue;
                    }
                }
        );

        updateListView();
    }

    private void updateLabelInfo() {
        lblBakiye.setText("Bakiye: " + UserInfo.currentAccount.getBalance());
        lblHesapid.setText("Hesap id: " + UserInfo.currentAccount.getAccId());
    }

    private void updateListView(){
        ArrayList<Account> accList = BalanceRepository.getAccounts(UserInfo.currentUser.getTc());

        if (accList != null) {
            lvAcc.getItems().clear();
            lvAcc.getItems().addAll(accList);
        }

        updateLabelInfo();
    }

    @FXML
    private void onClickSil(){
        if(selectedAcc == null) {
            lblResult.setText("Önce bir hesap seçin!");
            lblResult.setVisible(true);
            return;
        }

        if(selectedAcc.getBalance() > 0){
            lblResult.setText("Hesap silmek için bakiyeniz 0 olmalı!");
            lblResult.setVisible(true);
            return;
        }

        BalanceRepository.deleteAccount(UserInfo.currentAccount.getAccId());
        lblBakiye.setText("");
        lblHesapid.setText("");
        lblResult.setText("Silme işlemi başarılı!");
        lblResult.setVisible(true);

        updateListView();
    }
}
