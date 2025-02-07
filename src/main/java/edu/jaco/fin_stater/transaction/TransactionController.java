package edu.jaco.fin_stater.transaction;

import edu.jaco.fin_stater.stats.StatsManager;
import edu.jaco.fin_stater.transaction.impl.PkoBpTranzMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionRespository transactionRespository;

    @Autowired
    private StatsManager statsManager;

    @Autowired
    private  PkoBpTranzMgr pkoBpTranzMgr;

    @CrossOrigin
    @GetMapping("/transaction")
    public List<Transaction> getTransaction(@RequestHeader("mode") String mode) {
        System.out.println("Received mode header with value: " + mode);
        return transactionRespository.findAll();
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
    public void uploadTransactions(@RequestHeader("mode") String mode, @RequestBody byte[] fileContent) {
        pkoBpTranzMgr.loadTransactionsFromAPI(fileContent);
        statsManager.calculateBalance();
        statsManager.calculateBalanceMonthly();
        statsManager.calculateCategorized();
    }

    @CrossOrigin
    @PatchMapping("/transaction/{id}")
    public void toogleTransactionForStats(@RequestHeader("mode") String mode, @PathVariable Integer id) {

    }
}
