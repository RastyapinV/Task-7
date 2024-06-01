package ru.itmentor.spring.boot_security.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Data
@Entity
@Table(name = "users", schema = "public", catalog = "test_db")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Index.class)
    private Long id;

    @NotEmpty(message = "Name should not be empty.")
    @NotEmpty(message = "Name should not be empty.", groups = Save.class)
    @Size(min = 2, max = 1478, message = "Name should be longer then 2 characters.")
    @Size(min = 2, max = 1478, message = "Name should be longer then 2 characters.", groups = Save.class)
    @JsonView(Views.Edit.class)
    private String name;

    @Min(value = 0, message = "Age should be greater then 0.")
    @Min(value = 0, message = "Age should be greater then 0.", groups = Save.class)
    @JsonView(Views.Edit.class)
    private int age;

    @NotEmpty(message = "Email should not be empty.")
    @NotEmpty(message = "Email should not be empty.", groups = Save.class)
    @Email(message = "Email should be valid.")
    @Email(message = "Email should be valid.", groups = Save.class)
    @JsonView(Views.Edit.class)
    private String email;

    @NotEmpty(message = "Username should be not empty.", groups = Save.class)
    @Size(min = 5, message = "Username should be longer then 5 characters.", groups = Save.class)
    @Size(max = 15, message = "Username should be shorter then 15 characters.", groups = Save.class)
    @JsonView(Views.Save.class)
    private String username;

    @NotEmpty(message = "Password should be not empty.", groups = Save.class)
    @Size(min = 8, message = "Password should be longer then 8 characters.", groups = Save.class)
    @JsonView(Views.Save.class)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonView(Views.Save.class)
    private Set<Role> roles;

    public interface Save {}

    public static class Views {
        public static class Edit {}
        public static class Index extends Edit {}
        public static class Save extends Index{}
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "\n{name: " + getName() + ";\n"
                + "age: " + getAge() + ";\n"
                + "email" + getEmail() + ";}\n";
    }

}
