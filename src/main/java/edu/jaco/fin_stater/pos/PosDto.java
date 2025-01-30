package edu.jaco.fin_stater.pos;

import java.util.ArrayList;
import java.util.List;

public class PosDto {

    private final List<String> posNames = new ArrayList<>();
    private final int posTransactionsCount;
    private final double posTransactionsSum;
    private final String categoryMatchKeyword;

    public PosDto(String posName, double posTransactionsAmount, String categoryMatchKeyword) {
        this.posNames.add(posName);
        this.posTransactionsCount = 1;
        this.posTransactionsSum = posTransactionsAmount;
        this.categoryMatchKeyword = categoryMatchKeyword;
    }

    public PosDto(List<String> posNames, int posTransactionsCount, double posTransactionsSum, String categoryMatchKeyword) {
        this.posNames.addAll(posNames);
        this.posTransactionsCount = posTransactionsCount;
        this.posTransactionsSum = posTransactionsSum;
        this.categoryMatchKeyword = categoryMatchKeyword;
    }

    public List<String> getPosNames() {
        return posNames;
    }

    public int getPosTransactionsCount() {
        return posTransactionsCount;
    }

    public double getPosTransactionsSum() {
        return posTransactionsSum;
    }

    public String getCategoryMatchKeyword() {
        return categoryMatchKeyword;
    }
}
