package edu.jaco.fin_stater.stats.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BalanceAvarage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double avarageIncome;

    private double avarageExpenses;

    private double avarageBalance;

    public BalanceAvarage() {}

    public BalanceAvarage(double income, double expenses, double balance) {
        this.avarageIncome = income;
        this.avarageExpenses = expenses;
        this.avarageBalance = balance;
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
