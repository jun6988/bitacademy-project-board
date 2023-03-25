package com.bitacademy.projectboard.dto.request;

import com.bitacademy.projectboard.dto.Article_CommentDto;
import com.bitacademy.projectboard.dto.UserAccountDto;

public record Article_CommentRequest(Long articleId, String content) {

	// 댓글 달 때 필요한 data 
    public static Article_CommentRequest of(Long articleId, String content) {
        return new Article_CommentRequest(articleId, content);
    }

    // user account에서 정보 받아야함 
    public Article_CommentDto toDto(UserAccountDto userAccountDto) {
        return Article_CommentDto.of(
                articleId,
                userAccountDto,
                content
        );
    }

}