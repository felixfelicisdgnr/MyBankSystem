package com.yeocak.proje3.model;

import com.yeocak.proje3.db.BalanceRepository;

public class Account {
    private int accId;
    private String tc;
    private int balance;
    private String exchange;

    public Account(int accId, String tc, int balance, String exchange) {
        this.accId = accId;
        this.tc = tc;
        this.balance = balance;
        this.exchange = exchange;
    }

    public void updateAccount(){
        Account newAcc = BalanceRepository.getAccount(accId);
        if(newAcc != null){
            this.accId = newAcc.accId;
            this.tc = newAcc.tc;
            this.balance = newAcc.balance;
            this.exchange = newAcc.exchange;
        }
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
}
