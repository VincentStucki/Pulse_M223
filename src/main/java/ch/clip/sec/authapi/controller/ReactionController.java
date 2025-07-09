package ch.clip.sec.authapi.controller;

import ch.clip.sec.authapi.model.Reaction;
import ch.clip.sec.authapi.model.ReactionDto;
import ch.clip.sec.authapi.model.User;
import ch.clip.sec.authapi.service.ReactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/reactions")
public class ReactionController {

    private final ReactionService service;

    public ReactionController(ReactionService service) {
        this.service = service;
    }

    // Reaktion auf Media erstellen
    @PostMapping("/media/{mediaId}")
    public ResponseEntity<?> createReaction(
            @PathVariable Long mediaId,
            @RequestBody Reaction reaction,
            @AuthenticationPrincipal User user) {

        Optional<Reaction> result = service.createReaction(mediaId, reaction, user);

        if (result.isPresent()) {
            return ResponseEntity.ok(ReactionDto.from(result.get()));
        } else {
            return ResponseEntity.badRequest().body("Ungültiges Media");
        }
    }


    // Antwort auf eine Reaktion erstellen
    @PostMapping("/{reactionId}/reply")
    public ResponseEntity<?> replyToReaction(@PathVariable Long reactionId, @RequestBody Reaction reply, @AuthenticationPrincipal User user) {

        Optional<Reaction> result = service.replyToReaction(reactionId, reply, user);

        if (result.isPresent()) {
            return ResponseEntity.ok(ReactionDto.from(result.get()));
        } else {
            return ResponseEntity.badRequest().body("Ungültige Parent Reaction");
        }
    }


    // Details zu einer Reaktion holen
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Reaction> reactionOpt = service.findById(id);

        if (reactionOpt.isPresent()) {
            return ResponseEntity.ok(ReactionDto.from(reactionOpt.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
