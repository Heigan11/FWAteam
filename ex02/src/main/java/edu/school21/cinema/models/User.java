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
//    ArrayDeque<Auth> authHistory = new ArrayDeque<>();


    public User(String name, String surname, String phone, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

//    public void addToAuthHistory(Auth auth){
//        if (authHistory.size() >= 4){
//            System.out.println("full");
//            authHistory.pollLast();
//            authHistory.push(auth);
//        } else authHistory.push(auth);
//    }
}
