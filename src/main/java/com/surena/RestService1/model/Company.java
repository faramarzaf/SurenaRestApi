package com.surena.RestService1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "company")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "companies")
    @JsonIgnoreProperties(value = {"companies"})
    private Set<Address> userAddresses = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ca_fid", referencedColumnName = "id")
    private Set<Address> companyAddresses = new HashSet<>();


    public Company() {
    }


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

    @JsonIgnore
    public Set<Address> getUserAddresses() {
        return userAddresses;
    }

    public void setUserAddresses(Set<Address> addresses) {
        this.userAddresses = addresses;
    }


    public Set<Address> getCompanyAddresses() {
        return companyAddresses;
    }

    public void setCompanyAddresses(Set<Address> addressSet) {
        this.companyAddresses = addressSet;
    }
}
