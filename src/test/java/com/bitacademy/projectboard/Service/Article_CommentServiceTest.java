package com.bitacademy.projectboard.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
// given Error 해결
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bitacademy.projectboard.domain.Article;
import com.bitacademy.projectboard.domain.Article_Comment;
import com.bitacademy.projectboard.domain.UserAccount;
import com.bitacademy.projectboard.dto.Article_CommentDto;
import com.bitacademy.projectboard.dto.UserAccountDto;
import com.bitacademy.projectboard.repository.ArticleRepository;
import com.bitacademy.projectboard.repository.Article_CommentRepository;
import com.bitacademy.projectboard.repository.UserAccountRepository;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks private Article_CommentService sut;

    @Mock private ArticleRepository articleRepository;
    @Mock private Article_CommentRepository article_CommentRepository;
    @Mock private UserAccountRepository userAccountRepository;

    @DisplayName("게시글 ID로 조회하면, 해당하는 댓글 리스트를 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticleComments_thenReturnsArticleComments() {
        // Given
        Long articleId = 1L;
        Article_Comment expected = createArticle_Comment("content");
        given(article_CommentRepository.findByArticle_Id(articleId)).willReturn(List.of(expected));

        // When
        List<Article_CommentDto> actual = sut.searchArticle_Comments(articleId);

        // Then
        assertThat(actual)
                .hasSize(1)
                .first().hasFieldOrPropertyWithValue("content", expected.getContent());
        then(article_CommentRepository).should().findByArticle_Id(articleId);
    }

    @DisplayName("댓글 정보를 입력하면, 댓글을 저장한다.")
    @Test
    void givenArticleCommentInfo_whenSavingArticleComment_thenSavesArticleComment() {
        // Given
        Article_CommentDto dto = createArticle_CommentDto("댓글");
        given(articleRepository.getReferenceById(dto.articleId())).willReturn(createArticle());
        given(userAccountRepository.getReferenceById(dto.userAccountDto().userId())).willReturn(createUserAccount());
        given(article_CommentRepository.save(any(Article_Comment.class))).willReturn(null);

        // When
        sut.saveArticle_Comment(dto);

        // Then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(userAccountRepository).should().getReferenceById(dto.userAccountDto().userId());
        then(article_CommentRepository).should().save(any(Article_Comment.class));
    }

    @DisplayName("댓글 저장을 시도했는데 맞는 게시글이 없으면, 경고 로그를 찍고 아무것도 안 한다.")
    @Test
    void givenNonexistentArticle_whenSavingArticleComment_thenLogsSituationAndDoesNothing() {
        // Given
        Article_CommentDto dto = createArticle_CommentDto("댓글");
        given(articleRepository.getReferenceById(dto.articleId())).willThrow(EntityNotFoundException.class);

        // When
        sut.saveArticle_Comment(dto);

        // Then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(userAccountRepository).shouldHaveNoInteractions();
        then(article_CommentRepository).shouldHaveNoInteractions();
    }

    @DisplayName("댓글 정보를 입력하면, 댓글을 수정한다.")
    @Test
    void givenArticleCommentInfo_whenUpdatingArticleComment_thenUpdatesArticleComment() {
        // Given
        String oldContent = "content";
        String updatedContent = "댓글";
        Article_Comment articleComment = createArticle_Comment(oldContent);
        Article_CommentDto dto = createArticle_CommentDto(updatedContent);
        given(article_CommentRepository.getReferenceById(dto.id())).willReturn(articleComment);

        // When
        sut.updateArticle_Comment(dto);

        // Then
        assertThat(articleComment.getContent())
                .isNotEqualTo(oldContent)
                .isEqualTo(updatedContent);
        then(article_CommentRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("없는 댓글 정보를 수정하려고 하면, 경고 로그를 찍고 아무 것도 안 한다.")
    @Test
    void givenNonexistentArticleComment_whenUpdatingArticleComment_thenLogsWarningAndDoesNothing() {
        // Given
        Article_CommentDto dto = createArticle_CommentDto("댓글");
        given(article_CommentRepository.getReferenceById(dto.id())).willThrow(EntityNotFoundException.class);

        // When
        sut.updateArticle_Comment(dto);

        // Then
        then(article_CommentRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("댓글 ID를 입력하면, 댓글을 삭제한다.")
    @Test
    void givenArticleCommentId_whenDeletingArticleComment_thenDeletesArticleComment() {
        // Given
        Long article_CommentId = 1L;
        String userId = "uno";
        willDoNothing().given(article_CommentRepository).deleteByIdAndUserAccount_UserId(article_CommentId, userId);

        // When
        sut.deleteArticle_Comment(article_CommentId, userId);

        // Then
        then(article_CommentRepository).should().deleteByIdAndUserAccount_UserId(article_CommentId, userId);
    }


    private Article_CommentDto createArticle_CommentDto(String content) {
        return Article_CommentDto.of(
                1L,
                1L,
                createUserAccountDto(),
                content,
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "uno",
                "password",
                "uno@mail.com",
                "Uno",
                "This is memo",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }

    private Article_Comment createArticle_Comment(String content) {
        return Article_Comment.of(
                Article.of(createUserAccount(), "title", "content", "hashtag"),
                createUserAccount(),
                content
        );
    }

    private UserAccount createUserAccount() {
        return UserAccount.of(
                "uno",
                "password",
                "uno@email.com",
                "Uno",
                null
        );
    }

    private Article createArticle() {
        return Article.of(
                createUserAccount(),
                "title",
                "content",
                "#java"
        );
    }

}