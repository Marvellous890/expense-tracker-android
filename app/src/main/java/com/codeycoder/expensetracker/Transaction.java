package com.codeycoder.expensetracker;

public class Transaction {
    String name;
    float amount;
    String account = "Main Acccount";
    long timestamp;

    public Transaction(String name, float amount, long timestamp) {
        this.name = name;
        this.amount = amount;
        this.timestamp= timestamp;
    }
}
