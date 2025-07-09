package repository;

import ch.zli.vs.pulse.Model.Media;
import ch.zli.vs.pulse.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByCreator(User creator);
}
