package it.epicode.progetto_settimanale_finale.auth;


import it.epicode.progetto_settimanale_finale.auth.user.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private Role role;
}
