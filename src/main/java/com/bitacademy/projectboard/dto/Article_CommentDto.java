package com.bitacademy.projectboard.dto;

import java.time.LocalDateTime;

import com.bitacademy.projectboard.domain.Article;
import com.bitacademy.projectboard.domain.Article_Comment;
import com.bitacademy.projectboard.domain.UserAccount;

//public record Article_CommentDto (
//		LocalDateTime createdAt,
//		String createdBy,
//		LocalDateTime modifiedAt,
//		String modifiedBy,
//		String content
//) {
//	public static Article_CommentDto of(LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy, String content) {
//		return new Article_CommentDto(createdAt, createdBy, modifiedAt, modifiedBy, content);
//	}
//
//}

public record Article_CommentDto(
        Long id,
        Long articleId,
        UserAccountDto userAccountDto,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static Article_CommentDto of(Long articleId, UserAccountDto userAccountDto, String content) {
        return new Article_CommentDto(null, articleId, userAccountDto, content, null, null, null, null);
    }
    public static Article_CommentDto of(Long id, Long articleId, UserAccountDto userAccountDto, String content, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new Article_CommentDto(id, articleId, userAccountDto, content, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static Article_CommentDto from(Article_Comment entity) {
        return new Article_CommentDto(
                entity.getId(),
                entity.getArticle().getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public Article_Comment toEntity(Article article, UserAccount userAccount) {
        return Article_Comment.of(
                article,
                userAccount,
                content
        );
    }

}