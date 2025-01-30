package edu.jaco.fin_stater.stats;

import java.util.LinkedList;

public class Stats {
    private Double income = 0.0;
    private Double expenses = 0.0;
    private Double savings = 0.0;

    public void print(String year, Integer numberOfMonths) {
        System.out.println("===================== " + year + " =====================");
        System.out.println("Expenses: " + Math.round(-1*expenses));
        System.out.println("Expenses per month: " + Math.round((-1*expenses)/numberOfMonths));
        System.out.println("Income: " + Math.round(income));
        System.out.println("Income per month: " + Math.round(income/numberOfMonths));
        System.out.println("Income per day: " + Math.round((income/numberOfMonths)/22));
        System.out.println("Income per hour: " + Math.round(((income/numberOfMonths)/22)/8));
        System.out.println("Savings: " + Math.round(savings));
    }

    public void printMonth(Integer monthNumber) {
        System.out.println("Month " + monthNumber + ": " + Math.round(-1*expenses) + " | " + income + " | " + Math.round(savings));
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getExpenses() {
        return expenses;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }

    public Double getSavings() {
        return savings;
    }

    public void setSavings(Double savings) {
        this.savings = savings;
    }

    public void calculateAvarageSavings(LinkedList<Stats> monthlyStats, Integer startMonth, Integer endMonth) {
        Double savings = 0.0;
        for(int i = (startMonth-1); i < endMonth; i++) {
            savings += monthlyStats.get(i).getSavings();
        }
        Double avarageSavings = savings/(endMonth-startMonth+1);
        System.out.println(Math.round(avarageSavings));
    }
}
