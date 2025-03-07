package edu.jaco.fin_stater.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRespository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    public List<Transaction> findByType(String type);

    public List<Transaction> findBySender(String sender);

    //public List<Transaction> findBySenderWithSchema(String sender);
}
