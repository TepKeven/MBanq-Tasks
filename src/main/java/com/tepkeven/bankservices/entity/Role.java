package com.tepkeven.bankservices.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_roles", schema = "public")
@Getter
@Setter
public class Role {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", length=100, nullable = false)
    private String name;

    @OneToMany(mappedBy = "role") // 1 Role can have multiple users
    @JsonManagedReference
    private List<User> users;
}
