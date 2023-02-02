package com.bitacademy.projectboard.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Disabled("Spring Data REST 통합테스트는 불필요하므로 제외시킴")
@DisplayName("Data REST - API 테스트")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class DataRestTest {

	private final MockMvc mvc;
	
	public DataRestTest(@Autowired MockMvc mvc) {
		this.mvc = mvc;
	}
	
	@DisplayName("[api] 게시글 리스트 조회")
	@Test
	void giveNothing_whenRequestingArticles_thenReturnsArticlesJsonResponse() throws Exception {
		// Given
		
		// When & Then
		mvc.perform(get("/api/articles"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.valueOf("application/hal+jason")));
	}
	
	@DisplayName("[api] 게시글 단건 조회")
	@Test
	void giveNothing_whenRequestingArticles_thenReturnsArticleJsonResponse() throws Exception {
		// Given
		
		// When & Then
		mvc.perform(get("/api/articles/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.valueOf("application/hal+jason")));
	}
	
	@DisplayName("[api] 게시글 -> 댓글 리스트 조회")
	@Test
	void giveNothing_whenRequestingArticle_CommentsFromArticle_thenReturnsArticle_CommentsJsonResponse() throws Exception {
		// Given
		
		// When & Then
		mvc.perform(get("/api/articles/1/article_Comments"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.valueOf("application/hal+jason")));
	}
	
	@DisplayName("[api] 댓글 리스트 조회")
	@Test
	void giveNothing_whenRequestingArticle_Comments_thenReturnsArticle_CommentsJsonResponse() throws Exception {
		// Given
		
		// When & Then
		mvc.perform(get("/api/article_Comments"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.valueOf("application/hal+jason")));
	}
	
	@DisplayName("[api] 댓글 단건 조회")
	@Test
	void giveNothing_whenRequestingArticle_Comment_thenReturnsArticle_CommentJsonResponse() throws Exception {
		// Given
		
		// When & Then
		mvc.perform(get("/api/article_Comments/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.valueOf("application/hal+jason")));
	}
}
