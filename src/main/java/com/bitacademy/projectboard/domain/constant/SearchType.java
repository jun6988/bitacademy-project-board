package com.bitacademy.projectboard.domain.constant;

import lombok.Getter;

// 검색 가능하도록 할 항목들 추가 
// 한글로 보이게 하고 싶으면 옆에 적어주면 된다 
public enum SearchType {
    TITLE("제목"),
    CONTENT("본문"),
    ID("유저 ID"),
    NICKNAME("닉네임"),
    HASHTAG("해시태그");

    @Getter private final String description;

    SearchType(String description) {
        this.description = description;
    }

}
