package edu.jaco.fin_stater.stats;

import edu.jaco.fin_stater.pos.PosDto;
import edu.jaco.fin_stater.stats.entity.*;
import edu.jaco.fin_stater.stats.repo.ViewAvarageRepository;
import edu.jaco.fin_stater.stats.repo.BalanceMonthlyRepository;
import edu.jaco.fin_stater.stats.repo.ViewRepository;
import edu.jaco.fin_stater.stats.repo.CategorizedRepository;
import edu.jaco.fin_stater.transaction.Transaction;
import edu.jaco.fin_stater.transaction.TransactionCategory;
import edu.jaco.fin_stater.transaction.TransactionRespository;
import edu.jaco.fin_stater.transaction.TransactionSubcategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Period;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class StatsManager {

    @Autowired
    private ViewRepository viewRepository;

    @Autowired
    private TransactionRespository transactionRespository;

    @Autowired
    BalanceMonthlyRepository balanceMonthlyRepository;

    @Autowired
    CategorizedRepository categorizedRepository;

    @Autowired
    ViewAvarageRepository balanceAvarageRepository;

    public StatsManager() {}

    public CategoryStats calculateCategoryStats(TransactionCategory category, List<Transaction> transactions) {
        CategoryStats categoryStats = new CategoryStats();

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

    public void printPosView(List<Transaction> transactions, TransactionCategory category, int month) {
        List<PosDto> posList = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if ((transaction.getCategory() == category) /*&& (transaction.getDate().getMonth() == month)*/) {
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

        viewRepository.save(new View(minTransactionDate.get().getDate(),
                maxTransactionDate.get().getDate(), income, expenses, balance, "Full date range"));
    }

    public void calculateBalance(List<Transaction> transactions, String viewName) {
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

        viewRepository.save(new View(minTransactionDate.get().getDate(),
                maxTransactionDate.get().getDate(), income, expenses, balance, viewName));
    }

    public void calculateBalanceMonthly(Map<YearMonth, Set<CategorizedMonthly>> calegorizedMonthly) {
        Map<YearMonth, Map<String, Double>> result = new TreeMap<>();

        List<Transaction> transactions = transactionRespository.findAll()
                .stream()
                .filter(tr -> tr.isUsedForCalculation())
                .collect(Collectors.toList());

        for(Transaction transaction: transactions)
        {
            YearMonth mapKey = YearMonth.of(transaction.getDate().getYear(), transaction.getDate().getMonth());
            if (result.containsKey(mapKey)) {
                Map<String, Double> current = result.get(mapKey);
                if (transaction.getAmount() > 0) {
                    current.replace("income", current.get("income") + transaction.getAmount());
                } else {
                    current.replace("expense", current.get("expense") + transaction.getAmount());
                }
                result.replace(mapKey, current);
            } else {
                Map<String, Double> init = new HashMap<>();
                if (transaction.getAmount() > 0d) {
                    init.put("income", transaction.getAmount());
                    init.put("expense", 0d);
                } else {
                    init.put("income", 0d);
                    init.put("expense", transaction.getAmount());
                }
                result.put(mapKey, init);
            }
        }

        if (balanceMonthlyRepository.count() != 0) balanceMonthlyRepository.deleteAll();

        for(Map.Entry<YearMonth, Map<String, Double>> monthEntry: result.entrySet()) {
            Double income = monthEntry.getValue().get("income");
            Double expenses = monthEntry.getValue().get("expense");
            Double balance = income - (-1*expenses);
            double rateOfReturn = (balance / income) * 100;
            ArrayList<CategorizedMonthly> categorizedMonth = new ArrayList<>(calegorizedMonthly.get(monthEntry.getKey()));
            Comparator<CategorizedMonthly> categoryComparator = Comparator.comparing(CategorizedMonthly::getExpense);
            categorizedMonth.sort(categoryComparator);
            BalanceMonthly balanceMonthly = new BalanceMonthly(
                    monthEntry.getKey().toString(),
                    income,
                    expenses,
                    balance,
                    rateOfReturn,
                    categorizedMonth);
            balanceMonthlyRepository.save(balanceMonthly);
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

    public void calculateBalanceAvarage(String viewName) {
        View view = viewRepository.findByViewName(viewName);
        Period period = Period.between(view.getFrom_date(), view.getTo());
        long monthsCount = period.getMonths();
        if (period.getDays() > 0) monthsCount++;
        double avgIncome = view.getIncome()/monthsCount;
        double avgExpense = view.getExpenses()/monthsCount;
        double avgBalance = view.getPeriodBalance()/monthsCount;
        balanceAvarageRepository.save(new ViewAvarage(avgIncome, avgExpense, avgBalance, viewName));
    }

    public Map<YearMonth, Set<CategorizedMonthly>> calculateCategorizedMonthly() {
        Map<YearMonth, Set<CategorizedMonthly>> result = new TreeMap<>();

        List<Transaction> transactions = transactionRespository.findAll()
                .stream()
                .filter(tr -> tr.isUsedForCalculation())
                .collect(Collectors.toList());

        for(Transaction transaction: transactions)
        {
            YearMonth mapKey = YearMonth.of(transaction.getDate().getYear(), transaction.getDate().getMonth());
            if (result.containsKey(mapKey)) {
                Set<CategorizedMonthly> current = result.get(mapKey);
                if (current.stream().filter(it -> it.getCategory() == transaction.getCategory()).count() > 0) {
                    Iterator<CategorizedMonthly> itr = current.iterator();
                    CategorizedMonthly tmp;
                    while(itr.hasNext()) {
                        tmp = itr.next();
                        if ((tmp.getCategory() == transaction.getCategory()) && (transaction.getAmount() < 0d)) {
                            double newExpense = tmp.getExpense() + transaction.getAmount();
                            tmp.setExpense(newExpense);
                            break;
                        }
                    }
                } else {
                    if (transaction.getAmount() < 0d) {
                        CategorizedMonthly newCategory = new CategorizedMonthly(transaction.getCategory(), transaction.getAmount());
                        current.add(newCategory);
                    }
                }
            } else {
                if (transaction.getAmount() < 0d) {
                    CategorizedMonthly newCategory = new CategorizedMonthly(transaction.getCategory(), transaction.getAmount());
                    Set<CategorizedMonthly> newList = new HashSet<>();
                    newList.add(newCategory);
                    result.put(mapKey, newList);
                }
            }
        }

        for(YearMonth ym: result.keySet()) {
            Set<CategorizedMonthly> cm = result.get(ym);
            cm.forEach(it -> System.out.println("Kategoria: " + it.getCategory() + ", " + it.getExpense()));
        }

        return result;
    }
}
