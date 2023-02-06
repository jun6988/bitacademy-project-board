package com.bitacademy.projectboard.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bitacademy.projectboard.config.SecurityConfig;



@DisplayName("View 컨트롤러 - 게시글")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

	private final MockMvc mvc;
	
	public ArticleControllerTest(@Autowired MockMvc mvc) {
		this.mvc = mvc;
	}
	
	@DisplayName("[View][GET] 게시글 리스트 (게시판) 페이지 - 정상 호출")
	@Test
	public void giveNothing_whenRequestingArticlesView_thenReturnsArticlesView() throws Exception {
		// Given
		
		// When & Then
		mvc.perform(MockMvcRequestBuilders.get("/articles"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
				.andExpect(view().name("articles/index"))
				.andExpect(model().attributeExists("articles"));
	}
	
	@DisplayName("[View][GET] 게시글 상세 페이지 - 정상 호출")
	@Test
	public void giveNothing_whenRequestingArticleView_thenReturnsArticleView() throws Exception {
		// Given
		
		// When & Then
		mvc.perform(MockMvcRequestBuilders.get("/articles/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
				.andExpect(view().name("articles/detail"))
				.andExpect(model().attributeExists("article"))
				.andExpect(model().attributeExists("article_Comments"));
	}
	
	@DisplayName("[View][GET] 게시글 검색 전용 페이지 - 정상 호출")
	@Test
	public void giveNothing_whenRequestingArticleSearchView_thenReturnsArticleSearchView() throws Exception {
		// Given
		
		// When & Then
		mvc.perform(MockMvcRequestBuilders.get("/articles/search"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
				.andExpect(model().attributeExists("articles/search"));
	}
	
	@DisplayName("[View][GET] 게시글 해싵태그 검색 페이지 - 정상 호출")
	@Test
	public void giveNothing_whenRequestingArticleHashtagSearchView_thenReturnsArticleHashtagSearchView() throws Exception {
		// Given
		
		// When & Then
		mvc.perform(MockMvcRequestBuilders.get("/articles/search-hashtag"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
				.andExpect(model().attributeExists("articles/search-hashtag"));
	}
}
