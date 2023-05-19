package com.ks.transactions.controller;

import com.ks.transactions.entities.Status;
import com.ks.transactions.entities.Transaction;
import com.ks.transactions.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transaction")
public class TransactionRestController {

    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Transaction get(@PathVariable Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> put(@PathVariable Long id, @RequestBody Transaction input) {

        Optional<Transaction> transaction = transactionRepository.findById(id);

        if (transaction.isPresent()) {

            Transaction existingTransaction = transaction.get();

            existingTransaction.setReference(input.getReference());
            existingTransaction.setAccountIban(input.getAccountIban());
            existingTransaction.setDate(input.getDate());
            existingTransaction.setAmount(input.getAmount());
            existingTransaction.setFee(input.getFee());
            existingTransaction.setDescription(input.getDescription());
            existingTransaction.setStatus(input.getStatus());
            existingTransaction.setChannel(input.getChannel());

            transactionRepository.save(existingTransaction);
        }

        return ResponseEntity.ok(transaction.orElse(null));
    }

    @PostMapping
    public ResponseEntity<Transaction> post(@RequestBody Transaction input) {

        if (input.getDate().after(new Date())) {
            input.setStatus(Status.PENDIENTE);
        }

        if (input.getDate().before(new Date())) {
            input.setStatus(Status.LIQUIDADA);
        }

        Transaction newTransaction = transactionRepository.save(input);

        return ResponseEntity.ok(newTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction> delete(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);

        transaction.ifPresent(value -> transactionRepository.delete(value));

        return ResponseEntity.ok().build();
    }

    @GetMapping("/customer/transactions")
    public List<Transaction> get(@RequestParam String ibanAccount) {
        return transactionRepository.findByAccount(ibanAccount);
    }

}
