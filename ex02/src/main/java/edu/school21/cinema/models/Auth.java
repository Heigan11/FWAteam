package edu.school21.cinema.models;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class Auth {

    private String email;
    private String date;
    private String time;
    private String ip;
}
