package edu.school21.cinema.models;

import com.sun.jmx.remote.internal.ArrayQueue;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayDeque;
import java.util.ArrayList;

@Data
@NoArgsConstructor
public class User {

    Long id;
    String name;
    String surname;
    String phone;
    String email;
    String password;
    String avatar;

    public User(String name, String surname, String phone, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.avatar = "";
    }
}
