package com.ks.customer.controller;

import com.ks.customer.entities.Customer;
import com.ks.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Customer get(@PathVariable Long id) {
        return customerRepository.findById(id).get();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> put(@PathVariable Long id, @RequestBody Customer input) {
        Customer customer = customerRepository.save(input);
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Customer> post(@RequestBody Customer input) {

        Customer newCustomer = customerRepository.save(input);

        return ResponseEntity.ok(newCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delete(@PathVariable Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            customerRepository.delete(customer.get());
        }

        return ResponseEntity.ok().build();
    }

}
