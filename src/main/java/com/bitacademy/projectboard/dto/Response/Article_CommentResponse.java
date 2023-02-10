package com.bitacademy.projectboard.dto.Response;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.bitacademy.projectboard.dto.Article_CommentDto;

public record Article_CommentResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        String email,
        String nickname
       
) implements Serializable {

    public static Article_CommentResponse of(Long id, String content, LocalDateTime createdAt, String email, String nickname) {
        return Article_CommentResponse.of(id, content, createdAt, email, nickname);
    }

    public static Article_CommentResponse from(Article_CommentDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return Article_CommentResponse.of(
                dto.id(),
                dto.content(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname
        );
    }
}
