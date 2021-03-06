package com.surena.RestService1;

import com.surena.RestService1.exception.ApiRequestException;
import com.surena.RestService1.model.User;
import com.surena.RestService1.repository.UserRepository;
import com.surena.RestService1.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Validator validator;

    @Before
    public void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @Transactional
    public void save_user() {

        User user = new User();
        String encodedPassword = passwordEncoder.encode("0123");

        user.setUsername("SamMJ");
        user.setPassword(encodedPassword);
        user.setFirst_name("Sam");
        user.setLast_name("Johns");

        userService.save(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    @Transactional
    public void save_invalid_user() {
        User user = new User(1L, "", "", "Sam00", "Johns00");
        userService.save(user);
        verify(userRepository, never()).save(user);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations.size()).isEqualTo(2);

    }


    @Test
    @Transactional
    public void throw_exception_when_username_taken() {
        User user = new User(1L, "SamMJ", "0123", "Sam00", "Johns00");

        when(userRepository.existsUserByUsername(user.getUsername())).thenReturn(true);  // we simulate that username is exists.

        assertThatThrownBy(() -> userService.save(user))
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("Username has already taken!");

        verify(userRepository, never()).save(any()); // we expect if username has taken, never saves any user.
    }

    @Test
    @Transactional
    public void throw_exception_when_username_not_exists() {
        User user = new User(1L, "SamMJ", "0123", "Sam00", "Johns00");

        when(userRepository.existsUserByUsername(user.getUsername())).thenReturn(false);

        assertThatThrownBy(() -> userService.getByUsername(user.getUsername()))
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("User with username " + user.getUsername() + " not found!");
    }


    @Test
    @Transactional
    public void throw_exception_when_user_with_id_not_exists() {
        when(userRepository.findUserById(2L)).thenReturn((
                new User(1L, "SamMJ", "0123", "Sam", "Johns"))
        );
        User user = userService.getById(2L);

        assertThatThrownBy(() -> userService.getById(user.getId()))
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("User with id " + user.getId() + " not found!");
    }

    @Test
    @Transactional
    public void update_user() {
        User user = new User(1L, "SamMJ", "0123", "Sam", "Johns");

        when(userRepository.getById(any())).thenReturn(user);
        when(userService.update(user, 1L)).thenReturn(user);

        user.setFirst_name("Sam00");
        user.setLast_name("Johns00");

        assertEquals("Sam00", user.getFirst_name());
        assertEquals("Johns00", user.getLast_name());
    }

    @Test
    @Transactional
    public void update_password() {
        User user = new User(1L, "SamMJ", "0123", "Sam", "Johns");

        when(userRepository.getById(any())).thenReturn(user);
        when(userService.updatePassword(user, 1L, "0123")).thenReturn(user);

        user.setPassword("0124");

        assertThat(user.getPassword()).isEqualTo("0124");
    }

    @Test
    public void get_user_by_id() {
        when(userRepository.findUserById(1L)).thenReturn((
                new User(1L, "SamMJ", "0123", "Sam", "Johns"))
        );

        User user = userService.getById(1L);

        assertEquals(1L, user.getId());
        assertEquals("SamMJ", user.getUsername());
        assertEquals("0123", user.getPassword());
        assertEquals("Sam", user.getFirst_name());
        assertEquals("Johns", user.getLast_name());

    }

    @Test
    public void get_user_by_username() {
        when(userRepository.findByUsername("SamMJ"))
                .thenReturn((new User(1L, "SamMJ", "0123", "Sam", "Johns")));

        User user = userService.getByUsername("SamMJ");

        assertEquals(1L, user.getId());
        assertEquals("SamMJ", user.getUsername());
        assertEquals("0123", user.getPassword());
        assertEquals("Sam", user.getFirst_name());
        assertEquals("Johns", user.getLast_name());
    }

    @Test
    public void find_all_users() {

        List<User> list = new ArrayList<>();
        User user1 = new User(1L, "SamMJ", "0123",
                "Sam", "Johns", LocalDateTime.now(), LocalDateTime.now().plusHours(1));

        User user2 = new User(1L, "JohnWoo", "2525",
                "John", "Woods", LocalDateTime.now(), LocalDateTime.now().plusHours(1));

        User user3 = new User(1L, "SamuelM", "0141",
                "Samuel", "Mit", LocalDateTime.now(), LocalDateTime.now().plusHours(1));

        list.add(user1);
        list.add(user2);
        list.add(user3);

        when(userRepository.findAll()).thenReturn(list);

        List<User> empList = userService.getAllUsers();

        assertEquals(3, empList.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @Transactional
    public void delete_user_by_username() {
        User user1 = new User(1L, "SamMJ", "0123", "Sam", "Johns");
        userRepository.deleteByUsername(user1.getUsername());
        User deletedUser = userRepository.findByUsername("SamMJ");
        assertThat(deletedUser).isNull();
    }


    @Test
    @Transactional
    public void delete_user_by_id() {
        User user1 = new User(1L, "SamMJ", "0123", "Sam", "Johns");
        userRepository.deleteUserById(user1.getId());
        User deletedUser = userRepository.findUserById(1L);
        assertThat(deletedUser).isNull();
    }

}