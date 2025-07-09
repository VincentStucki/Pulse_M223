package ch.clip.sec.authapi.service;

import ch.clip.sec.authapi.model.Media;
import ch.clip.sec.authapi.model.User;
import ch.clip.sec.authapi.repo.MediaRepository;

import ch.clip.sec.authapi.repo.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MediaService {
    private final MediaRepository mediaRepository;
    private final UserRepository userRepostory;

    public MediaService(MediaRepository mediaRepository, UserRepository userRepository) {
        this.mediaRepository = mediaRepository;
        this.userRepostory = userRepository;
    }

    public Media save(Media media, @AuthenticationPrincipal User user) {
        media.setCreator(user);
        return mediaRepository.save(media);
    }

    public Optional<Media> findById(Long id) {
        return mediaRepository.findById(id);
    }

    public boolean delete(Long id, @AuthenticationPrincipal User user) {
        Optional<Media> mediaOpt = mediaRepository.findById(id);

        if (mediaOpt.isEmpty()) return false;

        Media media = mediaOpt.get();
        if (media.getCreator().getId() != user.getId()) {
            return false;
        }

        mediaRepository.deleteById(id);
        return true;
    }

    public Optional<Media> update(Long mediaId, Media updatedMedia, User user) {
        Optional<Media> existingOpt = mediaRepository.findById(mediaId);

        if (existingOpt.isEmpty()) return Optional.empty();

        Media existing = existingOpt.get();
        if (existing.getCreator().getId() != user.getId()) {
            return Optional.empty();
        }

        // Nur erlaubte Felder aktualisieren
        existing.setType(updatedMedia.getType());
        existing.setContent(updatedMedia.getContent());

        return Optional.of(mediaRepository.save(existing));
    }


    public List<Media> findByUserId(Long id) {
        return mediaRepository.findAllByCreatorId(id);
    }

}
