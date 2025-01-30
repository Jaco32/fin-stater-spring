package edu.jaco.fin_stater.transaction.intf;

import edu.jaco.fin_stater.dictionaries.cats.*;
import edu.jaco.fin_stater.dictionaries.freqs.*;
import edu.jaco.fin_stater.dictionaries.subcats.ZamawianeDictionary;
import edu.jaco.fin_stater.transaction.*;
import com.opencsv.exceptions.CsvValidationException;
import edu.jaco.fin_stater.transaction.Transaction;
import edu.jaco.fin_stater.transaction.TransactionRespository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class TransactionManager {

    @Autowired
    protected TransactionRespository transactionRespository;

    public abstract List<Transaction> loadTransactions(String path) throws IOException, CsvValidationException, ParseException;

    public List<Transaction> excludeTransactionsByExcelRowNumber(List<Transaction> transactions, List<Integer> excelRowNumbers)
    {
        return IntStream.range(0, transactions.size())
                .filter(i -> !excelRowNumbers.contains(i+2))
                .mapToObj(i -> transactions.get(i))
                .collect(Collectors.toList());
    }

    public List<Transaction> excludeTransactionsByDescription(List<Transaction> transactions, String description) {
        return transactions.stream()
                .filter(tr -> !tr.getDescription().contains(description))
                .collect(Collectors.toList());
    }

    public List<Transaction> excludeTransactionsByReceiver(List<Transaction> transactions, String receiver) {
        return transactions.stream()
                .filter(tr -> !tr.getReceiver().contains(receiver))
                .collect(Collectors.toList());
    }

    public List<Transaction> excludeIncome(List<Transaction> transactions) {
        return transactions.stream()
                .filter(tr -> tr.getAmount() > 0.0)
                .collect(Collectors.toList());
    }

    public List<Transaction> findTransactionsBySender(String sender, List<Transaction> transactions) {
        return transactions.stream()
                .filter(tr -> tr.getSender() != null)
                .filter(tr -> tr.getSender().contains(sender))
                .collect(Collectors.toList());
    }

    public List<Transaction> findTransactionsByDescription(String description, List<Transaction> transactions) {
        return transactions.stream()
                .filter(tr -> tr.getDescription() != null)
                .filter(tr -> tr.getDescription().contains(description))
                .collect(Collectors.toList());
    }

    public List<Transaction> findTransactionsByReceiver(String receiver, List<Transaction> transactions) {
        return transactions.stream()
                .filter(tr -> tr.getReceiver() != null)
                .filter(tr -> tr.getReceiver().contains(receiver))
                .collect(Collectors.toList());
    }
/*
    private Set<TransactionRow> excludeTransactionsByIndex() {

    }

    private Set<TransactionRow> excludeTransactionsByData() {

    }
*/

    protected void categorizeRow(Transaction transactionRow)
    {
        if(SpozywczeDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setCategory(TransactionCategory.SPOZYWCZE);
        } else if(ZdrowieDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setCategory(TransactionCategory.ZDROWIE);
        } else if(HigienaDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setCategory(TransactionCategory.HIGIENA);
        } else if(PaliwoDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setCategory(TransactionCategory.PALIWO);
        } else if(AutoDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setCategory(TransactionCategory.AUTO);
        } else if(RowerDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setCategory(TransactionCategory.ROWER);
        } else if(DzieciDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setCategory(TransactionCategory.DZIECI);
        } else if(OplatyDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setCategory(TransactionCategory.OPLATY);
        } else if(DominoDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setCategory(TransactionCategory.DOMINO);
        } else if(KartaKredytowaDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setCategory(TransactionCategory.KARTA_KREDYTOWA);
        } else if(DomDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setCategory(TransactionCategory.DOM);
        } else if(BankomatDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setCategory(TransactionCategory.BANKOMAT);
        } else {
            transactionRow.setCategory(TransactionCategory.OTHER);
        }
    }

    protected void subCategorizeRow(Transaction transactionRow) {
        if(ZamawianeDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setSubcategory(TransactionSubcategory.SPOZYWCZE_ZAMAWIANE);
        } else {
            transactionRow.setSubcategory(TransactionSubcategory.OTHER);
        }
    }

    protected void setTransactionFrequency(Transaction transactionRow) {
        if(MonthlyDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setFrequency(TransactionFrequency.MONTHLY);
        } else if(YearlyDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setFrequency(TransactionFrequency.YEARLY);
        } else {
            transactionRow.setFrequency(TransactionFrequency.OTHER);
        }
    }
}
