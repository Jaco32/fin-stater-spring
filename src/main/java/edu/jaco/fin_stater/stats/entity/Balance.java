package edu.jaco.fin_stater.stats.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date fromDate;
    private Date toDate;
    private double income;
    private double expenses;
    private double periodBalance;

    public Balance() {}

    public Balance(Date from, Date to, double income, double expenses, double periodBalance) {
        this.fromDate = from;
        this.toDate = to;
        this.income = income;
        this.expenses = expenses;
        this.periodBalance = periodBalance;
    }

    public Date getFrom_date() {
        return fromDate;
    }

    public Date getTo() {
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
}
