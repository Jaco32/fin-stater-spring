package edu.jaco.fin_stater.stats.repo;

import edu.jaco.fin_stater.stats.entity.BalanceAvarage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceAvarageRepository extends JpaRepository<BalanceAvarage, Long> {
}
