package edu.jaco.fin_stater.transaction;

import edu.jaco.fin_stater.transaction.enums.TransactionCategory;
import edu.jaco.fin_stater.transaction.enums.TransactionFrequency;
import edu.jaco.fin_stater.transaction.enums.TransactionSubcategory;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
public class Transaction {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    private LocalDate date;
    @Getter
    private String type = "";
    @Getter
    private double amount;
    @Getter
    private double balance;
    @Getter
    private String sender;
    @Getter
    private String receiver = "";
    @Getter
    private String description;
    @Getter
    private String additional_info = "";
    @Getter
    private String additional_info_2 = "";

    @Getter
    @Enumerated(EnumType.STRING)
    private TransactionCategory category;

    @Getter
    @Enumerated(EnumType.STRING)
    private TransactionSubcategory subcategory;

    @Enumerated(EnumType.STRING)
    private TransactionFrequency frequency;

    @Getter
    @Column(name = "category_match_keyword")
    private String categoryMatchKeyword;

    @Getter
    @Column(name = "used_for_calculation")
    private boolean usedForCalculation;

    public Transaction() {}

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public void setAdditional_info_2(String additional_info_2) {
        this.additional_info_2 = additional_info_2;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public void setSubcategory(TransactionSubcategory subcategory) {
        this.subcategory = subcategory;
    }

    public void setFrequency(TransactionFrequency frequency) {
        this.frequency = frequency;
    }

    public void setCategoryMatchKeyword(String categoryMatchKeyword) {
        this.categoryMatchKeyword = categoryMatchKeyword;
    }

    public void setUsedForCalculation(boolean usedForCalculation) {
        this.usedForCalculation = usedForCalculation;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date=" + date +
                ", amount=" + amount +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", description='" + description + '\'' +
                ", additional_info='" + additional_info + '\'' +
                ", additional_info_2='" + additional_info_2 + '\'' +
                ", category=" + category +
                ", subcategory=" + subcategory +
                ", usedForCalculation=" + usedForCalculation +
                '}';
    }
}
