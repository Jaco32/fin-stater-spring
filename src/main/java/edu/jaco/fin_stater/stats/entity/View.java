package edu.jaco.fin_stater.stats.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Getter
    @Setter
    private double income;

    @Getter
    @Setter
    private double expenses;

    @Getter
    @Setter
    private double excluded;

    @Getter
    @Setter
    @Column(name = "period_balance")
    private double periodBalance;

    @Getter
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

    public LocalDate getFrom_date() {
        return fromDate;
    }

    public LocalDate getTo() {
        return toDate;
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
