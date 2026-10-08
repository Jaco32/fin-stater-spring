package edu.jaco.fin_stater.repo;

import edu.jaco.fin_stater.entity.BalanceMonthly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceMonthlyRepository extends JpaRepository<BalanceMonthly, Long> {
}
