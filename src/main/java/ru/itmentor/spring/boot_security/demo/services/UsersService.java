package ru.itmentor.spring.boot_security.demo.services;

import ru.itmentor.spring.boot_security.demo.models.User;

import java.util.List;

public interface UsersService{

    List<User> index();
    void save(User user);
    User getUser(long id);
    void update(long id, User updatedUser);
    void delete(long id);
    User getUserByUsername(String username);

}
