package com.bitacademy.projectboard.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.bitacademy.projectboard.domain.Article;
import com.bitacademy.projectboard.domain.UserAccount;

// @ActiveProfiles("testdb") --> application.yaml 파일에서 설정 후 사용 
@DisplayName("JPA 연결 테스트")
@Import(JpaRepositoryTest.TestJpaConfig.class) // 만들어준 JpaConfig에서 @EnableJpaAuditing 인식 시켜주기 위해 import
@DataJpaTest
class JpaRepositoryTest {

	// Test할 내용 추가 
    private final ArticleRepository articleRepository;
    private final Article_CommentRepository article_CommentRepository;
    private final UserAccountRepository userAccountRepository;

    // 생성자 주입 패턴 
    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired Article_CommentRepository article_CommentRepository,
            @Autowired UserAccountRepository userAccountRepository
    ) {
        this.articleRepository = articleRepository;
        this.article_CommentRepository = article_CommentRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // Given

        // When - articleRepository 기준으로 test할 것이고 find한 내용을 list에 집어 넣을 것이다. <Article>도메인으로 articles로 받아와야 한다.
        List<Article> articles = articleRepository.findAll();

        // Then - assertj로 테스트 할 것이고 articles가 not null이였으면 좋겠고, size = 123
        assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        // Given - 기존 갯수를 count 후 insert 하면 숫자가 하나 늘었다.   
        long previousCount = articleRepository.count(); 
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("newUno", "pw", null, null, null));
        Article article = Article.of(userAccount, "new article", "new content", "#spring");

        // When
        articleRepository.save(article);

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("update 테스트") // 수정 했을 때 query를 관찰해야 한다.
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow(); // 보통 1L은 있으니까 꺼내오고 없으면 throw
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);

        // When
        Article savedArticle = articleRepository.saveAndFlush(article); // save 동시에 flush 시켜주고 변경점 없으면 rollback

        // Then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag); // saved된 article에서 hashtag가 update 되었나 
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        // Given - 게시글 삭제하면 댓글도 삭제. 댓글 사이즈도 확인 
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticle_CommentCount = article_CommentRepository.count(); 
        int deletedCommentsSize = article.getArticle_Comments().size();

        // When - void method = return 값 없음. 
        articleRepository.delete(article);

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(article_CommentRepository.count()).isEqualTo(previousArticle_CommentCount - deletedCommentsSize); // deletedCommentsSize는 모른다. (댓글이 몇개 있는지 모르기 때문) 
    }

    @EnableJpaAuditing
    @TestConfiguration
    public static class TestJpaConfig {
        @Bean
        public AuditorAware<String> auditorAware() {
            return () -> Optional.of("uno");
        }
    }

}