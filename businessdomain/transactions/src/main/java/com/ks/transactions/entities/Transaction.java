package com.ks.transactions.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String reference;
    private String accountIban;
    private Date date;
    private Double amount;
    private Double fee;
    private String description;
    private Status status;
    private Channel channel;

}
