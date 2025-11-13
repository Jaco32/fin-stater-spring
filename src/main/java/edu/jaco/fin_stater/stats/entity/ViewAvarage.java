package edu.jaco.fin_stater.stats.entity;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class ViewAvarage extends View {

    private double avarageIncome;

    private double avarageExpenses;

    private double avarageBalance;

    public ViewAvarage() {}

    public ViewAvarage(LocalDate from,
                       LocalDate to,
                       double income,
                       double expenses,
                       double balance,
                       String viewName,
                       double avgIncome,
                       double avgExpenses,
                       double avgBalance)
    {
        super(from, to, income, expenses, balance, viewName);
        this.avarageIncome = avgIncome;
        this.avarageExpenses = avgExpenses;
        this.avarageBalance = avgBalance;
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

    @Override
    public String toString() {
        return "ViewAvarage{" +
                "avarageIncome=" + avarageIncome +
                ", avarageExpenses=" + avarageExpenses +
                ", avarageBalance=" + avarageBalance +
                ", viewName='" + viewName + '\'' +
                '}';
    }
}
