package edu.jaco.fin_stater.stats.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BalanceMonthly {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String monthName;
    private double income;
    private double expenses;
    private double balance;
    private double rateOfReturn;

    public BalanceMonthly() {}

    public BalanceMonthly(String month, double income, double expenses, double balance, double rateOfReturn) {
        this.monthName = month;
        this.income = income;
        this.expenses = expenses;
        this.balance = balance;
        this.rateOfReturn = rateOfReturn;
    }

    public String getMonth() {
        return monthName;
    }

    public double getIncome() {
        return income;
    }

    public double getExpenses() {
        return expenses;
    }

    public double getBalance() {
        return balance;
    }

    public double getRateOfReturn() {
        return rateOfReturn;
    }
}
