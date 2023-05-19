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
        return customerRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> put(@PathVariable Long id, @RequestBody Customer input) {

        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()) {

            Customer existingCustomer = customer.get();

            existingCustomer.setCode(input.getCode());
            existingCustomer.setName(input.getName());
            existingCustomer.setIban(input.getIban());
            existingCustomer.setPhone(input.getPhone());
            existingCustomer.setSurname(input.getSurname());
            existingCustomer.setAddress(input.getAddress());

            input.getProducts().forEach(x -> x.setCustomer(existingCustomer));
            existingCustomer.setProducts(input.getProducts());

            customerRepository.save(existingCustomer);
        }

        return ResponseEntity.ok(customer.orElse(null));
    }

    @PostMapping
    public ResponseEntity<Customer> post(@RequestBody Customer input) {

        input.getProducts().forEach(x -> x.setCustomer(input));

        Customer newCustomer = customerRepository.save(input);

        return ResponseEntity.ok(newCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delete(@PathVariable Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        customer.ifPresent(value -> customerRepository.delete(value));

        return ResponseEntity.ok().build();
    }

}
