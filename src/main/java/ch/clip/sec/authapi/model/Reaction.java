package ch.clip.sec.authapi.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // "like" oder "comment"

    @Column(length = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "media_id")
    private Media media;

    @ManyToOne
    @JoinColumn(name = "parent_reaction_id")
    private Reaction parentReaction;

    @OneToMany(mappedBy = "parentReaction", cascade = CascadeType.ALL)
    private List<Reaction> replies;
}

