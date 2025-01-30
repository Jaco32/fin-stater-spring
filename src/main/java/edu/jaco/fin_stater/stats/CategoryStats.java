package edu.jaco.fin_stater.stats;

import edu.jaco.fin_stater.transaction.TransactionCategory;

import java.text.NumberFormat;

public class CategoryStats {

    public TransactionCategory category;
    public long count;
    public double amount;
    public long countPercentage;
    public long amountPercentage;
    private final NumberFormat pnf;
    private final NumberFormat nf;

    public CategoryStats() {
        this.pnf = NumberFormat.getPercentInstance();
        this.nf = NumberFormat.getInstance();
        this.nf.setMaximumFractionDigits(2);
    }

    @Override
    public String toString() {
        return category + ": \n" +
                "amount = " + nf.format(amount) + " / " + pnf.format(((double)amountPercentage / 100)) +
                "    |    count = " + count + " / " + pnf.format(((double)countPercentage) / 100);
    }
}
