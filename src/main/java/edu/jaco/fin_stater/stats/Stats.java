package edu.jaco.fin_stater.stats;

public class Stats {
    private Double income = 0.0;

    public void print(Integer numberOfMonths) {
        System.out.println("Income per day: " + Math.round((income/numberOfMonths)/22));
        System.out.println("Income per hour: " + Math.round(((income/numberOfMonths)/22)/8));
    }
}
