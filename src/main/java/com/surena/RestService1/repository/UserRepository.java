package com.surena.RestService1.repository;

import com.surena.RestService1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    void deleteUserById(Long id);

    void deleteByUsername(String username);

    User findUserById(Long id);

    User findByUsername(String username);

    boolean existsUserByUsername(String username);
}
