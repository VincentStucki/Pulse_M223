package ch.clip.sec.authapi.junit;

import ch.clip.sec.authapi.model.Media;
import ch.clip.sec.authapi.model.User;
import ch.clip.sec.authapi.repo.MediaRepository;
import ch.clip.sec.authapi.repo.UserRepository;
import ch.clip.sec.authapi.service.MediaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MediaServiceTest {

    @Mock
    private MediaRepository mediaRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MediaService mediaService;

    @Test
    void testSaveMedia_success() {
        User user = new User();
        user.setId(1L);
        Media media = new Media();

        when(mediaRepository.save(any(Media.class))).thenAnswer(inv -> inv.getArgument(0));

        Media result = mediaService.save(media, user);

        assertEquals(user, result.getCreator());
        verify(mediaRepository).save(media);
    }

    @Test
    void testDeleteMedia_success() {
        User user = new User();
        user.setId(1L);
        Media media = new Media();
        media.setId(10L);
        media.setCreator(user);

        when(mediaRepository.findById(10L)).thenReturn(Optional.of(media));

        boolean result = mediaService.delete(10L, user);

        assertTrue(result);
        verify(mediaRepository).deleteById(10L);
    }

    @Test
    void testDeleteMedia_wrongUser() {
        User correctUser = new User(); correctUser.setId(1L);
        User wrongUser = new User(); wrongUser.setId(2L);
        Media media = new Media(); media.setId(10L); media.setCreator(correctUser);

        when(mediaRepository.findById(10L)).thenReturn(Optional.of(media));

        boolean result = mediaService.delete(10L, wrongUser);

        assertFalse(result);
        verify(mediaRepository, never()).deleteById(any());
    }

    @Test
    void testUpdateMedia_success() {
        User user = new User(); user.setId(1L);
        Media original = new Media(); original.setId(5L); original.setCreator(user);
        Media update = new Media(); update.setType("video"); update.setContent("Updated Content");

        when(mediaRepository.findById(5L)).thenReturn(Optional.of(original));
        when(mediaRepository.save(any(Media.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<Media> result = mediaService.update(5L, update, user);

        assertTrue(result.isPresent());
        assertEquals("video", result.get().getType());
        assertEquals("Updated Content", result.get().getContent());
    }
}

