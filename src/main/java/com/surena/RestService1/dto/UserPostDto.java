package com.surena.RestService1.dto;

import com.surena.RestService1.model.Address;
import com.surena.RestService1.model.Company;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserPostDto {

    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private String first_name;

    private String last_name;

    private LocalDateTime create_date;

    private LocalDateTime modified_date;

    private Company company;

    private List<Address> addresses = new ArrayList<>();

    public UserPostDto() {
    }

    public UserPostDto(Long id, String username, String password, String first_name, String last_name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public UserPostDto(Long id, String username, String password, String first_name, String last_name, LocalDateTime create_date, LocalDateTime modified_date, Company company, List<Address> addresses) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.create_date = create_date;
        this.modified_date = modified_date;
        this.company = company;
        this.addresses = addresses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    public LocalDateTime getModified_date() {
        return modified_date;
    }

    public void setModified_date(LocalDateTime modified_date) {
        this.modified_date = modified_date;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}