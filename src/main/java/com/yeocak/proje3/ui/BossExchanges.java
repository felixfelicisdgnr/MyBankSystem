package com.yeocak.proje3.ui;

import com.yeocak.proje3.db.BalanceRepository;
import com.yeocak.proje3.model.Account;
import com.yeocak.proje3.model.Exchange;
import com.yeocak.proje3.utils.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class BossExchanges {

    @FXML
    private TextField edtAddName;

    @FXML
    private TextField edtAddRatio;

    @FXML
    private TextField edtExchangeName;

    @FXML
    private TextField edtExchangeRatio;

    @FXML
    private ListView<Exchange> lvExchanges;

    @FXML
    private void onClickKaydet() {
        if(edtExchangeName.getText().isBlank() || edtExchangeRatio.getText().isBlank()) return;

        Exchange updated = new Exchange(edtExchangeName.getText(), Double.parseDouble(edtExchangeRatio.getText()));
        BalanceRepository.updateExchange(updated);

        updateList();
    }

    @FXML
    private void onClickCikar() {
        if(edtExchangeName.getText().isBlank() || edtExchangeRatio.getText().isBlank()) return;

        Exchange updated = new Exchange(edtExchangeName.getText(), Double.parseDouble(edtExchangeRatio.getText()));
        BalanceRepository.deleteExchange(updated);

        updateList();
    }

    @FXML
    private void onClickEkle() {
        if(edtAddName.getText().isBlank() || edtAddRatio.getText().isBlank()) return;

        Exchange updated = new Exchange(edtAddName.getText(), Double.parseDouble(edtAddRatio.getText()));
        BalanceRepository.addNewExchange(updated);

        updateList();
    }

    @FXML
    private void initialize() {
        lvExchanges.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Exchange item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        lvExchanges.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        updateFields(newValue);
                    }
                }
        );

        updateList();
    }

    private void updateList(){
        ArrayList<Exchange> accList = BalanceRepository.getAllExchanges();
        if (accList != null) {
            lvExchanges.getItems().clear();
            lvExchanges.getItems().addAll(accList);
        }
    }

    private void updateFields(Exchange exchange){
        edtExchangeName.setText(exchange.getName());
        edtExchangeRatio.setText(String.valueOf(exchange.getRatio()));
    }
}
