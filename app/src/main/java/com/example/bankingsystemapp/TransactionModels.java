package com.example.bankingsystemapp;

public class TransactionModels {
    String name1,name2,date,transaction,balance;

    public TransactionModels(String name1, String name2, String date, String transaction, String balance) {
        this.name1 = name1;
        this.name2 = name2;
        this.date = date;
        this.transaction = transaction;
        this.balance = balance;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
