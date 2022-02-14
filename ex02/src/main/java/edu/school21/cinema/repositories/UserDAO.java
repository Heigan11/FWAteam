package edu.school21.cinema.repositories;

import edu.school21.cinema.models.Auth;
import edu.school21.cinema.models.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT * FROM users WHERE email=?", new Object[]{email}, new BeanPropertyRowMapper<>(User.class)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void updateAvatar(String email, String avatarPath) {
        jdbcTemplate.update("update users set avatar = ? where email = ?", avatarPath, email);
    }

    public User saveUser(User user) throws SQLException {
        String SQL = "insert into users(name, surname, phone, email, password, avatar) values (?, ?, ?, ?, ?, ?)";
        try (
                Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL,
                        Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setString(6, "");

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
        return user;
    }

    public Auth saveAuth(Auth auth) throws SQLException {
        String SQL = "insert into auth(email, date, time, ip) values (?, ?, ?, ?)";
        try (
                Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL);
        ) {
            statement.setString(1, auth.getEmail());
            statement.setString(2, auth.getDate());
            statement.setString(3, auth.getTime());
            statement.setString(4, auth.getIp());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
        }
        return auth;
    }

    public List<Auth> getAuth(String email) {
        return jdbcTemplate.query("SELECT * FROM auth WHERE email=?", new Object[]{email}, new BeanPropertyRowMapper<>(Auth.class));
    }
}
