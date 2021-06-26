package com.surena.RestService1.repository;

import com.surena.RestService1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<User, Long> {


    List<User> findByAddresses_streetIgnoreCase(String address);

    List<User> findByCompany_nameIgnoreCase(String companyName);

}
