package controller;

import ch.zli.vs.pulse.Model.Media;
import ch.zli.vs.pulse.service.MediaService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/media")
public class MediaController {
    private final MediaService service;

    public MediaController(MediaService service) {
        this.service = service;
    }

    @PostMapping
    public Media create(@RequestBody Media media) {
        return service.save(media);
    }

    @GetMapping("/{id}")
    public Optional<Media> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
