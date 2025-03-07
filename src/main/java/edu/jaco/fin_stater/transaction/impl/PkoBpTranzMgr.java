package edu.jaco.fin_stater.transaction.impl;

import edu.jaco.fin_stater.transaction.Transaction;
import edu.jaco.fin_stater.transaction.TransactionSpecification;
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
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class PkoBpTranzMgr extends TransactionManager {

    private final short TRANSACTION_DATE_COLUMN_NUM = 0;
    private final short TRANSACTION_TYPE_COLUMN_NUM = 2;
    private final short TRANSACTION_AMOUNT_COLUMN_NUM = 3;
    private final short TRANSACTION_BALANCE_COLUMN_NUM = 5;
    private final short TRANSACTION_SENDER_COLUMN_NUM = 7;
    private final short TRANSACTION_RECEIVER_COLUMN_NUM = 10;
    private final short TRANSACTION_DESCRIPTION_COLUMN_NUM = 12;
    private final short TRANSACTION_ADD_INFO_1_COLUMN_NUM = 13;
    private final short TRANSACTION_ADD_INFO_2_COLUMN_NUM = 14;

    public void loadTransactions(String path) {
        try
        {
            Workbook workbook = new XSSFWorkbook(path);
            parseTransactions(workbook);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadTransactionsFromAPI(byte[] fileContent) {
        try {
            InputStream fileIS = new ByteArrayInputStream(fileContent);
            Workbook workbook = new XSSFWorkbook(fileIS);
            parseTransactions(workbook);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseTransactions(Workbook workbook) throws ParseException {
        Sheet sheet = workbook.getSheetAt(0);
        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
            Transaction transactionRow = new Transaction();
            Row row = sheet.getRow(i);
            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                switch (j) {
                    case TRANSACTION_DATE_COLUMN_NUM:
                        try {
                            String dateCellValue = row.getCell(j).getStringCellValue();
                            transactionRow.setDate(LocalDate.parse(dateCellValue));
                        } catch (IllegalStateException e) {
                            LocalDateTime ldt = DateUtil.getLocalDateTime(row.getCell(j).getNumericCellValue());
                            transactionRow.setDate(ldt.toLocalDate());
                        }
                        break;
                    case TRANSACTION_TYPE_COLUMN_NUM:
                        transactionRow.setType(row.getCell(j).getStringCellValue());
                        break;
                    case TRANSACTION_AMOUNT_COLUMN_NUM:
                        try {
                            transactionRow.setAmount(row.getCell(j).getNumericCellValue());
                        } catch (IllegalStateException ex) {
                            throw ex;
                        }
                        break;
                    case TRANSACTION_BALANCE_COLUMN_NUM:
                        transactionRow.setBalance(row.getCell(j).getNumericCellValue());
                        break;
                    case TRANSACTION_SENDER_COLUMN_NUM:
                        transactionRow.setSender(row.getCell(j).getStringCellValue());
                        break;
                    case TRANSACTION_RECEIVER_COLUMN_NUM:
                        transactionRow.setReceiver(row.getCell(j).getStringCellValue());
                        break;
                    case TRANSACTION_DESCRIPTION_COLUMN_NUM:
                        transactionRow.setDescription(row.getCell(j).getStringCellValue());
                        break;
                    case TRANSACTION_ADD_INFO_1_COLUMN_NUM:
                        if (row.getCell(j) != null)
                            transactionRow.setAdditional_info(row.getCell(j).getStringCellValue());
                        break;
                    case TRANSACTION_ADD_INFO_2_COLUMN_NUM:
                        if (row.getCell(j) != null)
                            transactionRow.setAdditional_info_2(row.getCell(j).getStringCellValue());
                }
            }

            categorizeRow(transactionRow);
            subCategorizeRow(transactionRow);
            setTransactionFrequency(transactionRow);
            transactionRow.setUsedForCalculation(true);

            TransactionSpecification transactionSpecification = new TransactionSpecification(transactionRow);
            List<Transaction> matchingTransactions = transactionRespository.findAll(transactionSpecification);
            if(matchingTransactions.size() == 1) transactionRespository.delete(matchingTransactions.get(0));
            else transactionRespository.save(transactionRow);
        }
    }
}
