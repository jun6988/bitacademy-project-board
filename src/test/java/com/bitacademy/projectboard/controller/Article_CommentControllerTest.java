//package com.bitacademy.projectboard.controller;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.then;
//import static org.mockito.BDDMockito.willDoNothing;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//import java.util.Map;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.TestExecutionEvent;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.bitacademy.projectboard.Service.Article_CommentService;
//import com.bitacademy.projectboard.config.TestSecurityConfig;
//import com.bitacademy.projectboard.dto.Article_CommentDto;
//import com.bitacademy.projectboard.dto.request.Article_CommentRequest;
//import com.bitacademy.projectboard.util.FormDataEncoder;
//
//@DisplayName("View 컨트롤러 - 댓글")
//@Import({TestSecurityConfig.class, FormDataEncoder.class})
//@WebMvcTest(Article_CommentController.class)
//class Article_CommentControllerTest {
//
//    private final MockMvc mvc;
//    private final FormDataEncoder formDataEncoder;
//
//    @MockBean private Article_CommentService article_CommentService;
//
//
//    public Article_CommentControllerTest(
//            @Autowired MockMvc mvc,
//            @Autowired FormDataEncoder formDataEncoder
//    ) {
//        this.mvc = mvc;
//        this.formDataEncoder = formDataEncoder;
//    }
//
//
//    @WithUserDetails(value = "unoTest", setupBefore = TestExecutionEvent.TEST_EXECUTION)
//    @DisplayName("[view][POST] 댓글 등록 - 정상 호출")
//    @Test
//    void givenArticleCommentInfo_whenRequesting_thenSavesNewArticleComment() throws Exception {
//        // Given
//        long articleId = 1L;
//        Article_CommentRequest request = Article_CommentRequest.of(articleId, "test comment");
//        willDoNothing().given(article_CommentService).saveArticle_Comment(any(Article_CommentDto.class));
//
//        // When & Then
//        mvc.perform(
//                post("/comments/new")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .content(formDataEncoder.encode(request))
//                        .with(csrf())
//        )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/articles/" + articleId))
//                .andExpect(redirectedUrl("/articles/" + articleId));
//        then(article_CommentService).should().saveArticle_Comment(any(Article_CommentDto.class));
//    }
//
//    @WithUserDetails(value = "unoTest", setupBefore = TestExecutionEvent.TEST_EXECUTION)
//    @DisplayName("[view][GET] 댓글 삭제 - 정상 호출")
//    @Test
//    void givenArticleCommentIdToDelete_whenRequesting_thenDeletesArticleComment() throws Exception {
//        // Given
//        long articleId = 1L;
//        long article_CommentId = 1L;
//        String userId = "unoTest";
//        willDoNothing().given(article_CommentService).deleteArticle_Comment(article_CommentId, userId);
//
//        // When & Then
//        mvc.perform(
//                post("/comments/" + article_CommentId + "/delete")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .content(formDataEncoder.encode(Map.of("articleId", articleId)))
//                        .with(csrf())
//        )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/articles/" + articleId))
//                .andExpect(redirectedUrl("/articles/" + articleId));
//        then(article_CommentService).should().deleteArticle_Comment(article_CommentId, userId);
//    }
//
//}