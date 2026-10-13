package edu.jaco.fin_stater.entity;

import edu.jaco.fin_stater.transaction.enums.TransactionCategory;
import edu.jaco.fin_stater.transaction.enums.TransactionFrequency;
import edu.jaco.fin_stater.transaction.enums.TransactionSubcategory;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate date;
    private String type = "";
    private double amount;
    private double balance;
    private String sender;
    private String receiver = "";
    private String description;
    private String additional_info = "";
    private String additional_info_2 = "";

    @Enumerated(EnumType.STRING)
    private TransactionCategory category;

    @Enumerated(EnumType.STRING)
    private TransactionSubcategory subcategory;

    @Enumerated(EnumType.STRING)
    private TransactionFrequency frequency;

    @Column(name = "category_match_keyword")
    private String categoryMatchKeyword;

    @Column(name = "used_for_calculation")
    private boolean usedForCalculation;

    public Transaction() {}
}
