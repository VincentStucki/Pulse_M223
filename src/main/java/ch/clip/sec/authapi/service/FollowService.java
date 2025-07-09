package ch.clip.sec.authapi.service;

import ch.clip.sec.authapi.model.Follow;
import ch.clip.sec.authapi.model.User;
import ch.clip.sec.authapi.repo.FollowRepository;
import ch.clip.sec.authapi.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowService(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    public Optional<Follow> follow(User follower, Long followedId) {
        Optional<User> followedOpt = userRepository.findById(followedId);
        if (followedOpt.isEmpty() || follower.getId() == followedId) {
            return Optional.empty();
        }

        // Prüfen, ob schon gefolgt
        if (followRepository.findByFollowerAndFollowed(follower, followedOpt.get()).isPresent()) {
            return Optional.empty(); // bereits gefolgt
        }

        Follow follow = Follow.builder()
                .follower(follower)
                .followed(followedOpt.get())
                .build();

        return Optional.of(followRepository.save(follow));
    }

    public boolean unfollow(User follower, Long followedId) {
        Optional<User> followedOpt = userRepository.findById(followedId);
        if (followedOpt.isEmpty()) {
            return false;
        }

        Optional<Follow> follow = followRepository.findByFollowerAndFollowed(follower, followedOpt.get());
        if (follow.isEmpty()) {
            return false;
        }

        followRepository.delete(follow.get());
        return true;
    }

    public List<User> getFollowers(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) return List.of();

        return followRepository.findAllByFollowed(userOpt.get()).stream()
                .map(Follow::getFollower)
                .collect(Collectors.toList());
    }

    public List<User> getFollowing(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) return List.of();

        return followRepository.findAllByFollower(userOpt.get()).stream()
                .map(Follow::getFollowed)
                .collect(Collectors.toList());
    }
}
