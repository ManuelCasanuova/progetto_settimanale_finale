package it.epicode.progetto_settimanale_finale.eventi;

import it.epicode.progetto_settimanale_finale.auth.user.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public List<Evento> getEventi() {
        return eventoRepository.findAll();
    }

    public Evento getEvento(Long id) {
        return eventoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento non trovato"));
    }

    public void deleteEvento(Long id) {
        getEvento(id);
        eventoRepository.deleteById(id);
    }

    public void createEvento(EventoDTO dto, AppUser organizzatore) {
        Evento evento = new Evento();
        evento.setTitolo(dto.getTitolo());
        evento.setDescrizione(dto.getDescrizione());
        evento.setLuogo(dto.getLuogo());
        evento.setData(dto.getData());
        evento.setPostiDisponibili(dto.getPostiDisponibili());
        evento.setOrganizzatore(organizzatore);
        eventoRepository.save(evento);
    }

    public void updateEventoDTO(Evento evento, EventoDTO dto) {
        evento.setTitolo(dto.getTitolo());
        evento.setDescrizione(dto.getDescrizione());
        evento.setLuogo(dto.getLuogo());
        evento.setData(dto.getData());
        evento.setPostiDisponibili(dto.getPostiDisponibili());
        eventoRepository.save(evento);
    }

    public EventoResponse toResponse(Evento evento) {
        EventoResponse response = new EventoResponse();
        response.setId(evento.getId());
        response.setTitolo(evento.getTitolo());
        response.setDescrizione(evento.getDescrizione());
        response.setLuogo(evento.getLuogo());
        response.setData(evento.getData());
        response.setPostiDisponibili(evento.getPostiDisponibili());
        response.setPostiPrenotati(evento.getPostiPrenotati());
        response.setOrganizzatore(evento.getOrganizzatore().getUsername());
        return response;
    }
}