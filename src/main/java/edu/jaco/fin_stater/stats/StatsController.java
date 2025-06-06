package edu.jaco.fin_stater.stats;

import edu.jaco.fin_stater.stats.entity.*;
import edu.jaco.fin_stater.stats.repo.*;
import edu.jaco.fin_stater.transaction.Transaction;
import edu.jaco.fin_stater.transaction.TransactionRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("stat")
public class StatsController {

    Logger logger = LoggerFactory.getLogger(StatsController.class);

    @Autowired
    BalanceRepository balanceRepository;

    @Autowired
    BalanceMonthlyRepository balanceMonthlyRepository;

    @Autowired
    CategorizedRepository categorizedRepository;

    @Autowired
    BalanceAvarageRepository balanceAvarageRepository;

    @Autowired
    CategorizedMonthlyRepository categorizedMonthlyRepository;

    @Autowired
    TransactionRespository transactionRespository;

    @Autowired
    StatsManager statsManager;

    @CrossOrigin
    @GetMapping
    public List<Balance> getBalance(@RequestHeader("mode") String mode) {
        logger.info("getBalance - entered");
        return balanceRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("month")
    public List<BalanceMonthly> getBalanceMonthly(@RequestHeader("mode") String mode) {
        logger.info("getBalanceMonthly - entered");
        return balanceMonthlyRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("categorized")
    public List<Categorized> getCategorized(@RequestHeader("mode") String mode) {
        logger.info("getCategorized - entered");
        return categorizedRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("avarage")
    public BalanceAvarage getBalanceAvarage(@RequestHeader("mode") String mode) {
        logger.info("getBalanceAvarage - entered");
        return balanceAvarageRepository.findAll().get(0);
    }

    @CrossOrigin
    @GetMapping("categorizedmonthly")
    public List<CategorizedMonthly> getCategorizedMonthly(@RequestHeader("mode") String mode) {
        logger.info("getCategorizedMonthly - entered");
        return categorizedMonthlyRepository.findAll();
    }

    @CrossOrigin
    @PatchMapping("view/{name}")
    public void createView(@RequestHeader("mode") String mode, @PathVariable String name, @RequestBody String indexes) {
        List<Long> idxs = Arrays.stream(indexes.split(","))
                .mapToLong(it -> Long.valueOf(it))
                .boxed()
                .toList();

        List<Transaction> transactions = transactionRespository.findAllById(idxs);
        statsManager.calculateBalance(transactions, name);
    }
}
