package edu.jaco.fin_stater.transaction.impl;

import edu.jaco.fin_stater.transaction.Transaction;
import edu.jaco.fin_stater.transaction.intf.TransactionManager;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

@Component
public class PkoBpTranzMgr extends TransactionManager {

    public List<Transaction> loadTransactions(String path) {
        List<Transaction> transactionRows = new LinkedList<>();

        try {
            Workbook workbook = new XSSFWorkbook(path);
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
                Transaction transactionRow = new Transaction();
                Row row = sheet.getRow(i);
                for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                    if (j == 0) { // Data operacji
                        try {
                            String dateCellValue = row.getCell(j).getStringCellValue();
                            transactionRow.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateCellValue));
                        }
                        catch (IllegalStateException e) {
                            transactionRow.setDate(DateUtil.getJavaDate(row.getCell(j).getNumericCellValue()));
                        }
                    }
                    if (j == 2) { // Typ tranzakcji
                        transactionRow.setType(row.getCell(j).getStringCellValue());
                    }
                    if (j == 3) { // Kwota
                        try {
                            transactionRow.setAmount(row.getCell(j).getNumericCellValue());
                        } catch (IllegalStateException ex) {
                            System.out.println("i = " + i);
                            throw ex;
                        }
                    }
                    if (j == 5) { // Saldo po tranzakcji
                        transactionRow.setBalance(row.getCell(j).getNumericCellValue());;
                    }
                    if (j == 7) { // Nazwa nadawcy
                        transactionRow.setSender(row.getCell(j).getStringCellValue());
                    }
                    if (j == 10) { // Nazwa odbiorcy
                        transactionRow.setReceiver(row.getCell(j).getStringCellValue());
                    }
                    if (j == 12) { // Opis tranzakcji
                        transactionRow.setDescription(row.getCell(j).getStringCellValue());
                    }
                    if (j == 13) { // kolumna N. dodatkowe info - lokalizacja (kraj + miasto), nr telefonu, opis
                        if(row.getCell(j) != null)
                            transactionRow.setAdditional_info(row.getCell(j).getStringCellValue());;
                    }
                    if (j == 14) { // kolumna O. dodatkowe info - adres strony internetowej
                        if(row.getCell(j) != null)
                            transactionRow.setAdditional_info_2(row.getCell(j).getStringCellValue());
                    }
                }

                categorizeRow(transactionRow);
                subCategorizeRow(transactionRow);
                setTransactionFrequency(transactionRow);
                transactionRow.setUsedForCalculation(true);
                transactionRespository.save(transactionRow);
                transactionRows.add(transactionRow);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return transactionRows;
    }

    public List<Transaction> loadTransactionsFromAPI(byte[] fileContent) {
        List<Transaction> transactionRows = new LinkedList<>();

        try {
            InputStream fileIS = new ByteArrayInputStream(fileContent);
            Workbook workbook = new XSSFWorkbook(fileIS);
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
                Transaction transactionRow = new Transaction();
                Row row = sheet.getRow(i);
                for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                    if (j == 0) { // Data operacji
                        try {
                            String dateCellValue = row.getCell(j).getStringCellValue();
                            transactionRow.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateCellValue));
                        }
                        catch (IllegalStateException e) {
                            transactionRow.setDate(DateUtil.getJavaDate(row.getCell(j).getNumericCellValue()));
                        }
                    }
                    if (j == 2) { // Typ tranzakcji
                        transactionRow.setType(row.getCell(j).getStringCellValue());
                    }
                    if (j == 3) { // Kwota
                        try {
                            transactionRow.setAmount(row.getCell(j).getNumericCellValue());
                        } catch (IllegalStateException ex) {
                            System.out.println("i = " + i);
                            throw ex;
                        }
                    }
                    if (j == 5) { // Saldo po tranzakcji
                        transactionRow.setBalance(row.getCell(j).getNumericCellValue());;
                    }
                    if (j == 7) { // Nazwa nadawcy
                        transactionRow.setSender(row.getCell(j).getStringCellValue());
                    }
                    if (j == 10) { // Nazwa odbiorcy
                        transactionRow.setReceiver(row.getCell(j).getStringCellValue());
                    }
                    if (j == 12) { // Opis tranzakcji
                        transactionRow.setDescription(row.getCell(j).getStringCellValue());
                    }
                    if (j == 13) { // kolumna N. dodatkowe info - lokalizacja (kraj + miasto), nr telefonu, opis
                        if(row.getCell(j) != null)
                            transactionRow.setAdditional_info(row.getCell(j).getStringCellValue());;
                    }
                    if (j == 14) { // kolumna O. dodatkowe info - adres strony internetowej
                        if(row.getCell(j) != null)
                            transactionRow.setAdditional_info_2(row.getCell(j).getStringCellValue());
                    }
                }

                categorizeRow(transactionRow);
                subCategorizeRow(transactionRow);
                setTransactionFrequency(transactionRow);
                transactionRow.setUsedForCalculation(true);
                transactionRespository.save(transactionRow);
                transactionRows.add(transactionRow);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return transactionRows;
    }
}
