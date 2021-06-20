package com.surena.RestService1.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Username may not be empty")
    private String username;

    @NotEmpty(message = "Password may not be empty")
    private String old_password;

    private String new_password;

    private String first_name;

    private String last_name;

    @CreationTimestamp
    private LocalDateTime create_date;

    @UpdateTimestamp
    private LocalDateTime modified_date;

    public User() {
    }

    public User(Long id, String username, String old_password, String new_password, String first_name, String last_name, LocalDateTime create_date, LocalDateTime modified_date) {
        this.id = id;
        this.username = username;
        this.old_password = old_password;
        this.new_password = new_password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.create_date = create_date;
        this.modified_date = modified_date;
    }

    public User(Long id, String username, String old_password, String new_password, String first_name, String last_name) {
        this.id = id;
        this.username = username;
        this.old_password = old_password;
        this.new_password = new_password;
        this.first_name = first_name;
        this.last_name = last_name;
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

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String password) {
        this.old_password = password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
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


    /**
     * @Override equals for save_user method in UserServiceTest class.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(id, user.id)) return false;
        if (!Objects.equals(username, user.username)) return false;
        if (!Objects.equals(old_password, user.old_password)) return false;
        if (!Objects.equals(new_password, user.new_password)) return false;
        if (!Objects.equals(first_name, user.first_name)) return false;
        if (!Objects.equals(last_name, user.last_name)) return false;
        if (!Objects.equals(create_date, user.create_date)) return false;
        return Objects.equals(modified_date, user.modified_date);
    }


}
