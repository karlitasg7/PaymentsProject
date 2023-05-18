package com.ks.customer.controller;

import com.ks.customer.entities.Customer;
import com.ks.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping()
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Customer get(@PathVariable String id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody Customer input) {
        return null;
    }

    @PostMapping
    public ResponseEntity<Customer> post(@RequestBody Customer input) {

        Customer newCustomer = customerRepository.save(input);

        return ResponseEntity.ok(newCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delete(@PathVariable String id) {
        return null;
    }

}
