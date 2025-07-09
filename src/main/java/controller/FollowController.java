package controller;

import ch.zli.vs.pulse.Model.Follow;
import ch.zli.vs.pulse.service.FollowService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/follow")
public class FollowController {
    private final FollowService service;

    public FollowController(FollowService service) {
        this.service = service;
    }

    @PostMapping
    public Follow follow(@RequestBody Follow follow) {
        return service.follow(follow);
    }

    @PostMapping("/unfollow")
    public void unfollow(@RequestBody Follow follow) {
        service.unfollow(follow);
    }
}
