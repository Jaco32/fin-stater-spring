package edu.jaco.fin_stater.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
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
    private double excluded;

    @Column(name = "period_balance")
    private double periodBalance;

    @Column(name = "view_name")
    protected String viewName;

    public View() {}

    public View(LocalDate from,
                LocalDate to,
                double income,
                double expenses,
                double excluded,
                double periodBalance,
                String viewName)
    {
        this.fromDate = from;
        this.toDate = to;
        this.income = income;
        this.expenses = expenses;
        this.excluded = excluded;
        this.periodBalance = periodBalance;
        this.viewName = viewName;
    }
}
