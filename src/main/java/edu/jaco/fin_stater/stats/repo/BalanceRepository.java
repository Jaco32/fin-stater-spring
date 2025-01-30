package edu.jaco.fin_stater.stats.repo;

import edu.jaco.fin_stater.stats.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
}
