package edu.jaco.fin_stater.stats.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate fromDate;
    private LocalDate toDate;
    private double income;
    private double expenses;
    private double periodBalance;
    private String viewName;

    public Balance() {}

    public Balance(LocalDate from, LocalDate to, double income, double expenses, double periodBalance, String viewName) {
        this.fromDate = from;
        this.toDate = to;
        this.income = income;
        this.expenses = expenses;
        this.periodBalance = periodBalance;
        this.viewName = viewName;
    }

    public LocalDate getFrom_date() {
        return fromDate;
    }

    public LocalDate getTo() {
        return toDate;
    }

    public double getIncome() {
        return income;
    }

    public double getExpenses() {
        return expenses;
    }

    public double getPeriodBalance() {
        return periodBalance;
    }

    public String getViewName() {
        return viewName;
    }
}
