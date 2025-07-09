package ch.clip.sec.authapi.controller;

import ch.clip.sec.authapi.model.Media;
import ch.clip.sec.authapi.model.MediaDto;
import ch.clip.sec.authapi.model.User;
import ch.clip.sec.authapi.service.MediaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public MediaDto create(@RequestBody Media media, @AuthenticationPrincipal User user) {
        return MediaDto.from(service.save(media, user));
    }


    @GetMapping("/{id}")
    public ResponseEntity<MediaDto> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(MediaDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        boolean deleted = service.delete(id, user);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Nicht erlaubt: Nicht dein Media");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMedia(@PathVariable Long id, @RequestBody Media updatedMedia, @AuthenticationPrincipal User user
    ) {
        Optional<Media> updated = service.update(id, updatedMedia, user);

        if (updated.isPresent()) {
            return ResponseEntity.ok(MediaDto.from(updated.get()));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Nicht erlaubt oder nicht gefunden");
        }
    }

}
