package Model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String profileImage;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Media> mediaList;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Reaction> reactions;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private List<Follow> following;

    @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL)
    private List<Follow> followers;
}

