package edu.jaco.fin_stater.transaction.impl;

import edu.jaco.fin_stater.transaction.Transaction;
import edu.jaco.fin_stater.transaction.TransactionRespository;
import edu.jaco.fin_stater.transaction.TransactionSpecification;
import edu.jaco.fin_stater.transaction.intf.TransactionManager;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Component
public class SantanderTranzMgr extends TransactionManager {

    @Autowired
    private TransactionRespository transactionRespository;

    public void loadTransactionsFromAPI(byte[] fileContent) throws CsvValidationException, IOException {
        CSVParser csvParser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(false)
                .build();

        Reader reader = new InputStreamReader(new ByteArrayInputStream(fileContent));

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(1)
                .withCSVParser(csvParser)
                .build();

        String[] rowValues;
        while((rowValues = csvReader.readNext()) != null) {
            System.out.println("CSV vals: " + rowValues[0] + ", " + rowValues[2] + ", " + rowValues[5]);
            Transaction transaction = new Transaction();
            transaction.setDate(LocalDate.parse(rowValues[0], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
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
            transaction.setUsedForCalculation(true);

            TransactionSpecification transactionSpecification = new TransactionSpecification(transaction);
            List<Transaction> matchingTransactions = transactionRespository.findAll(transactionSpecification);
            if(matchingTransactions.size() == 1) transactionRespository.delete(matchingTransactions.get(0));
            else transactionRespository.save(transaction);
        }
    }

    public void loadTransactions(String path) throws IOException, CsvValidationException, ParseException {
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
            //transaction.setDate(new SimpleDateFormat("dd-MM-yyyy").parse(rowValues[0]));
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
    }
}
