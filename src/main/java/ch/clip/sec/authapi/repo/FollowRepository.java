package ch.clip.sec.authapi.repo;

import ch.clip.sec.authapi.model.Follow;
import ch.clip.sec.authapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerAndFollowed(User follower, User followed);
    void deleteByFollowerAndFollowed(User follower, User followed);

    List<Follow> findAllByFollowed(User followed);
    List<Follow> findAllByFollower(User follower);
}
