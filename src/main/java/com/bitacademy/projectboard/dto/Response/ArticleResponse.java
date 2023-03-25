package com.bitacademy.projectboard.dto.Response;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import com.bitacademy.projectboard.dto.ArticleDto;

public record ArticleResponse(
        Long id,
        String title,
        String content,
        String hashtag,
        Long hit,
        LocalDateTime createdAt,
        String email,
        String nickname
) {

    public static ArticleResponse of(Long id, String title, String content, String hashtag, Long hit, LocalDateTime createdAt, String email, String nickname) {
        return new ArticleResponse(id, title, content, hashtag, hit, createdAt, email, nickname);
    }

    public static ArticleResponse from(ArticleDto dto) {
    	// 작성자 보여주는 것 (nickname 없으면 id) 
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return new ArticleResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.hashtag(),
                dto.hit(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname
        );
    }

}
