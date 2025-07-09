package service;

import ch.zli.vs.pulse.Model.Reaction;
import ch.zli.vs.pulse.repository.ReactionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReactionService {
    private final ReactionRepository repo;

    public ReactionService(ReactionRepository repo) {
        this.repo = repo;
    }

    public Reaction save(Reaction reaction) {
        return repo.save(reaction);
    }

    public Optional<Reaction> findById(Long id) {
        return repo.findById(id);
    }
}
