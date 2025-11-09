package edu.jaco.fin_stater.stats.repo;

import edu.jaco.fin_stater.stats.entity.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewRepository extends JpaRepository<View, Long> {

    List<View> findByViewName(String viewName);
}
