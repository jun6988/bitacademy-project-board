package com.bitacademy.projectboard.dto.Response;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.bitacademy.projectboard.dto.Article_CommentDto;

// 댓글 요청하는 controller에 응답하는 DTO (Controller에서만 사용) 
// -> controller에서는 response dto오를 최소화 / 일반 dto 존재 모르고 request, response dto만 알 수 있다.
// dto, domain 존재를 모두 알고 있는 곳 = Service
public record Article_CommentResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        String email,
        String nickname, // 닉네임은 원래 자기가 가지고 있는 것이 아닌 user account가 들고 있다. 
        String userId
) {

    public static Article_CommentResponse of(Long id, String content, LocalDateTime createdAt, String email, String nickname, String userId) {
        return new Article_CommentResponse(id, content, createdAt, email, nickname, userId);
    }

    // nickname 가공 
    // user account에서 꺼내는 장면 
    public static Article_CommentResponse from(Article_CommentDto dto) { // Article_CommentDto를 받아서   
        String nickname = dto.userAccountDto().nickname(); // 닉네임을 닉네임으로부터 불러오고  
        if (nickname == null || nickname.isBlank()) { // 만약 없으면 (null field) 
            nickname = dto.userAccountDto().userId(); // userId를 불러와서 userId나 닉네임(우선순위 1)을 불러와서 setting되서 출력되도록 형성 
        }

        return new Article_CommentResponse(
                dto.id(),
                dto.content(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname, // 닉네임 출력 보장 
                dto.userAccountDto().userId()
        );
    }

}
