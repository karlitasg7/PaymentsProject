package com.ks.transactions.repository;

import com.ks.transactions.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
            from Transaction
            where accountIban = ?1
            """)
    List<Transaction> findByAccount(String accountIban);

}
