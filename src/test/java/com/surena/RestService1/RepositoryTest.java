package com.surena.RestService1;

import com.surena.RestService1.model.User;
import com.surena.RestService1.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * If you want to run the tests against the actually registered database,
 * you can annotate the test with @AutoConfigureTestDatabase(replace=Replace.NONE),
 * which will use the registered DataSource instead of an in-memory data source.
 */

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTest {


    @Autowired
    private UserRepository repository;

    @After
    public void tear_down() {
        repository.deleteAll();
    }

    @Before
    public void tear_down_bef() {
        repository.deleteAll();
    }

    @Test
    public void save_user() {
        User user1 = new User(1L, "SamMJ", "0123", "Sam", "Johns");
        repository.save(user1);
        User user2 = repository.findTopByUsername("SamMJ");
        assertNotNull(user1);
        assertEquals(user2.getFirst_name(), user1.getFirst_name());
        assertEquals(user2.getLast_name(), user1.getLast_name());
    }


    @Test
    public void get_all_user() {
        User user1 = new User(1L, "SamMJ", "0123", "Sam", "Johns");
        repository.save(user1);
        assertNotNull(repository.findAll());
    }

    @Test
    public void get_user_by_username() {
        User user1 = new User(1L, "SamMJ", "0123", "Sam", "Johns");
        repository.save(user1);
        User user2 = repository.findTopByUsername("SamMJ");
        assertNotNull(user1);
        assertEquals(user2.getFirst_name(), user1.getFirst_name());
        assertEquals(user2.getLast_name(), user1.getLast_name());
    }

    @Test
    public void get_user_by_id() {
        User user1 = new User(1L, "Sara", "0123", "Sara", "Johns");
        repository.save(user1);
        User user2 = repository.getById(user1.getId());
        assertNotNull(user1);
        assertEquals(user2.getId(), user1.getId());
    }

    @Test
    public void delete_user() {
        User user1 = new User(1L, "SamMJ", "0123", "Sam", "Johns");
        repository.save(user1);
        repository.delete(user1);
        assertNull(repository.findUserById(1L));
    }

    @Test
    public void delete_by_id() {
        User user1 = new User(1L, "SamMJ", "0123", "Sam", "Johns");
        User user2 = repository.save(user1);
        repository.deleteUserById(user2.getId());
        assertNull(repository.findUserById(1L));
    }

    @Test
    public void delete_by_username() {
        User user1 = new User(1L, "SamMJ", "0123", "Sam", "Johns");
        User user2 = repository.save(user1);
        repository.deleteByUsername(user2.getUsername());
        assertNull(repository.findByUsername(user2.getUsername()));
    }

    @Test
    public void exist_by_username() {
        User user1 = new User(1L, "SamMJ", "0123", "Sam", "Johns");
        repository.save(user1);
        Boolean exists = repository.existsUserByUsername(user1.getUsername());
        assertThat(exists).isTrue();
    }

}
