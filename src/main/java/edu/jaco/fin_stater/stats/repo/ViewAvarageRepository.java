package edu.jaco.fin_stater.stats.repo;

import edu.jaco.fin_stater.stats.entity.ViewAvarage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewAvarageRepository extends JpaRepository<ViewAvarage, Long> {

    List<ViewAvarage> findByViewName(String viewName);
}
