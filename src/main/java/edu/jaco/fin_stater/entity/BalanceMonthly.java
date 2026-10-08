package edu.jaco.fin_stater.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "balance_monthly")
@Data
public class BalanceMonthly {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "month_name")
    String monthName;

    private double income;
    private double expenses;
    private double balance;

    @Column(name = "rate_of_return")
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
}
