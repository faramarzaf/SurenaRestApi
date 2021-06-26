package com.surena.RestService1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "company")
    private User user;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ca_fid", referencedColumnName = "id")
    private Set<Address> addresses = new HashSet<>();


    public Company() {
    }

 /*   public Company(Long id, String name, User user, Set<Address> addresses) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.addresses = addresses;
    }

    public Company(String name, User user, Set<Address> addresses) {
        this.name = name;
        this.user = user;
        this.addresses = addresses;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

}
