package it.epicode.progetto_settimanale_finale.eventi;

import it.epicode.progetto_settimanale_finale.auth.user.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/evento")
public class EventoController {


    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public List<Evento> getEventi() {
        return eventoService.getEventi();
    }

    @GetMapping("/{id}")
    public Evento getEvento(@PathVariable Long id) {
        return eventoService.getEvento(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ORGANIZER')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvento(@RequestBody EventoDTO eventoDTO, @AuthenticationPrincipal AppUser user) {
        eventoService.createEvento(eventoDTO, user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ORGANIZER')")
    public void updateEvento(@PathVariable Long id, @RequestBody EventoDTO eventoDTO, @AuthenticationPrincipal AppUser user) {
        Evento evento = eventoService.getEvento(id);
        if (!evento.getOrganizzatore().getId().equals(user.getId())) {
            throw new AccessDeniedException("Organizzatore non autorizzato a modificare questo evento.");
        }
        eventoService.updateEventoDTO(evento, eventoDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ORGANIZER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvento(@PathVariable Long id, @AuthenticationPrincipal AppUser user) {
        Evento evento = eventoService.getEvento(id);
        if (!evento.getOrganizzatore().getId().equals(user.getId())) {
            throw new AccessDeniedException("Organizzatore non autorizzato a eliminare questo evento.");
        }
        eventoService.deleteEvento(id);
    }
}