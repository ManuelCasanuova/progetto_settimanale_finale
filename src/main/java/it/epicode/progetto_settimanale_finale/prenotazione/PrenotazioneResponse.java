package it.epicode.progetto_settimanale_finale.prenotazione;


import it.epicode.progetto_settimanale_finale.eventi.EventoResponse;
import lombok.Data;

@Data
public class PrenotazioneResponse {
    private long id;
    private String partecipante;
    private EventoResponse evento;
}
