package it.epicode.progetto_settimanale_finale.prenotazione;



import it.epicode.progetto_settimanale_finale.auth.user.AppUser;
import it.epicode.progetto_settimanale_finale.eventi.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByPartecipante(AppUser partecipante);
    boolean existsByEventoAndPartecipante(Evento evento, AppUser partecipante);
    void deleteByEventoAndPartecipante(Evento evento, AppUser partecipante);
}