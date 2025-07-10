package ch.clip.sec.authapi.junit;

import ch.clip.sec.authapi.model.Follow;
import ch.clip.sec.authapi.model.User;
import ch.clip.sec.authapi.repo.FollowRepository;
import ch.clip.sec.authapi.repo.UserRepository;
import ch.clip.sec.authapi.service.FollowService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @Mock
    private FollowRepository followRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FollowService followService;

    @Test
    void testFollow_success() {
        User follower = new User();
        follower.setId(1L);
        User followed = new User();
        followed.setId(2L);

        when(userRepository.findById(2L)).thenReturn(Optional.of(followed));
        when(followRepository.findByFollowerAndFollowed(follower, followed)).thenReturn(Optional.empty());
        when(followRepository.save(any(Follow.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<Follow> result = followService.follow(follower, 2L);

        assertTrue(result.isPresent());
        assertEquals(followed, result.get().getFollowed());
    }

    @Test
    void testFollow_selfFollow_notAllowed() {
        User user = new User();
        user.setId(1L);

        Optional<Follow> result = followService.follow(user, 1L);
        assertTrue(result.isEmpty());
    }

    @Test
    void testUnfollow_success() {
        User follower = new User();
        follower.setId(1L);
        User followed = new User();
        followed.setId(2L);

        Follow follow = Follow.builder().follower(follower).followed(followed).build();

        when(userRepository.findById(2L)).thenReturn(Optional.of(followed));
        when(followRepository.findByFollowerAndFollowed(follower, followed)).thenReturn(Optional.of(follow));

        boolean result = followService.unfollow(follower, 2L);

        assertTrue(result);
        verify(followRepository).delete(follow);
    }
}
