package com.bitacademy.projectboard.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * /articles/{article-id}
 * /articles/search
 * /articles/search-hashtag
 */
@RequestMapping("/articles")
@Controller
public class ArticleController {

	@GetMapping
	public String articles(ModelMap map) {
		map.addAttribute("articles", List.of());
		return "articles/index";
	}
	
	@GetMapping("/{articleId}")
	public String article(@PathVariable Long articleId, ModelMap map) {
		map.addAttribute("article", "article"); // TODO : 구현할 때 실제 데이터를 넣어줘야한다.
		map.addAttribute("article_Comments", List.of());
		return "articles/detail";
	}
}
