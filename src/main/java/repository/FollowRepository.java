package repository;

import ch.zli.vs.pulse.Model.Follow;
import ch.zli.vs.pulse.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollower(User follower);
    List<Follow> findByFollowed(User followed);
}
