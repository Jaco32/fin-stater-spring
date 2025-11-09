package edu.jaco.fin_stater.stats.entity;

import jakarta.persistence.Entity;

@Entity
public class ViewAvarage extends View {

    private double avarageIncome;

    private double avarageExpenses;

    private double avarageBalance;

    public ViewAvarage() {}

    public ViewAvarage(double income, double expenses, double balance, String viewName) {
        this.avarageIncome = income;
        this.avarageExpenses = expenses;
        this.avarageBalance = balance;
        this.viewName = viewName;
    }

    public double getAvarageIncome() {
        return avarageIncome;
    }

    public double getAvarageExpenses() {
        return avarageExpenses;
    }

    public double getAvarageBalance() {
        return avarageBalance;
    }

    public void setAvarageIncome(double avarageIncome) {
        this.avarageIncome = avarageIncome;
    }

    public void setAvarageExpenses(double avarageExpenses) {
        this.avarageExpenses = avarageExpenses;
    }

    public void setAvarageBalance(double avarageBalance) {
        this.avarageBalance = avarageBalance;
    }
}
