package edu.jaco.fin_stater.transaction.impl;

import edu.jaco.fin_stater.transaction.Transaction;
import edu.jaco.fin_stater.transaction.intf.TransactionManager;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class SantanderTranzMgr extends TransactionManager {

    public List<Transaction> loadTransactions(String path) throws IOException, CsvValidationException, ParseException {
        CSVParser csvParser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(false)
                .build();

        Path filePath = Paths.get(path);
        Reader reader = Files.newBufferedReader(filePath);

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(1)
                .withCSVParser(csvParser)
                .build();

        String[] rowValues;
        List<Transaction> transactions = new LinkedList<>();
        while((rowValues = csvReader.readNext()) != null) {
            Transaction transaction = new Transaction();
            transaction.setDate(new SimpleDateFormat("dd-MM-yyyy").parse(rowValues[0]));
            transaction.setDescription(rowValues[2]);
            transaction.setAmount(Double.parseDouble(rowValues[5].replace(',', '.')));
            transaction.setBalance(Double.parseDouble(rowValues[6].replace(',', '.')));
            if (transaction.getAmount() > 0) {
                transaction.setSender(rowValues[3]);
            }
            else {
                transaction.setReceiver(rowValues[3]);
            }
            categorizeRow(transaction);
            transactions.add(transaction);
        }

        return transactions;
    }
}
