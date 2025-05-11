package it.epicode.progetto_settimanale_finale.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
