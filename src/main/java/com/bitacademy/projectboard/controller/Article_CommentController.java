package com.bitacademy.projectboard.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bitacademy.projectboard.Service.Article_CommentService;
import com.bitacademy.projectboard.dto.request.Article_CommentRequest;
import com.bitacademy.projectboard.dto.security.BoardPrincipal;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class Article_CommentController {

    private final Article_CommentService article_CommentService;

    @PostMapping ("/new")
    public String postNewArticle_Comment(
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            Article_CommentRequest article_CommentRequest
    ) {
        article_CommentService.saveArticle_Comment(article_CommentRequest.toDto(boardPrincipal.toDto()));


        return "redirect:/articles/" + article_CommentRequest.articleId();
    }

    @PostMapping ("/{commentId}/delete")
    public String deleteArticle_Comment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            Long articleId
    ) {
        article_CommentService.deleteArticle_Comment(commentId, boardPrincipal.getUsername());

        return "redirect:/articles/" + articleId;
    }

}