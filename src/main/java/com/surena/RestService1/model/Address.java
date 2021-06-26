package com.surena.RestService1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String street;

    private int number;

    private Long lat;

    private Long lon;

    public Address() {
    }

/*    public Address(Long id, String street, int number, Long lat, Long lon, Set<Company> companies) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.lat = lat;
        this.lon = lon;
        this.companies = companies;
    }

    public Address(String street, int number, Long lat, Long lon, Set<Company> companies) {
        this.street = street;
        this.number = number;
        this.lat = lat;
        this.lon = lon;
        this.companies = companies;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Long getLat() {
        return lat;
    }

    public void setLat(Long lat) {
        this.lat = lat;
    }

    public Long getLon() {
        return lon;
    }

    public void setLon(Long lon) {
        this.lon = lon;
    }



}
