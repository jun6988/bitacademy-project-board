package com.bitacademy.projectboard.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.bitacademy.projectboard.config.JpaConfig;
import com.bitacademy.projectboard.domain.Article;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

	private final ArticleRepository articleRepository;
	private final Article_CommentRepository article_CommentRepository;

	public JpaRepositoryTest (
			@Autowired ArticleRepository articleRepository,
			@Autowired Article_CommentRepository article_CommentRepository
	) {
		this.articleRepository = articleRepository;
		this.article_CommentRepository = article_CommentRepository;
	}
	
	@DisplayName("select 테스트")
	@Test
	void givenTestData_whenSelecting_thenWorksFine() {
		// Given
		
		// When
		// findall한 내용을 List에 넣고 Article이란 도메인으로 받아와야한다.   
		List<Article> articles = articleRepository.findAll();
		
		// Then
		// assertj로 테스트 
		assertThat(articles)
				.isNotNull()
				.hasSize(0);
	}
	
	@DisplayName("insert 테스트") // 기존 data보다 1씩 늘어난다. 
	@Test
	void givenTestData_whenInserting_thenWorksFine() {
		// Given 
		long previousCount = articleRepository.count();
		
		// When  
		// save한 내용을 savedArticle로 받겠다. 
		Article savedArticle = articleRepository.save(Article.of("new article", "new content", "#spring"));
		
		// Then
		assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
	}
	
	@DisplayName("upadate 테스트")
	@Test
	void givenTestData_whenUpdating_thenWorksFine() {
		// Given
		Article article = articleRepository.findById(1L).orElseThrow(); // 기존 data에서 한 줄 가져오기 
		String updatedHashtag = "#springboot";
		article.setHashtag(updatedHashtag);
		
		// When  
		Article savedArticle = articleRepository.saveAndFlush(article);
		
		// Then
		assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
	}
	
	@DisplayName("delete 테스트") // 글 삭제하면 댓글도 삭제 
	@Test
	void givenTestData_whenDeleting_thenWorksFine() {
		// Given
		Article article = articleRepository.findById(1L).orElseThrow();
		long previousArticleCount = articleRepository.count();
		long previousArticle_CommentCount = article_CommentRepository.count();
		int deletedCommentsSize = article.getArticle_Comments().size();
		
		// When  
		articleRepository.delete(article);
		
		// Then
		assertThat(articleRepository.count()).isEqualTo(previousArticleCount -1);
		assertThat(article_CommentRepository.count()).isEqualTo(previousArticle_CommentCount - deletedCommentsSize);
	}
}