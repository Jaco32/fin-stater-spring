package edu.jaco.fin_stater.stats.entity;

import edu.jaco.fin_stater.transaction.TransactionCategory;
import jakarta.persistence.*;

@Entity
@Table(name = "categorized_monthly")
public class CategorizedMonthly {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionCategory category;

    private double expense;

    private CategorizedMonthly() {}

    public CategorizedMonthly(TransactionCategory category, double expense) {
        this.category = category;
        this.expense = expense;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }
}
