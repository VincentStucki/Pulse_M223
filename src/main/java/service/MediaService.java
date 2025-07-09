package service;

import ch.zli.vs.pulse.Model.Media;
import ch.zli.vs.pulse.Model.User;
import ch.zli.vs.pulse.repository.MediaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MediaService {
    private final MediaRepository repo;

    public MediaService(MediaRepository repo) {
        this.repo = repo;
    }

    public Media save(Media media) {
        return repo.save(media);
    }

    public Optional<Media> findById(Long id) {
        return repo.findById(id);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<Media> findByUser(User user) {
        return repo.findByCreator(user);
    }
}
