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

    public LinkedList<Stats> calculateMonthlyStats(Set<Transaction> transactionRows)
    {
        LinkedList<Stats> perMonthStats = new LinkedList<>();

        Map<Integer, ArrayList<Transaction>> transactionsPerMonth = new HashMap<>();
        transactionsPerMonth.put(0, new ArrayList<Transaction>());
        transactionsPerMonth.put(1, new ArrayList<Transaction>());
        transactionsPerMonth.put(2, new ArrayList<Transaction>());
        transactionsPerMonth.put(3, new ArrayList<Transaction>());
        transactionsPerMonth.put(4, new ArrayList<Transaction>());
        transactionsPerMonth.put(5, new ArrayList<Transaction>());
        transactionsPerMonth.put(6, new ArrayList<Transaction>());
        transactionsPerMonth.put(7, new ArrayList<Transaction>());
        transactionsPerMonth.put(8, new ArrayList<Transaction>());
        transactionsPerMonth.put(9, new ArrayList<Transaction>());
        transactionsPerMonth.put(10, new ArrayList<Transaction>());
        transactionsPerMonth.put(11, new ArrayList<Transaction>());

        Calendar calendar = Calendar.getInstance();

        for(Transaction trRow : transactionRows)
        {
            if(trRow.getType().equals("Spłata kredytu")) {
                if(trRow.getAmount() > -3240) {
                    calendar.setTime(trRow.getDate());
                    transactionsPerMonth.get(calendar.get(Calendar.MONTH)).add(trRow);
                }
            }
            else if(!trRow.getReceiver().contains("BUDLAND") && // GeoGrupa
                    !trRow.getReceiver().contains("MICHAŁ KOŚCIŃSKI") && // Klima
                    !trRow.getDescription().contains("010062941 74987502241466914879860") && // Booking.com -> Kaszuby
                    !trRow.getDescription().contains("BPID:AV9DZAFZXP") && // Przyczepka Thule
                    (!trRow.getDescription().contains("APART") && !trRow.getDescription().contains("2023-07-13")) && // Obrączki Apart
                    !trRow.getDescription().contains("TASHI THAI&SUSHI") && // Chrzciny Tosi
                    (!trRow.getSender().contains("ANNA MARIA PIECZKA") && (Double.compare(trRow.getAmount(), 10000) != 0)) && // Wkład Ani w Tourana
                    !trRow.getReceiver().contains("GRUPA CICHY-ZASADA SP. Z.O.O. ") && // Touran 50%
                    (!trRow.getDescription().contains("CICHY - ZASADA") && !trRow.getDescription().contains("2023-10-26")) && // Touran - hak
                    ((!trRow.getSender().contains("JACEK LESZEK WĘGORKIEWICZ") && (Double.compare(trRow.getAmount(), 12000) != 0) && !trRow.getDescription().contains("WPŁATA")) || // Kasa Mili
                            (trRow.getSender().contains("JACEK LESZEK WĘGORKIEWICZ") && (Double.compare(trRow.getAmount(), 10200) == 0) && trRow.getDescription().contains("WPŁATA"))) && // Kasa z chrzcin i ślubu
                    (Double.compare(trRow.getAmount(), -99600) != 0) &&
                    !trRow.getSender().contains("WĘGORKIEWICZ MACIEJ") &&
                    !trRow.getDescription().contains("KIMONKO") &&
                    (!trRow.getDescription().contains("centrumfotelikow.pl") && !trRow.getDescription().contains("00000075698411673")))
            {
                calendar.setTime(trRow.getDate());
                transactionsPerMonth.get(calendar.get(Calendar.MONTH)).add(trRow);
            }
        }

        for(int i = 0; i <= transactionsPerMonth.size()-1; i++) {
            Stats monthlyStats = new Stats();
            for(Transaction monthlyTransaction : transactionsPerMonth.get(i)) {
                if (monthlyTransaction.getAmount() < 0)
                    monthlyStats.setExpenses(monthlyStats.getExpenses() + monthlyTransaction.getAmount());
                else
                    monthlyStats.setIncome(monthlyStats.getIncome() + monthlyTransaction.getAmount());
            }
            monthlyStats.setSavings(monthlyStats.getIncome() - (-1*monthlyStats.getExpenses()));
            perMonthStats.add(monthlyStats);
        }

        return perMonthStats;
    }

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
        Comparator<Transaction> transactionDateComparator = Comparator.comparing(Transaction::getDate);
        Optional<Transaction> minTransactionDate = transactionRespository.findAll().stream().min(transactionDateComparator);
        Optional<Transaction> maxTransactionDate = transactionRespository.findAll().stream().max(transactionDateComparator);
        double income = transactionRespository.findAll().stream()
                .mapToDouble(tr -> tr.getAmount())
                .filter(am -> am > 0.0)
                .sum();

        double expenses = transactionRespository.findAll().stream()
                .mapToDouble(tr -> tr.getAmount())
                .filter(am -> am < 0.0)
                .sum();

        double balance = transactionRespository.findAll().stream().mapToDouble(tr -> tr.getAmount()).sum();
        balanceRepository.save(new Balance(minTransactionDate.get().getDate(),
                maxTransactionDate.get().getDate(), income, expenses, balance));
    }

    public void calculateBalanceMonthly() {
        Map<Integer, Map<String, Double>> result = new HashMap<>();
        Calendar calendar = Calendar.getInstance();

        for(Transaction transaction: transactionRespository.findAll())
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

        for(Map.Entry<Integer, Map<String, Double>> monthEntry: result.entrySet()) {
            Double income = monthEntry.getValue().get("income");
            Double expenses = monthEntry.getValue().get("expense");
            Double balance = income - (-1*expenses);
            calendar.set(Calendar.MONTH, monthEntry.getKey());
            String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.UK);
            balanceMonthlyRepository.save(new BalanceMonthly(month, income, expenses, balance, (balance / income) * 100));
        }
    }

    public void calculateCategorized() {
        Map<TransactionCategory, Double> result = new HashMap<>();

        for(Transaction transaction: transactionRespository.findAll()) {
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

        for(Map.Entry<TransactionCategory, Double> categoryEntry: result.entrySet()) {
            categorizedRepository.save(new Categorized(categoryEntry.getKey(), categoryEntry.getValue()));
        }
    }
}
