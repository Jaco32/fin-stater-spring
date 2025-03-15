package edu.jaco.fin_stater.stats.repo;

import edu.jaco.fin_stater.stats.entity.CategorizedMonthly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorizedMonthlyRepository extends JpaRepository<CategorizedMonthly, Long> {
}
