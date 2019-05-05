package com.suez.acoustic_logger.entity;

import io.jsonwebtoken.Jwts;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
public class User {
    @Id
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String role;
    private String token;
}
