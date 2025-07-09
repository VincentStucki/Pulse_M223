package ch.clip.sec.authapi.model;


import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // z. B. "post", "reel", "short"

    @Column(length = 1000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(mappedBy = "media", cascade = CascadeType.ALL)
    private List<Reaction> reactions;
}

