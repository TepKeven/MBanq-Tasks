package com.tepkeven.bankservices.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_accounts", schema = "public")
@Getter
@Setter
public class Account {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ACCOUNT_NO", nullable = false, unique = true)
    private String accountNo;

    @Column(name = "OPEN_DATE", nullable = false, updatable = false)
    @CreationTimestamp
    private Date openDate;

    @Column(name = "ACC_TYPE", nullable = false)
    private String accType;

    @Column(name = "TOTAL_BALANCE", nullable = false)
    private Double balance;

    @Column(name = "BLOCKED_DATE", nullable = true) // If null then account is not blocked
    private Date blockDate;

    @Column(name = "ACC_STATUS", nullable = false)
    private int accStatus = 1;

    @ManyToOne  // 1 user can have multiple accounts
    @JoinColumn(name = "USER_ID")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "account") // 1 account can have multiple transactions
    @JsonManagedReference
    private List<Transaction> transactions;

}
