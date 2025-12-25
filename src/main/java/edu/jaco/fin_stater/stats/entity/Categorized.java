package edu.jaco.fin_stater.stats.entity;

import edu.jaco.fin_stater.transaction.TransactionCategory;
import edu.jaco.fin_stater.transaction.TransactionSubcategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Entity
@NoArgsConstructor
public class Categorized {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Enumerated(EnumType.STRING)
    private TransactionCategory category;

    @Getter
    private double expense;

    @ElementCollection
    @Getter
    private Map<TransactionSubcategory, Double> subcategoryStat;

    public Categorized(TransactionCategory category,
                       double expense,
                       Map<TransactionSubcategory, Double> subcategoryStat)
    {
        this.category = category;
        this.expense = expense;
        this.subcategoryStat = subcategoryStat;
    }
}
