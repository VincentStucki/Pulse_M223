package service;

import ch.zli.vs.pulse.Model.Follow;
import ch.zli.vs.pulse.Model.User;
import ch.zli.vs.pulse.repository.FollowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {
    private final FollowRepository repo;

    public FollowService(FollowRepository repo) {
        this.repo = repo;
    }

    public Follow follow(Follow follow) {
        return repo.save(follow);
    }

    public void unfollow(Follow follow) {
        repo.delete(follow);
    }

    public List<Follow> getFollowers(User user) {
        return repo.findByFollowed(user);
    }

    public List<Follow> getFollowing(User user) {
        return repo.findByFollower(user);
    }
}
