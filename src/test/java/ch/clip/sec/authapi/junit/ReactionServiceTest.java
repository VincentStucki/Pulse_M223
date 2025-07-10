package ch.clip.sec.authapi.junit;

import ch.clip.sec.authapi.model.Media;
import ch.clip.sec.authapi.model.Reaction;
import ch.clip.sec.authapi.model.User;
import ch.clip.sec.authapi.repo.MediaRepository;
import ch.clip.sec.authapi.repo.ReactionRepository;
import ch.clip.sec.authapi.service.ReactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReactionServiceTest {

    @Mock
    private ReactionRepository reactionRepository;

    @Mock
    private MediaRepository mediaRepository;

    @InjectMocks
    private ReactionService reactionService;

    @Test
    void testCreateReaction_success() {
        User user = new User();
        user.setId(1L);
        Media media = new Media();
        media.setId(10L);
        Reaction reaction = new Reaction();

        when(mediaRepository.findById(10L)).thenReturn(Optional.of(media));
        when(reactionRepository.save(any(Reaction.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<Reaction> result = reactionService.createReaction(10L, reaction, user);

        assertTrue(result.isPresent());
        assertEquals(user, result.get().getAuthor());
        assertEquals(media, result.get().getMedia());
    }

    @Test
    void testCreateReaction_mediaNotFound() {
        User user = new User();
        Reaction reaction = new Reaction();

        when(mediaRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Reaction> result = reactionService.createReaction(999L, reaction, user);

        assertTrue(result.isEmpty());
    }

    @Test
    void testReplyToReaction_success() {
        User user = new User();
        user.setId(1L);
        Media media = new Media();
        media.setId(20L);
        Reaction parent = new Reaction();
        parent.setId(100L);
        parent.setMedia(media);

        Reaction reply = new Reaction();

        when(reactionRepository.findById(100L)).thenReturn(Optional.of(parent));
        when(reactionRepository.save(any(Reaction.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<Reaction> result = reactionService.replyToReaction(100L, reply, user);

        assertTrue(result.isPresent());
        assertEquals(parent, result.get().getParentReaction());
        assertEquals(media, result.get().getMedia());
        assertEquals(user, result.get().getAuthor());
    }
}
