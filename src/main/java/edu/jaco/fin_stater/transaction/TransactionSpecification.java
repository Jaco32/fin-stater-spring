package edu.jaco.fin_stater.transaction;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class TransactionSpecification implements Specification<Transaction> {

    private Transaction transaction;

    public TransactionSpecification(Transaction transaction) {
        super();
        this.transaction = transaction;
    }

    @Override
    public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(
                criteriaBuilder.equal(root.get("date"), transaction.getDate()),
                criteriaBuilder.equal(root.get("amount"), transaction.getAmount() * -1),
                criteriaBuilder.equal(root.get("description"), transaction.getDescription())
        );
    }
}
