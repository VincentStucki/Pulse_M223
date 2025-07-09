package ch.clip.sec.authapi.repo;

import ch.clip.sec.authapi.model.Media;
import ch.clip.sec.authapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findAllByCreatorId(Long id);

}
