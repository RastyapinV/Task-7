package ru.itmentor.spring.boot_security.demo.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.DAO.UsersDAO;
import ru.itmentor.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UsersServiceImpl implements UsersService, UserDetailsService {

    private final UsersDAO usersDAO;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersDAO.findByUsername(username);
        return user.orElseThrow(() -> new UsernameNotFoundException(username + " not found."));
    }

    @Override
    public List<User> index() {
        return usersDAO.findAll();
    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersDAO.save(user);
    }

    @Override
    public User getUser(long id) {
        return usersDAO.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + id
                        + " not found."));
    }

    @Override
    public void update(long id, User updatedUser) {
        User user = getUser(id);
        updatedUser.setPassword(user.getPassword());
        updatedUser.setUsername(user.getUsername());
        updatedUser.setRoles(user.getRoles());
        usersDAO.update(id, updatedUser);
    }

    @Override
    public void delete(long id) {
        usersDAO.deleteById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return usersDAO.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found."));
    }

}
