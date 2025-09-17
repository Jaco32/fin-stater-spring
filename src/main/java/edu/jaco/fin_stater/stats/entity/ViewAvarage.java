package edu.jaco.fin_stater.stats.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
}
