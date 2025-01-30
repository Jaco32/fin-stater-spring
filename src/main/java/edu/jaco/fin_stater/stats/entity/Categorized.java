package edu.jaco.fin_stater.stats.entity;

import edu.jaco.fin_stater.transaction.TransactionCategory;
import jakarta.persistence.*;

@Entity
public class Categorized {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionCategory category;

    private double expense;

    public Categorized() {}

    public Categorized(TransactionCategory category, double expense) {
        this.category = category;
        this.expense = expense;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public double getExpense() {
        return expense;
    }
}
