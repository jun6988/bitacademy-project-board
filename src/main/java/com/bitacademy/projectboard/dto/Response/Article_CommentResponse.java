package com.bitacademy.projectboard.dto.Response;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.bitacademy.projectboard.dto.Article_CommentDto;

public record Article_CommentResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        String email,
        String nickname,
        String userId
) {

    public static Article_CommentResponse of(Long id, String content, LocalDateTime createdAt, String email, String nickname, String userId) {
        return new Article_CommentResponse(id, content, createdAt, email, nickname, userId);
    }

    public static Article_CommentResponse from(Article_CommentDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return new Article_CommentResponse(
                dto.id(),
                dto.content(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname,
                dto.userAccountDto().userId()
        );
    }

}
