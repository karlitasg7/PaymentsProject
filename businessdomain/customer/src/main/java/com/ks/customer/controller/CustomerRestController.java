package com.ks.customer.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.ks.customer.entities.Customer;
import com.ks.customer.repository.CustomerRepository;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {

    @Autowired
    CustomerRepository customerRepository;

    private final WebClient.Builder webClientBuilder;

    public CustomerRestController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(EpollChannelOption.TCP_KEEPIDLE, 300)
            .option(EpollChannelOption.TCP_KEEPINTVL, 60)
            .responseTimeout(Duration.ofSeconds(1))
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            });

    private String getProductName(long id) {
        WebClient webClient = webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("http://localhost:8083/product")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8083/product"))
                .build();

        JsonNode jsonNode = webClient.method(HttpMethod.GET).uri("/" + id)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        return jsonNode.get("name").asText();
    }

    private List<?> getTransactions(String iban) {
        WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("http://localhost:8082/transaction")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return build.method(HttpMethod.GET).uri(uriBuilder -> uriBuilder
                        .path("/customer/transactions")
                        .queryParam("ibanAccount", iban)
                        .build())
                .retrieve()
                .bodyToFlux(Object.class)
                .collectList()
                .block();
    }

    @GetMapping
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Customer get(@PathVariable Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @GetMapping("/full")
    public Customer getByCode(@RequestParam String code) {
        Customer customer = customerRepository.findByCode(code);

        customer
                .getProducts()
                .forEach(product -> {
                    String productName = getProductName(product.getProductId());
                    product.setProductName(productName);
                });

        List<?> transactions = getTransactions(customer.getIban());
        customer.setTransactions(transactions);

        return customer;
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
