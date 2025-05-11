package it.epicode.progetto_settimanale_finale.auth;


import it.epicode.progetto_settimanale_finale.auth.user.AppUser;
import it.epicode.progetto_settimanale_finale.auth.user.AppUserService;
import it.epicode.progetto_settimanale_finale.auth.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class AuthRunner implements ApplicationRunner {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<AppUser> adminUser = appUserService.findByUsername("admin");
        if (adminUser.isEmpty()) {
            appUserService.registerUser("admin", "adminpwd", Set.of(Role.ROLE_ADMIN));
        }

        Optional<AppUser> normalUser = appUserService.findByUsername("user");
        if (normalUser.isEmpty()) {
            appUserService.registerUser("user", "userpwd", Set.of(Role.ROLE_USER));
        }

        Optional<AppUser> normalOrganizer = appUserService.findByUsername("organizer");
        if (normalUser.isEmpty()) {
            appUserService.registerUser("organizer", "organizerpwd", Set.of(Role.ROLE_ORGANIZER));
        }
    }
}
