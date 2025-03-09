package edu.jaco.fin_stater.transaction;

import com.opencsv.exceptions.CsvValidationException;
import edu.jaco.fin_stater.stats.StatsManager;
import edu.jaco.fin_stater.transaction.impl.PkoBpTranzMgr;
import edu.jaco.fin_stater.transaction.impl.SantanderTranzMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class TransactionController {

    @Autowired
    private TransactionRespository transactionRespository;

    @Autowired
    private StatsManager statsManager;

    @Autowired
    private  PkoBpTranzMgr pkoBpTranzMgr;

    @Autowired
    private SantanderTranzMgr santanderTranzMgr;

    @CrossOrigin
    @GetMapping("/transaction")
    public List<Transaction> getTransaction(@RequestHeader("mode") String mode) {
        return transactionRespository.findAll(Sort.by("date").descending());
    }

    @CrossOrigin
    @GetMapping("/transaction/type/{type}")
    public List<Transaction> getTransactionByType(@RequestHeader("mode") String mode, @PathVariable String type) {
        return transactionRespository.findByType(type);
    }

    @CrossOrigin
    @GetMapping("/transaction/sender/{sender}")
    public List<Transaction> getTransactionBySender(@RequestHeader("mode") String mode, @PathVariable String sender) {
        return transactionRespository.findBySender(sender);
    }

    @CrossOrigin
    @PostMapping("/transaction/upload/")
    public void uploadTransactions(@RequestHeader("mode") String mode,
                                   @RequestHeader("Content-Type") String contentType,
                                   @RequestBody byte[] fileContent) throws CsvValidationException, IOException
    {
        if(contentType.equals("text/csv")) santanderTranzMgr.loadTransactionsFromAPI(fileContent);
        else pkoBpTranzMgr.loadTransactionsFromAPI(fileContent);

        statsManager.calculateBalance();
        statsManager.calculateBalanceMonthly();
        statsManager.calculateCategorized();
        statsManager.calculateBalanceAvarage();
    }

    @CrossOrigin
    @PatchMapping("/transaction/{id}")
    public void toogleTransactionForStats(@RequestHeader("mode") String mode, @PathVariable Long id) {
        Optional<Transaction> transaction = transactionRespository.findById(id);
        transaction.ifPresent(tr -> {
            tr.setUsedForCalculation(!tr.isUsedForCalculation());
            transactionRespository.save(tr);
        });
        statsManager.calculateBalance();
        statsManager.calculateBalanceMonthly();
        statsManager.calculateCategorized();
        statsManager.calculateBalanceAvarage();
    }
}
