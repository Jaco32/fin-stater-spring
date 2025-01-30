package edu.jaco.fin_stater.stats;

import edu.jaco.fin_stater.stats.entity.Balance;
import edu.jaco.fin_stater.stats.entity.BalanceMonthly;
import edu.jaco.fin_stater.stats.entity.Categorized;
import edu.jaco.fin_stater.stats.repo.BalanceMonthlyRepository;
import edu.jaco.fin_stater.stats.repo.BalanceRepository;
import edu.jaco.fin_stater.stats.repo.CategorizedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("stat")
public class StatsController {

    @Autowired
    BalanceRepository balanceRepository;

    @Autowired
    BalanceMonthlyRepository balanceMonthlyRepository;

    @Autowired
    CategorizedRepository categorizedRepository;

    @CrossOrigin
    @GetMapping
    public Balance getBalance(@RequestHeader("mode") String mode) {
        return balanceRepository.findAll().get(0);
    }

    @CrossOrigin
    @GetMapping("month")
    public List<BalanceMonthly> getBalanceMonthly(@RequestHeader("mode") String mode) {
        return balanceMonthlyRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("categorized")
    public List<Categorized> getCategorized(@RequestHeader("mode") String mode) {
        return categorizedRepository.findAll();
    }
}
