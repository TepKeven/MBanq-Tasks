package com.tepkeven.bankservices.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_transactions", schema = "public")
@Getter
@Setter
public class Transaction {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BATCH_ID", nullable = false)
    private String batchId;

    @Column(name = "TXN_DATE", nullable = false, updatable = false)
    @CreationTimestamp
    private Date txnDate;

    @Column(name = "AMOUNT", nullable = false)
    private Double amount;

    @Column(name = "TXN_CODE", nullable = false)
    private int txnCode;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    @JsonBackReference
    private Account account;

}
