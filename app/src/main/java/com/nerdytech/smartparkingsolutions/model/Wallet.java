package com.nerdytech.smartparkingsolutions.model;

public class Wallet {
    private int amount;

    public Wallet() {
    }

    public Wallet(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
