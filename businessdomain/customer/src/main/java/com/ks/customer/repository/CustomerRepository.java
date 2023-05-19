package com.ks.customer.repository;

import com.ks.customer.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c where c.code = ?1 ")
    Customer findByCode(String code);

    @Query("select c from Customer c where c.iban = ?1 ")
    Customer findByAccount(String iban);

}
