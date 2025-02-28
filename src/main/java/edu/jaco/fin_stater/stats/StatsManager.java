package edu.jaco.fin_stater.stats;

import edu.jaco.fin_stater.pos.PosDto;
import edu.jaco.fin_stater.stats.entity.Balance;
import edu.jaco.fin_stater.stats.entity.BalanceMonthly;
import edu.jaco.fin_stater.stats.entity.Categorized;
import edu.jaco.fin_stater.stats.repo.BalanceMonthlyRepository;
import edu.jaco.fin_stater.stats.repo.BalanceRepository;
import edu.jaco.fin_stater.stats.repo.CategorizedRepository;
import edu.jaco.fin_stater.transaction.Transaction;
import edu.jaco.fin_stater.transaction.TransactionCategory;
import edu.jaco.fin_stater.transaction.TransactionRespository;
import edu.jaco.fin_stater.transaction.TransactionSubcategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class StatsManager {

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private TransactionRespository transactionRespository;

    @Autowired
    BalanceMonthlyRepository balanceMonthlyRepository;

    @Autowired
    CategorizedRepository categorizedRepository;

    public Stats stats;

    public StatsManager(Stats stats) {
        this.stats = stats;
    }

    public StatsManager() {}

    public CategoryStats calculateCategoryStats(TransactionCategory category, List<Transaction> transactions) {
        CategoryStats categoryStats = new CategoryStats();

        categoryStats.category = category;

        categoryStats.count = transactions.stream()
                .filter(trRow -> trRow.getCategory() == category)
                .count();

        categoryStats.amount = transactions.stream()
                .filter((trRow -> trRow.getCategory() == category))
                .mapToDouble(trRow -> trRow.getAmount())
                .sum();

        double totalAmount = transactions.stream().mapToDouble(trRow -> trRow.getAmount()).sum();

        categoryStats.countPercentage = Math.round(((double)categoryStats.count / transactions.size()) * 100);
        categoryStats.amountPercentage = Math.round((categoryStats.amount / totalAmount) * 100);

        return categoryStats;
    }

    public void printSaldoView(List<Transaction> transactions) {
        for (int i = 0; i < transactions.size(); i++) {
            System.out.print(transactions.get(i).getDescription() + ", " + transactions.get(i).getBalance());
            if (i < (transactions.size() - 1)) {
                System.out.println(", " + Math.round((transactions.get(i).getBalance() - transactions.get(i + 1).getBalance())));
            }
        }
    }

    public void printSubcategoryView(List<Transaction> transactions, TransactionSubcategory subcategory)
    {
        double sum = 0.0;
        for (Transaction transaction : transactions) {
            if(transaction.getSubcategory() == subcategory) {
                sum += transaction.getAmount();
            }
        }

        System.out.println(subcategory.name() + ", sum = " + sum);
    }

    public void printTransactionsView(List<Transaction> transactions, TransactionPrintStyle printStyle)
    {
        int counter = 0;
        for(Transaction transaction: transactions) {
            if(printStyle == TransactionPrintStyle.ONE_LINER) {
                System.out.println("description: " + transaction.getDescription() + ", date: " + transaction.getDate() + ", amount: " + transaction.getAmount() + ", sender: " + transaction.getSender() + ", receiver: " + transaction.getReceiver());
            } else {
                System.out.println(transaction);
            }
            counter++;
        }
        System.out.println("Printed " + counter + " transactions");
    }

    public void printPosView(List<Transaction> transactions, TransactionCategory category, int month) {
        List<PosDto> posList = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if ((transaction.getCategory() == category) && (transaction.getDate().getMonth() == month)) {
                if (posList.size() > 0) {
                    boolean wasFound = false;
                    for (int i = 0; i < posList.size(); i++) {
                        PosDto posDto = posList.get(i);
                        if (posDto.getCategoryMatchKeyword().equals(transaction.getCategoryMatchKeyword())) {
                            wasFound = true;

                            posDto.getPosNames().add(transaction.getAdditional_info());
                            PosDto newPosItem = new PosDto(
                                    posDto.getPosNames(),
                                    posDto.getPosTransactionsCount() + 1,
                                    posDto.getPosTransactionsSum() + transaction.getAmount(),
                                    transaction.getCategoryMatchKeyword());

                            posList.set(i, newPosItem);
                            break;
                        }
                    }

                    if(!wasFound) {
                        PosDto posItem = new PosDto(transaction.getAdditional_info(), transaction.getAmount(), transaction.getCategoryMatchKeyword());
                        posList.add(posItem);
                    }
                } else {
                    PosDto posDto = new PosDto(transaction.getAdditional_info(), transaction.getAmount(), transaction.getCategoryMatchKeyword());
                    posList.add(posDto);
                }
            }
        }

        System.out.println(">>>> " + category + ", " + month + " <<<<");
        for (PosDto posDto : posList) {
            System.out.println(posDto.getCategoryMatchKeyword() + " -> " + posDto.getPosTransactionsCount() + " -> " + posDto.getPosTransactionsSum());
        }
    }

    public void printPosView(List<Transaction> transactions, TransactionCategory category) {
        List<PosDto> posList = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getCategory() == category) {
                if (posList.size() > 0) {
                    boolean wasFound = false;
                    for (int i = 0; i < posList.size(); i++) {
                        PosDto posDto = posList.get(i);
                        if (posDto.getCategoryMatchKeyword().equals(transaction.getCategoryMatchKeyword())) {
                            wasFound = true;

                            posDto.getPosNames().add(transaction.getAdditional_info());
                            PosDto newPosItem = new PosDto(
                                    posDto.getPosNames(),
                                    posDto.getPosTransactionsCount() + 1,
                                    posDto.getPosTransactionsSum() + transaction.getAmount(),
                                    transaction.getCategoryMatchKeyword());

                            posList.set(i, newPosItem);
                            break;
                        }
                    }

                    if(!wasFound) {
                        PosDto posItem = new PosDto(transaction.getAdditional_info(), transaction.getAmount(), transaction.getCategoryMatchKeyword());
                        posList.add(posItem);
                    }
                } else {
                    PosDto posDto = new PosDto(transaction.getAdditional_info(), transaction.getAmount(), transaction.getCategoryMatchKeyword());
                    posList.add(posDto);
                }
            }
        }

        System.out.println(">>>> " + category + " <<<<");
        for (PosDto posDto : posList) {
            System.out.println(posDto.getCategoryMatchKeyword() + " -> " + posDto.getPosTransactionsCount() + " -> " + posDto.getPosTransactionsSum());
        }
    }

    public void calculateBalance() {
        List<Transaction> transactions = transactionRespository.findAll();

        Comparator<Transaction> transactionDateComparator = Comparator.comparing(Transaction::getDate);
        Optional<Transaction> minTransactionDate = transactions.stream().min(transactionDateComparator);
        Optional<Transaction> maxTransactionDate = transactions.stream().max(transactionDateComparator);

        double income = transactions.stream()
                .filter(tr -> tr.isUsedForCalculation())
                .mapToDouble(tr -> tr.getAmount())
                .filter(am -> am > 0.0)
                .sum();

        double expenses = transactions.stream()
                .filter(tr -> tr.isUsedForCalculation())
                .mapToDouble(tr -> tr.getAmount())
                .filter(am -> am < 0.0)
                .sum();

        double balance = transactions.stream().mapToDouble(tr -> tr.getAmount()).sum();

        if (balanceRepository.count() != 0) balanceRepository.deleteAll();

        balanceRepository.save(new Balance(minTransactionDate.get().getDate(),
                maxTransactionDate.get().getDate(), income, expenses, balance));
    }

    public void calculateBalanceMonthly() {
        Map<Integer, Map<String, Double>> result = new HashMap<>();
        Calendar calendar = Calendar.getInstance();

        List<Transaction> transactions = transactionRespository.findAll()
                .stream()
                .filter(tr -> tr.isUsedForCalculation())
                .collect(Collectors.toList());

        for(Transaction transaction: transactions)
        {
            calendar.setTime(transaction.getDate());
            for(int month = Calendar.JANUARY; month <= Calendar.DECEMBER; month++) {
                if (calendar.get(Calendar.MONTH) == month) {
                    if (result.containsKey(month)) {
                        Map<String, Double> current = result.get(month);
                        if (transaction.getAmount() > 0) {
                            current.replace("income", current.get("income") + transaction.getAmount());
                        } else {
                            current.replace("expense", current.get("expense") + transaction.getAmount());
                        }
                        result.replace(month, current);
                    } else {
                        Map<String, Double> init = new HashMap<>();
                        if (transaction.getAmount() > 0d) {
                            init.put("income", transaction.getAmount());
                            init.put("expense", 0d);
                        } else {
                            init.put("income", 0d);
                            init.put("expense", transaction.getAmount());
                        }
                        result.put(month, init);
                    }
                }
            }
        }

        if (balanceMonthlyRepository.count() != 0) balanceMonthlyRepository.deleteAll();

        for(Map.Entry<Integer, Map<String, Double>> monthEntry: result.entrySet()) {
            Double income = monthEntry.getValue().get("income");
            Double expenses = monthEntry.getValue().get("expense");
            Double balance = income - (-1*expenses);

            calendar.set(Calendar.MONTH, monthEntry.getKey());
            String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.UK);

            double rateOfReturn = (balance / income) * 100;

            balanceMonthlyRepository.save(new BalanceMonthly(month, income, expenses, balance, rateOfReturn));
        }
    }

    public void calculateCategorized() {
        Map<TransactionCategory, Double> result = new HashMap<>();

        List<Transaction> transactions = transactionRespository.findAll()
                .stream()
                .filter(tr -> tr.isUsedForCalculation())
                .collect(Collectors.toList());

        for(Transaction transaction: transactions) {
            if(transaction.getAmount() < 0d) {
                if (result.containsKey(transaction.getCategory())) {
                    Double current = result.get(transaction.getCategory());
                    current += transaction.getAmount();
                    result.replace(transaction.getCategory(), current);
                } else {
                    result.put(transaction.getCategory(), transaction.getAmount());
                }
            }
        }

        List<Categorized> categorized = new ArrayList<>();
        for(Map.Entry<TransactionCategory, Double> categoryEntry: result.entrySet()) {
            categorized.add(new Categorized(categoryEntry.getKey(), categoryEntry.getValue()));
        }
        Comparator<Categorized> categoryComparator = Comparator.comparing(Categorized::getExpense);
        categorized.sort(categoryComparator);

        if (categorizedRepository.count() != 0) categorizedRepository.deleteAll();
        categorizedRepository.saveAll(categorized);
    }
}
