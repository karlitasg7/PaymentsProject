package com.ks.customer.controller;

import com.ks.customer.business.transactions.BussinesTransaction;
import com.ks.customer.entities.Customer;
import com.ks.customer.exception.BussinesRuleException;
import com.ks.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BussinesTransaction bussinesTransaction;

    @GetMapping
    public ResponseEntity<List<Customer>> findAll() {
        List<Customer> customerList = customerRepository.findAll();

        if (customerList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(customerList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> get(@PathVariable Long id) {
        return customerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/full")
    public Customer getByCode(@RequestParam String code) {
        return bussinesTransaction.get(code);
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
    public ResponseEntity<Customer> post(@RequestBody Customer input) throws UnknownHostException, BussinesRuleException {
        Customer save = bussinesTransaction.save(input);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delete(@PathVariable Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        customer.ifPresent(value -> customerRepository.delete(value));

        return ResponseEntity.ok().build();
    }

}
