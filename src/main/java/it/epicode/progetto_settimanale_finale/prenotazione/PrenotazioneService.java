package it.epicode.progetto_settimanale_finale.prenotazione;


import it.epicode.progetto_settimanale_finale.auth.user.AppUser;
import it.epicode.progetto_settimanale_finale.eventi.Evento;
import it.epicode.progetto_settimanale_finale.eventi.EventoRepository;
import it.epicode.progetto_settimanale_finale.eventi.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private EventoRepository eventoRepository;

    public List<PrenotazioneResponse> getPrenotazioniPerPartecipante(AppUser partecipante) {
        if (partecipante == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utente non trovato");
        }

        List<Prenotazione> prenotazioni = prenotazioneRepository.findByPartecipante(partecipante);
        if (prenotazioni.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessuna prenotazione trovata");
        }

        return prenotazioni.stream()
                .map(this::getPrenotazioneResponse)
                .collect(Collectors.toList());
    }

    public Prenotazione getPrenotazioneById(Long id) {
        return prenotazioneRepository.findById(id).orElseThrow(() -> new  ResponseStatusException(HttpStatus.NOT_FOUND, "Prenotazione non trovata"));
    }

    public void createPrenotazione(Evento evento, AppUser partecipante) {
        if (evento.getPostiPrenotati() >= evento.getPostiDisponibili()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Posti esauriti");
        }

        if (prenotazioneRepository.existsByEventoAndPartecipante(evento, partecipante)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Hai già prenotato questo evento");
        }

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setPartecipante(partecipante);
        prenotazione.setEvento(evento);

        evento.setPostiPrenotati(evento.getPostiPrenotati() + 1);

        prenotazioneRepository.save(prenotazione);
        eventoRepository.save(evento);
    }

    public void deletePrenotazione(Prenotazione prenotazione) {
        Evento evento = prenotazione.getEvento();

        if (!prenotazioneRepository.existsByEventoAndPartecipante(evento, prenotazione.getPartecipante())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non hai prenotato questo evento");
        }

        int postiPrenotati = evento.getPostiPrenotati();
        if (postiPrenotati > 0) {
            evento.setPostiPrenotati(postiPrenotati - 1);
        }

        prenotazioneRepository.delete(prenotazione);
        eventoRepository.save(evento);
    }

    public PrenotazioneResponse getPrenotazioneResponse(Prenotazione prenotazione) {
        PrenotazioneResponse prenotazioneResponse = new PrenotazioneResponse();
        prenotazioneResponse.setId(prenotazione.getId());
        prenotazioneResponse.setPartecipante(prenotazione.getPartecipante().getUsername());
        prenotazioneResponse.setEvento(eventoService.toResponse(prenotazione.getEvento()));
        return prenotazioneResponse;
    }
}
