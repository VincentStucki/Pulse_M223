package service;

import ch.zli.vs.pulse.Model.User;
import ch.zli.vs.pulse.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public Optional<User> getUser(Long id) {
        return repo.findById(id);
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }
}