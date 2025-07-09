package ch.clip.sec.authapi.controller;

import ch.clip.sec.authapi.model.Follow;
import ch.clip.sec.authapi.model.User;
import ch.clip.sec.authapi.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService service;

    public FollowController(FollowService service) {
        this.service = service;
    }

    @PostMapping("/{followedId}")
    public ResponseEntity<?> follow(@PathVariable Long followedId, @AuthenticationPrincipal User user) {
        Optional<Follow> result = service.follow(user, followedId);

        if (result.isPresent()) {
            return ResponseEntity.ok("Gefolgt");
        } else {
            return ResponseEntity.badRequest().body("Folgen fehlgeschlagen (existiert nicht oder bereits gefolgt)");
        }
    }

    @DeleteMapping("/{followedId}")
    public ResponseEntity<?> unfollow(@PathVariable Long followedId, @AuthenticationPrincipal User user) {
        boolean unfollowed = service.unfollow(user, followedId);

        if (unfollowed) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body("Entfolgen fehlgeschlagen (nicht gefunden)");
        }
    }

    @GetMapping("/users/{id}/followers")
    public ResponseEntity<?> getFollowers(@PathVariable Long id) {
        List<User> followers = service.getFollowers(id);
        return ResponseEntity.ok(followers.stream().map(User::getUsername).toList());
    }

    @GetMapping("/users/{id}/following")
    public ResponseEntity<?> getFollowing(@PathVariable Long id) {
        List<User> following = service.getFollowing(id);
        return ResponseEntity.ok(following.stream().map(User::getUsername).toList());
    }

}
