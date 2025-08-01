package org.turing.auth;

import java.util.List;
import org.turing.model.Utente;

/**
 * Classe di autenticazione, in futuro si potrebbe fare con SpringSecurity
 */
public class Autenticazione {
    public static boolean verificaLogin(String username, String password) {
        List<Utente> utenti = List.of(
                new Utente("admin", "admin123"),
                new Utente("user", "user123")
        );

        return utenti.stream()
                .anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
    }
}