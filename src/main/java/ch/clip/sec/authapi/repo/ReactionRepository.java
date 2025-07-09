package ch.clip.sec.authapi.repo;

import ch.clip.sec.authapi.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
}
