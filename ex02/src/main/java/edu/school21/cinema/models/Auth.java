package edu.school21.cinema.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Auth {

    String email;
    String date;
    String time;
    String ip;
}
