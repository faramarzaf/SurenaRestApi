package com.surena.RestService1;

import com.surena.RestService1.model.User;
import com.surena.RestService1.repository.UserRepository;
import com.surena.RestService1.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DataJpaTest
@SpringBootTest
public class UserServiceTest {


    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;


    @Before
    public void init() {
        userService = new UserService();
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void save_user() {
        User user = new User(1L, "SamMJ", "0123", "Sam", "Johns");

        userService.save(user);

        verify(userRepository, times(1)).save(user);

    }


    @Test
    public void updateUser() {
        when(userRepository.save(any(User.class))).thenReturn((
                new User(1L, "SamMJ", "0123", "Sam", "Johns")));

        User user = new User(1L, "SamMJ", "0123", "Sam00", "Johns00");
        userService.save(user);

        assertThat(user.getFirst_name()).isEqualTo("Sam00");
        assertThat(user.getLast_name()).isEqualTo("Johns00");

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
    public void delete_user_by_username() {
        User user1 = new User(1L, "SamMJ", "0123", "Sam", "Johns");
        userRepository.deleteByUsername(user1.getUsername());
        User deletedUser = userRepository.findByUsername("SamMJ");
        assertThat(deletedUser).isNull();
    }


    @Test
    public void delete_user_by_id() {
        User user1 = new User(1L, "SamMJ", "0123", "Sam", "Johns");
        userRepository.deleteUserById(user1.getId());
        User deletedUser = userRepository.findUserById(1L);
        assertThat(deletedUser).isNull();
    }


}
