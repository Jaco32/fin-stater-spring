package edu.jaco.fin_stater.stats.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class View {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    private double income;
    private double expenses;

    @Column(name = "period_balance")
    private double periodBalance;

    @Column(name = "view_name")
    protected String viewName;

    public View() {}

    public View(LocalDate from, LocalDate to, double income, double expenses, double periodBalance, String viewName) {
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

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

    public double getPeriodBalance() {
        return periodBalance;
    }

    public void setPeriodBalance(double periodBalance) {
        this.periodBalance = periodBalance;
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public String toString() {
        return "View{" +
                "id=" + id +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", income=" + income +
                ", expenses=" + expenses +
                ", periodBalance=" + periodBalance +
                ", viewName='" + viewName + '\'' +
                '}';
    }
}
