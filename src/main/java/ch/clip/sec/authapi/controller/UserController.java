package ch.clip.sec.authapi.controller;

import ch.clip.sec.authapi.model.MediaDto;
import ch.clip.sec.authapi.model.UserDto;
import ch.clip.sec.authapi.service.MediaService;
import ch.clip.sec.authapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final MediaService mediaService;

    public UserController(UserService userService, MediaService mediaService) {
        this.userService = userService;
        this.mediaService = mediaService;
    }

    // GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(UserDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/users/{id}/media
    @GetMapping("/{id}/media")
    public List<MediaDto> getUserMedia(@PathVariable Long id) {
        return mediaService.findByUserId(id)
                .stream()
                .map(MediaDto::from)
                .toList();
    }
}

