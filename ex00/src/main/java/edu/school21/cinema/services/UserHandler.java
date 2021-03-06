package edu.school21.cinema.services;

import edu.school21.cinema.models.User;
import edu.school21.cinema.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.Optional;

public class UserHandler {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean create(String name, String surname, String phone, String email, String password) throws SQLException {
        if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(surname) &&
                !StringUtils.isEmpty(phone) && !StringUtils.isEmpty(email) && !StringUtils.isEmpty(password)) {
            if (!userDAO.findByEmail(email).isPresent()){
                String hashedPassword = passwordEncoder.encode(password);
                User user = new User(name, surname, phone, email, hashedPassword);
                userDAO.saveUser(user);
                return true;
            }
        }
        return false;
    }

    public boolean read(String email, String password){
        if(!StringUtils.isEmpty(email) && !StringUtils.isEmpty(password)){
            Optional<User> user = userDAO.findByEmail(email);
            return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
        }
        return false;
    }

    public Optional<User> get(String email){
        if(!StringUtils.isEmpty(email))
            return userDAO.findByEmail(email);
        return Optional.empty();
    }

}
