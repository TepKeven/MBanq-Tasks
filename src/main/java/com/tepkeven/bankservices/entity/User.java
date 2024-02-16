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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_registers", schema = "public")
@Getter
@Setter
public class User {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "FIRST_NAME", length=50, nullable = false)
    private String firstname;

    @Column(name = "LAST_NAME", length=50, nullable = false)
    private String lastname;

    @Column(name = "DOB", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dob;

    @Column(name = "GENDER", nullable = false)
    private String gender;

    @Column(name = "PHONE", length=20, nullable = false, unique = true)
    private String phone;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ADDRESS", length=200, nullable = true)
    private String address;

    @Column(name = "REGISTER_DATE", nullable = false, updatable = false)
    @CreationTimestamp
    private Date registerDate;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Account> accounts;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    @JsonBackReference
    private Role role;
}

