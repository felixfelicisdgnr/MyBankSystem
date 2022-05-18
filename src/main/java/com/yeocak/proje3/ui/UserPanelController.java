package com.yeocak.proje3.ui;

import com.yeocak.proje3.model.Role;
import com.yeocak.proje3.utils.UserInfo;
import com.yeocak.proje3.utils.Utils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class UserPanelController {
    @FXML
    private MenuItem submenuBoss1;
    @FXML
    private MenuItem submenuBoss2;
    @FXML
    private MenuItem submenuBoss3;
    @FXML
    private MenuItem submenuBoss4;
    @FXML
    private MenuItem submenuRep1;
    @FXML
    private MenuItem submenuRep2;
    @FXML
    private Menu menuAuthorized;
    @FXML
    private BorderPane layoutPane;


    @FXML
    private void initialize() {
        Utils.changePaneCenter(layoutPane, "/com/yeocak/proje3/main_page.fxml");

        Boolean isBoss = (UserInfo.currentUser.getRole() == Role.MUDUR);
        Boolean isRep = (UserInfo.currentUser.getRole() == Role.TEMSILCI);

        menuAuthorized.setVisible(isBoss || isRep);
        if(isBoss || isRep){
            setSubmenus(isBoss);
        }
    }

    private void setSubmenus(Boolean isBoss){
        submenuBoss1.setVisible(isBoss);
        submenuBoss2.setVisible(isBoss);
        submenuBoss3.setVisible(isBoss);
        submenuBoss4.setVisible(isBoss);

        submenuRep1.setVisible(!isBoss);
        submenuRep2.setVisible(!isBoss);
    }

    @FXML
    private void onClickAnaSayfa() {
        Utils.changePaneCenter(layoutPane, "/com/yeocak/proje3/main_page.fxml");
    }

    @FXML
    private void onClickHesapListesi() {
        Utils.changePaneCenter(layoutPane, "/com/yeocak/proje3/acc_list.fxml");
    }

    @FXML
    private void onClickYeniHesap() {
        Utils.changePaneCenter(layoutPane, "/com/yeocak/proje3/add_new_account.fxml");
    }

    @FXML
    private void onClickParaGonder() {
        Utils.changePaneCenter(layoutPane, "/com/yeocak/proje3/send_money.fxml");
    }

    @FXML
    private void onClickParaYatirCek() {
        Utils.changePaneCenter(layoutPane, "/com/yeocak/proje3/money_transfer.fxml");
    }

    @FXML
    private void onClickHesapDokumu() {
        Utils.changePaneCenter(layoutPane, "/com/yeocak/proje3/transaction_history.fxml");
    }

    @FXML
    private void onClickKrediCek() {
        Utils.changePaneCenter(layoutPane, "/com/yeocak/proje3/take_credit.fxml");
    }

    @FXML
    private void onClickKrediYatir() {
        Utils.changePaneCenter(layoutPane, "/com/yeocak/proje3/pay_credit.fxml");
    }

    @FXML
    private void onClickHesapDokumleri() {
        Utils.changePaneCenter(layoutPane, "/com/yeocak/proje3/rep_account_history.fxml");
    }

    @FXML
    private void onClickMusteriBilgileri(){
        Utils.changePaneCenter(layoutPane, "/com/yeocak/proje3/rep_update_costumer.fxml");
    }

    @FXML
    private void onClickCikis() {
        Utils.changePage(layoutPane, "/com/yeocak/proje3/login.fxml");
    }

    @FXML
    private void onClickBankaBilgileri(){
        Utils.changePaneCenter(layoutPane, "/com/yeocak/proje3/boss_bank_info.fxml");
    }

    @FXML
    private void onClickIslemOzetleri(){
        Utils.changePaneCenter(layoutPane, "/com/yeocak/proje3/boss_bank_summary.fxml");
    }

    @FXML
    private void onClickKurBilgileri(){
        Utils.changePaneCenter(layoutPane, "/com/yeocak/proje3/boss_exchanges.fxml");
    }

    @FXML
    private void onClickYeniKullanici(){
        RegisterController.fromWhere = "boss";
        Utils.changePaneCenter(layoutPane, "/com/yeocak/proje3/register.fxml");
    }
}
