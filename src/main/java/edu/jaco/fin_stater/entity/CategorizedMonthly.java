package edu.jaco.fin_stater.entity;

import edu.jaco.fin_stater.transaction.enums.TransactionCategory;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categorized_monthly")
@NoArgsConstructor
@Data
public class CategorizedMonthly {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionCategory category;

    private double expense;

    public CategorizedMonthly(TransactionCategory category, double expense) {
        this.category = category;
        this.expense = expense;
    }
}
