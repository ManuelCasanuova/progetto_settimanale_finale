package it.epicode.progetto_settimanale_finale.eventi;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EventoDTO {
    private String titolo;
    private String descrizione;
    private String luogo;
    private LocalDate data;
    private int postiDisponibili;
}
