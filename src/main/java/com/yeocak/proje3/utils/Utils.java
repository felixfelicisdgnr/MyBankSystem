package com.yeocak.proje3.utils;

import com.yeocak.proje3.db.BalanceRepository;
import com.yeocak.proje3.model.Months;
import com.yeocak.proje3.ui.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import static com.yeocak.proje3.db.AuthRepository.statement;

public class Utils {
    public static void changePage(Region anyItem, String destination) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Utils.class.getResource(destination)));
            Stage stage = (Stage) anyItem.getScene().getWindow();

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void changePaneCenter(BorderPane borderPane, String destination) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Utils.class.getResource(destination)));
            borderPane.setCenter(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Boolean addTakeCredit(int accId, int amount, int howLong) {
        int singleCredit = (int) (Double.parseDouble(String.valueOf(amount)) / howLong);
        Months nowMonth = BankInfo.currentMonth;
        int nowYear = BankInfo.currentYear;
        for(int a = 0; a < howLong; a++){
            BalanceRepository.addTakeCredit(accId, singleCredit, nowMonth, nowYear);
            if(nowMonth == Months.ARALIK){
                nowYear += 1;
            }
            nowMonth = nowMonth.getNextMonthWithMod();
        }
        return true;
    }

    public static String formatDate(Months month, int year){
        return month.toString() + " " + year;
    }
}
