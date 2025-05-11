package it.epicode.progetto_settimanale_finale.eventi;


import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}