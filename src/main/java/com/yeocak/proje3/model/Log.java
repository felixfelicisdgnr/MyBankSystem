package com.yeocak.proje3.model;

public class Log {
    private int logId;
    private String sourceTc;
    private String targetTc;
    private Operation operation;
    private int amount;
    private int sourceBalance;
    private int targetBalance;
    private String date;

    public Log(int logId, String sourceTc, String targetTc, Operation operation, int amount, int sourceBalance, int targetBalance, String date) {
        this.logId = logId;
        this.sourceTc = sourceTc;
        this.targetTc = targetTc;
        this.operation = operation;
        this.amount = amount;
        this.sourceBalance = sourceBalance;
        this.targetBalance = targetBalance;
        this.date = date;
    }

    public Log(String sourceTc, String targetTc, Operation operation, int amount, int sourceBalance, int targetBalance, String date) {
        this.sourceTc = sourceTc;
        this.targetTc = targetTc;
        this.operation = operation;
        this.amount = amount;
        this.sourceBalance = sourceBalance;
        this.targetBalance = targetBalance;
        this.date = date;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getSourceTc() {
        return sourceTc;
    }

    public void setSourceTc(String sourceTc) {
        this.sourceTc = sourceTc;
    }

    public String getTargetTc() {
        return targetTc;
    }

    public void setTargetTc(String targetTc) {
        this.targetTc = targetTc;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSourceBalance() {
        return sourceBalance;
    }

    public void setSourceBalance(int sourceBalance) {
        this.sourceBalance = sourceBalance;
    }

    public int getTargetBalance() {
        return targetBalance;
    }

    public void setTargetBalance(int targetBalance) {
        this.targetBalance = targetBalance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
