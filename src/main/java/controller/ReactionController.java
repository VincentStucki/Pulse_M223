package controller;

import ch.zli.vs.pulse.Model.Reaction;
import ch.zli.vs.pulse.service.ReactionService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/reactions")
public class ReactionController {
    private final ReactionService service;

    public ReactionController(ReactionService service) {
        this.service = service;
    }

    @PostMapping
    public Reaction create(@RequestBody Reaction reaction) {
        return service.save(reaction);
    }

    @GetMapping("/{id}")
    public Optional<Reaction> getById(@PathVariable Long id) {
        return service.findById(id);
    }
}
