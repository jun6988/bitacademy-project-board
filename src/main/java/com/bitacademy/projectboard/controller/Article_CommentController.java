package com.bitacademy.projectboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bitacademy.projectboard.Service.Article_CommentService;
import com.bitacademy.projectboard.dto.UserAccountDto;
import com.bitacademy.projectboard.dto.request.Article_CommentRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class Article_CommentController {

    private final Article_CommentService article_CommentService;

    @PostMapping ("/new")
    public String postNewArticle_Comment(Article_CommentRequest article_CommentRequest) {
        // TODO: 인증 정보를 넣어줘야 한다.
        article_CommentService.saveArticle_Comment(article_CommentRequest.toDto(UserAccountDto.of(
                "june", "pw", "june@mail.com", null, null
        )));


        return "redirect:/articles/" + article_CommentRequest.articleId();
    }

    @PostMapping ("/{commentId}/delete")
    public String deleteArticle_Comment(@PathVariable Long commentId, Long articleId) {
        article_CommentService.deleteArticle_Comment(commentId);

        return "redirect:/articles/" + articleId;
    }

}
