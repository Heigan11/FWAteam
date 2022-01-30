package edu.school21.cinema.repositories;

import edu.school21.cinema.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> showTable(){
        return jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<>(User.class));
    }

    public Optional<User> findByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM users WHERE email=?", new Object[]{email}, new BeanPropertyRowMapper<>(User.class))
                .stream().findAny();
    }

    public User saveUser(User user) throws SQLException {
        String SQL = "insert into users(name, surname, phone, email, password) values (?, ?, ?, ?, ?)";
        try (
                Connection connection = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL,
                        Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());

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

    public void update(String email, User updatedUser) {
        jdbcTemplate.update("UPDATE users SET name=?, surname=?, phone=?, email=?, password=? WHERE email=?", updatedUser.getName(),
                updatedUser.getSurname(), updatedUser.getPhone(), updatedUser.getEmail(), updatedUser.getPassword(), email);
    }

    public void delete(String email) {
        jdbcTemplate.update("DELETE FROM users WHERE email=?", email);
    }
}
