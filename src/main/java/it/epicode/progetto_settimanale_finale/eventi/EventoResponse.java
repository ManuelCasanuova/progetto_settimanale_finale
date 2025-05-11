package it.epicode.progetto_settimanale_finale.eventi;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoResponse {
    private Long id;
    private String titolo;
    private String descrizione;
    private String luogo;
    private LocalDate data;
    private int postiDisponibili;
    private int postiPrenotati;
    private String organizzatore;
}
