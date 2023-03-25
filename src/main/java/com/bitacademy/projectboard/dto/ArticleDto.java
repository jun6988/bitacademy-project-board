 package com.bitacademy.projectboard.dto;

import java.time.LocalDateTime;

import com.bitacademy.projectboard.domain.Article;
import com.bitacademy.projectboard.domain.UserAccount;

// DTO 안에 넣어서 service에서 사용 
// -> Article은 DTO의 정보를 몰라도 된다는 이점  
// Article이 변경되면 DTO에 영향을 주지만 DTO가 변경되도 Article에는 영향을 주지 않는다.   
// Lombok record는 자동으로 getter setter를 생성해준다. 
public record ArticleDto(
		// entity의 모든 정보를 가지고 있다. 
        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,
        String hashtag,
        Long hit,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static ArticleDto of(UserAccountDto userAccountDto, String title, String content, String hashtag, Long hit) {
        return new ArticleDto(null, userAccountDto, title, content, hashtag, hit, null, null, null, null);
    }

    public static ArticleDto of(Long id, UserAccountDto userAccountDto, String title, String content, String hashtag, Long hit, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashtag, hit, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    // 자기 자신을 entity로 부터 반환한다. 
    // entity를 입력하면 DTO로 변환해준다. 
    // mapping 해서 return 해줌 
    public static ArticleDto from(Article entity) {
        return new ArticleDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getHit(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    // DTO로 부터 entity를 생성해주는 코드 (위 코드와 반대) 
    public Article toEntity(UserAccount userAccount) {
        return Article.of(
                userAccount,
                title,
                content,
                hashtag,
                hit
        );
    }

}