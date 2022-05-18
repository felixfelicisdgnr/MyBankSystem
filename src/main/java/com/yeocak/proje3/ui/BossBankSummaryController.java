package com.yeocak.proje3.ui;

import com.yeocak.proje3.db.BalanceRepository;
import com.yeocak.proje3.model.Log;
import com.yeocak.proje3.model.Operation;
import com.yeocak.proje3.utils.UserInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class BossBankSummaryController {
    @FXML
    private TextField edtTableCount;
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

    ObservableList<Log> list = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        tableLogId.setCellValueFactory(new PropertyValueFactory<Log, Integer>("logId"));
        tableSourceTc.setCellValueFactory(new PropertyValueFactory<Log, String>("sourceTc"));
        tableTargetTc.setCellValueFactory(new PropertyValueFactory<Log, String>("targetTc"));
        tableOperation.setCellValueFactory(new PropertyValueFactory<Log, Operation>("operation"));
        tableAmount.setCellValueFactory(new PropertyValueFactory<Log, Integer>("amount"));
        tableSourceBalance.setCellValueFactory(new PropertyValueFactory<Log, Integer>("sourceBalance"));
        tableTargetBalance.setCellValueFactory(new PropertyValueFactory<Log, Integer>("targetBalance"));
        tableDate.setCellValueFactory(new PropertyValueFactory<Log, String>("date"));

        edtTableCount.textProperty().addListener((observable, oldValue, newValue) -> {
            updateTable(Integer.parseInt(newValue));
        });
    }

    private void updateTable(int howMany) {
        ArrayList<Log> newList = BalanceRepository.getAllLogs(howMany);
        list.clear();
        if (newList != null) {
            list.addAll(newList);
        }

        table.setItems(list);
    }

}
