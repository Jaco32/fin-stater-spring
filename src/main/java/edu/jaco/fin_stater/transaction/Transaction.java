package edu.jaco.fin_stater.transaction;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public String getAdditional_info_2() {
        return additional_info_2;
    }

    public void setAdditional_info_2(String additional_info_2) {
        this.additional_info_2 = additional_info_2;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public TransactionSubcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(TransactionSubcategory subcategory) {
        this.subcategory = subcategory;
    }

    public void setFrequency(TransactionFrequency frequency) {
        this.frequency = frequency;
    }

    public String getCategoryMatchKeyword() {
        return categoryMatchKeyword;
    }

    public void setCategoryMatchKeyword(String categoryMatchKeyword) {
        this.categoryMatchKeyword = categoryMatchKeyword;
    }

    public boolean isUsedForCalculation() {
        return usedForCalculation;
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
