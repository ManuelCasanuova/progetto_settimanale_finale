package it.epicode.progetto_settimanale_finale.prenotazione;


import it.epicode.progetto_settimanale_finale.auth.user.AppUser;
import it.epicode.progetto_settimanale_finale.eventi.Evento;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Prenotazione")

public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "partecipante_id")
    private AppUser partecipante;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;
}