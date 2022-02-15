package edu.school21.cinema.services;

import edu.school21.cinema.models.Auth;
import edu.school21.cinema.models.User;
import edu.school21.cinema.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
public class UserHandler {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean create(String name, String surname, String phone, String email, String password) throws SQLException {
        if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(surname) &&
                !StringUtils.isEmpty(phone) && !StringUtils.isEmpty(email) && !StringUtils.isEmpty(password)) {
            if (!userRepository.findByEmail(email).isPresent()){
                String hashedPassword = passwordEncoder.encode(password);
                User user = new User(name, surname, phone, email, hashedPassword);
                userRepository.saveUser(user);
                return true;
            }
        }
        return false;
    }

    public boolean read(String email, String password){
        if(!StringUtils.isEmpty(email) && !StringUtils.isEmpty(password)){
            Optional<User> user = userRepository.findByEmail(email);
            return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
        }
        return false;
    }

    public Optional<User> get(String email){
        if(!StringUtils.isEmpty(email))
            return userRepository.findByEmail(email);
        return Optional.empty();
    }

    public void setAuth(String email, String address) throws SQLException {
        if (!StringUtils.isEmpty(email) && !StringUtils.isEmpty(address)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Auth auth = new Auth().setEmail(email)
                    .setDate(dateFormat.format(new Date()).split(" ")[0])
                    .setTime(dateFormat.format(new Date()).split(" ")[1])
                    .setIp(address);
            userRepository.saveAuth(auth);
        }
    }

    public ArrayList<Auth> getAuth(String email){
        ArrayList<Auth> authArray = (ArrayList<Auth>) userRepository.getAuth(email);
        while (authArray.size() > 4)
            authArray.remove(0);
        return authArray;
    }

    public void setAvatar(String email, String avatarPath){
        if (!StringUtils.isEmpty(email) && !StringUtils.isEmpty(avatarPath))
            userRepository.updateAvatar(email, avatarPath);
    }
}
