package edu.jaco.fin_stater.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class ViewAvarage extends View {

    @Column(name = "avarage_income")
    private double avarageIncome;

    @Column(name = "avarage_expenses")
    private double avarageExpenses;

    @Column(name = "avarage_balance")
    private double avarageBalance;

    public ViewAvarage() {}

    public ViewAvarage(LocalDate from,
                       LocalDate to,
                       double income,
                       double expenses,
                       double excluded,
                       double balance,
                       String viewName,
                       double avgIncome,
                       double avgExpenses,
                       double avgBalance)
    {
        super(from, to, income, expenses, excluded, balance, viewName);
        this.avarageIncome = avgIncome;
        this.avarageExpenses = avgExpenses;
        this.avarageBalance = avgBalance;
    }
}
