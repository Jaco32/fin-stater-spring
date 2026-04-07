package edu.jaco.fin_stater.transaction.intf;

import edu.jaco.fin_stater.dictionaries.cats.*;
import edu.jaco.fin_stater.dictionaries.cats.body.CialoDictionary;
import edu.jaco.fin_stater.dictionaries.cats.body.subcats.BeautyDictionary;
import edu.jaco.fin_stater.dictionaries.cats.body.subcats.HigienaDictionary;
import edu.jaco.fin_stater.dictionaries.cats.body.subcats.ZdrowieDictionary;
import edu.jaco.fin_stater.dictionaries.cats.car.AutoDictionary;
import edu.jaco.fin_stater.dictionaries.cats.car.subcats.PaliwoDictionary;
import edu.jaco.fin_stater.dictionaries.cats.car.subcats.SerwisDictionary;
import edu.jaco.fin_stater.dictionaries.cats.food.SpozywczeDictionary;
import edu.jaco.fin_stater.dictionaries.cats.food.subcats.MarketyDictionary;
import edu.jaco.fin_stater.dictionaries.cats.food.subcats.SlodkosciDictionary;
import edu.jaco.fin_stater.dictionaries.cats.kids.DzieciDictionary;
import edu.jaco.fin_stater.dictionaries.cats.kids.subcats.PlacowkiDictionary;
import edu.jaco.fin_stater.dictionaries.cats.oplaty.OplatyDictionary;
import edu.jaco.fin_stater.dictionaries.cats.oplaty.subcats.SubskrypcjeDictionary;
import edu.jaco.fin_stater.dictionaries.freqs.*;
import edu.jaco.fin_stater.dictionaries.cats.food.subcats.ZamawianeDictionary;
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

    public abstract void loadTransactions(String path) throws IOException, CsvValidationException, ParseException;

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
        } else if(CialoDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setCategory(TransactionCategory.CIALO);
        } else if(AutoDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setCategory(TransactionCategory.AUTO);
        } else if(RowerDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setCategory(TransactionCategory.ROWER);
        } else if(DzieciDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setCategory(TransactionCategory.DZIECI);
        } else if(OplatyDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setCategory(TransactionCategory.OPLATY);
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
        } else if(SlodkosciDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setSubcategory(TransactionSubcategory.SPOZYWCZE_SLODKOSCI);
        } else if(MarketyDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setSubcategory(TransactionSubcategory.SPOZYWCZE_MARKETY);
        } else if(PaliwoDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setSubcategory(TransactionSubcategory.AUTO_PALIWO);
        } else if(SerwisDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setSubcategory(TransactionSubcategory.AUTO_SERWIS);
        } else if(HigienaDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setSubcategory(TransactionSubcategory.CIALO_HIGIENA);
        } else if(ZdrowieDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setSubcategory(TransactionSubcategory.CIALO_ZDROWIE);
        } else if(BeautyDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setSubcategory(TransactionSubcategory.CIALO_BEAUTY);
        } else if(PlacowkiDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setSubcategory(TransactionSubcategory.DZIECI_PLACOWKI);
        } else if(SubskrypcjeDictionary.matchTransactionRow(transactionRow)) {
            transactionRow.setSubcategory(TransactionSubcategory.OPLATY_SUBSKRYPCJE);
        } else {
            if(transactionRow.getCategory() == TransactionCategory.SPOZYWCZE)
                transactionRow.setSubcategory(TransactionSubcategory.SPOZYWCZE_OTHER);
            else if(transactionRow.getCategory() == TransactionCategory.AUTO)
                transactionRow.setSubcategory(TransactionSubcategory.AUTO_OTHER);
            else if(transactionRow.getCategory() == TransactionCategory.CIALO)
                transactionRow.setSubcategory(TransactionSubcategory.CIALO_OTHER);
            else if(transactionRow.getCategory() == TransactionCategory.DZIECI)
                transactionRow.setSubcategory(TransactionSubcategory.DZIECI_OTHER);
            else if(transactionRow.getCategory() == TransactionCategory.OPLATY)
                transactionRow.setSubcategory(TransactionSubcategory.OPLATY_OTHER);
            else
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
