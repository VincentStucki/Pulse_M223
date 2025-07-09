package ch.clip.sec.authapi.model;
import lombok.Data;

@Data
public class ReactionDto {
    private Long id;
    private String type;
    private String content;
    private String authorName;

    public static ReactionDto from(Reaction reaction) {
        ReactionDto dto = new ReactionDto();
        dto.setId(reaction.getId());
        dto.setType(reaction.getType());
        dto.setContent(reaction.getContent());
        dto.setAuthorName(reaction.getAuthor().getUsername()); // oder getEmail()
        return dto;
    }
}
