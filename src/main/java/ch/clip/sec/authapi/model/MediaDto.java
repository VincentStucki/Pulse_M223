package ch.clip.sec.authapi.model;

public record MediaDto(Long id, String type, String content, Long creatorId, String creatorName) {
    public static MediaDto from(Media media) {
        return new MediaDto(
                media.getId(),
                media.getType(),
                media.getContent(),
                media.getCreator() != null ? media.getCreator().getId() : null,
                media.getCreator() != null ? media.getCreator().getName() : null
        );
    }
}
