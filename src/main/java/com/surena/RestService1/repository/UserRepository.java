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

    User findUserByPassword(String password);

      boolean existsUserByPassword(String password);

    /**
     * Hibernate findTop --> The only one result.
     * If you have not unique exception in your repository class, you can just pick top entity and solve non-unique situation.
     */

    User findTopByUsername(String username);

    boolean existsUserByUsername(String username);
}
