package ch.clip.sec.authapi.service;

import ch.clip.sec.authapi.model.Media;
import ch.clip.sec.authapi.model.Reaction;
import ch.clip.sec.authapi.model.User;
import ch.clip.sec.authapi.repo.MediaRepository;
import ch.clip.sec.authapi.repo.ReactionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReactionService {
    private final ReactionRepository reactionRepo;
    private final MediaRepository mediaRepo;

    public ReactionService(ReactionRepository reactionRepo, MediaRepository mediaRepo) {
        this.reactionRepo = reactionRepo;
        this.mediaRepo = mediaRepo;
    }

    public Optional<Reaction> findById(Long id) {
        return reactionRepo.findById(id);
    }

    public Optional<Reaction> replyToReaction(Long parentId, Reaction reply, User user) {
        Optional<Reaction> parentOpt = reactionRepo.findById(parentId);
        if (parentOpt.isEmpty()) return Optional.empty();

        reply.setAuthor(user);
        reply.setParentReaction(parentOpt.get());
        reply.setMedia(parentOpt.get().getMedia()); // selbes Media vererben
        return Optional.of(reactionRepo.save(reply));
    }

    public Optional<Reaction> createReaction(Long mediaId, Reaction reaction, User user) {
        Optional<Media> mediaOpt = mediaRepo.findById(mediaId);
        if (mediaOpt.isEmpty()) return Optional.empty();

        reaction.setAuthor(user);
        reaction.setMedia(mediaOpt.get());
        return Optional.of(reactionRepo.save(reaction));
    }
}
