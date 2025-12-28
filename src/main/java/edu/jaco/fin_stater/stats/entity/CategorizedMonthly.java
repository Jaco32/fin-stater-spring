package edu.jaco.fin_stater.stats.entity;

import edu.jaco.fin_stater.transaction.TransactionCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categorized_monthly")
@NoArgsConstructor
public class CategorizedMonthly {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionCategory category;

    @Getter
    private double expense;

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

    public void setExpense(double expense) {
        this.expense = expense;
    }
}
