package com.example.bankingsystemapp;

public class UserModels {
    String userName,balance,phoneNumber;

    public UserModels(String userName, String balance, String phoneNumber) {
        this.userName = userName;
        this.balance = balance;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
