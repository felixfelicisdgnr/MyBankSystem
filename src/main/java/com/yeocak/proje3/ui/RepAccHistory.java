package com.yeocak.proje3.ui;

import com.yeocak.proje3.db.BalanceRepository;
import com.yeocak.proje3.model.Account;
import com.yeocak.proje3.model.Log;
import com.yeocak.proje3.model.Operation;
import com.yeocak.proje3.model.User;
import com.yeocak.proje3.utils.UserInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class RepAccHistory {

    @FXML
    private TableView<Log> table;
    @FXML
    private TableColumn<Log, Integer> tableLogId;
    @FXML
    private TableColumn<Log, String> tableSourceTc;
    @FXML
    private TableColumn<Log, String> tableTargetTc;
    @FXML
    private TableColumn<Log, Operation> tableOperation;
    @FXML
    private TableColumn<Log, Integer> tableAmount;
    @FXML
    private TableColumn<Log, Integer> tableSourceBalance;
    @FXML
    private TableColumn<Log, Integer> tableTargetBalance;
    @FXML
    private TableColumn<Log, String> tableDate;

    private final ObservableList<Log> logList= FXCollections.observableArrayList();
    @FXML
    private ListView<User> lvCostumerName;

    @FXML
    private void initialize(){
        ArrayList<User> userList = BalanceRepository.getUserViaRepTc(UserInfo.currentUser.getTc());

        lvCostumerName.setCellFactory(param -> new ListCell<User>() {
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

        lvCostumerName.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        updateTable(newValue);
                    }
                }
        );

        if (userList != null) {
            lvCostumerName.getItems().addAll(userList);
        }
    }
    
    private void updateTable(User user){
        tableLogId.setCellValueFactory(new PropertyValueFactory<Log,Integer>("logId"));
        tableSourceTc.setCellValueFactory(new PropertyValueFactory<Log,String>("sourceTc"));
        tableTargetTc.setCellValueFactory(new PropertyValueFactory<Log,String>("targetTc"));
        tableOperation.setCellValueFactory(new PropertyValueFactory<Log,Operation>("operation"));
        tableAmount.setCellValueFactory(new PropertyValueFactory<Log,Integer>("amount"));
        tableSourceBalance.setCellValueFactory(new PropertyValueFactory<Log,Integer>("sourceBalance"));
        tableTargetBalance.setCellValueFactory(new PropertyValueFactory<Log,Integer>("targetBalance"));
        tableDate.setCellValueFactory(new PropertyValueFactory<Log,String>("date"));

        ArrayList<Log> newList = BalanceRepository.getLogs(user.getTc());
        if(newList != null){
            logList.clear();
            logList.addAll(newList);
        }

        table.setItems(logList);
    }

}
