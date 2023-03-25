package com.bitacademy.projectboard.dto.request;

import java.util.Set;

import com.bitacademy.projectboard.dto.ArticleDto;
import com.bitacademy.projectboard.dto.UserAccountDto;

public record ArticleRequest(
        String title,
        String content,
        String hashtag,
        Long hit
) {

    public static ArticleRequest of(String title, String content, String hashtag, Long hit) {
        return new ArticleRequest(title, content, hashtag, hit);
    }

    public ArticleDto toDto(UserAccountDto userAccountDto) {
        return ArticleDto.of(
                userAccountDto,
                title,
                content,
                hashtag,
                hit
        );
    }

}
