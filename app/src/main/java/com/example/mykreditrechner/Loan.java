package com.example.mykreditrechner;

public class Loan {
    private int id;
    private double amount;
    private int term;
    private double interestRate;
//change

    public Loan(int id, double amount, int term, double interestRate) {
        this.id = Integer.parseInt(String.valueOf(id));
        this.amount = Double.parseDouble(String.valueOf(amount));
        this.term = Integer.parseInt(String.valueOf(term));
        this.interestRate = Double.parseDouble(String.valueOf(interestRate));
    }

    public Loan(String auto, String number, String number1, String s) {
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public int getTerm() {
        return term;
    }

    public double getInterestRate() {
        return interestRate;
    }
}

