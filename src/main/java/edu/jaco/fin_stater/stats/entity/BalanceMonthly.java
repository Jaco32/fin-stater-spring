package edu.jaco.fin_stater.stats.entity;

import jakarta.persistence.*;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "categorized_monthly_id")
    private List<CategorizedMonthly> categorizedMonthly;

    public BalanceMonthly() {}

    public BalanceMonthly(String month,
                          double income,
                          double expenses,
                          double balance,
                          double rateOfReturn,
                          List<CategorizedMonthly> categorizedMonthly)
    {
        this.monthName = month;
        this.income = income;
        this.expenses = expenses;
        this.balance = balance;
        this.rateOfReturn = rateOfReturn;
        this.categorizedMonthly = categorizedMonthly;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setRateOfReturn(double rateOfReturn) {
        this.rateOfReturn = rateOfReturn;
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

    public List<CategorizedMonthly> getCategorizedMonthly() {
        return categorizedMonthly;
    }

    public void setCategorizedMonthly(List<CategorizedMonthly> categorizedMonthly) {
        this.categorizedMonthly = categorizedMonthly;
    }
}
