package edu.school21.cinema.config;

import edu.school21.cinema.repositories.UserRepository;
import edu.school21.cinema.services.UserHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@ComponentScan("edu.school21.cinema")
@PropertySource("classpath:../application.properties")
public class SpringConfig {

    @Value("${spring.datasource.url}")
    private String datasource_url;
    @Value("${spring.datasource.username}")
    private String datasource_username;
    @Value("${spring.datasource.password}")
    private String datasource_password;
    @Value("${spring.datasource.driver.name}")
    private String datasource_driver_name;
    @Value("${storage.path}")
    private String storage_path;


    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(datasource_driver_name);
        dataSource.setUrl(datasource_url);
        dataSource.setUsername(datasource_username);
        dataSource.setPassword(datasource_password);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public UserRepository userDAO() {
        return new UserRepository(jdbcTemplate());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserHandler userHandler(){
        return new UserHandler(userDAO(),passwordEncoder());
    }

    @Bean
    public String getStoragePath(){
        return storage_path;
    }
}
