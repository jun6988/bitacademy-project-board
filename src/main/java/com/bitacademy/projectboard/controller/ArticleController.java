//package com.bitacademy.projectboard.controller;
//
//import java.util.List;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.bitacademy.projectboard.Service.ArticleService;
//import com.bitacademy.projectboard.Service.PaginationService;
//import com.bitacademy.projectboard.domain.constant.FormStatus;
//import com.bitacademy.projectboard.domain.constant.SearchType;
//import com.bitacademy.projectboard.dto.UserAccountDto;
//import com.bitacademy.projectboard.dto.Response.ArticleResponse;
//import com.bitacademy.projectboard.dto.Response.ArticleWithCommentsResponse;
//import com.bitacademy.projectboard.dto.request.ArticleRequest;
//import com.bitacademy.projectboard.dto.security.BoardPrincipal;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@RequestMapping("/articles")
//@Controller
//public class ArticleController {
//
//    private final ArticleService articleService;
//    private final PaginationService paginationService;
//
//    @GetMapping
//    public String articles(
//            @RequestParam(required = false) SearchType searchType,
//            @RequestParam(required = false) String searchValue,
//            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
//            ModelMap map
//    ) {
//        Page<ArticleResponse> articles = articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from);
//        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
//
//        map.addAttribute("articles", articles);
//        map.addAttribute("paginationBarNumbers", barNumbers);
//        map.addAttribute("searchTypes", SearchType.values());
//        map.addAttribute("searchTypeHashtag", SearchType.HASHTAG);
//
//        return "articles/index";
//    }
//
//    @GetMapping("/{articleId}")
//    public String article(@PathVariable Long articleId, ModelMap map) {
//        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticleWithComments(articleId));
//
//        map.addAttribute("article", article);
//        map.addAttribute("articleComments", article.article_CommentsResponse());
//        map.addAttribute("totalCount", articleService.getArticleCount());
//        map.addAttribute("searchTypeHashtag", SearchType.HASHTAG);
//
//        return "articles/detail";
//    }
//
//    @GetMapping("/search-hashtag")
//    public String searchArticleHashtag(
//            @RequestParam(required = false) String searchValue,
//            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
//            ModelMap map
//    ) {
//        Page<ArticleResponse> articles = articleService.searchArticlesViaHashtag(searchValue, pageable).map(ArticleResponse::from);
//        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
//        List<String> hashtags = articleService.getHashtags();
//
//        map.addAttribute("articles", articles);
//        map.addAttribute("hashtags", hashtags);
//        map.addAttribute("paginationBarNumbers", barNumbers);
//        map.addAttribute("searchType", SearchType.HASHTAG);
//
//        return "articles/search-hashtag";
//    }
//
//    @GetMapping("/form")
//    public String articleForm(ModelMap map) {
//        map.addAttribute("formStatus", FormStatus.CREATE);
//
//        return "articles/form";
//    }
//
//    @PostMapping("/form")
//    public String postNewArticle(
//            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
//            ArticleRequest articleRequest
//    ) {
//        articleService.saveArticle(articleRequest.toDto(boardPrincipal.toDto()));
//
//        return "redirect:/articles";
//    }
//
//    @GetMapping("/{articleId}/form")
//    public String updateArticleForm(@PathVariable Long articleId, ModelMap map) {
//        ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId));
//
//        map.addAttribute("article", article);
//        map.addAttribute("formStatus", FormStatus.UPDATE);
//
//        return "articles/form";
//    }
//
//    @PostMapping("/{articleId}/form")
//    public String updateArticle(
//            @PathVariable Long articleId,
//            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
//            ArticleRequest articleRequest
//    ) {
//        articleService.updateArticle(articleId, articleRequest.toDto(boardPrincipal.toDto()));
//
//        return "redirect:/articles/" + articleId;
//    }
//
//    @PostMapping("/{articleId}/delete")
//    public String deleteArticle(
//            @PathVariable Long articleId,
//            @AuthenticationPrincipal BoardPrincipal boardPrincipal
//    ) {
//        articleService.deleteArticle(articleId, boardPrincipal.getUsername());
//
//        return "redirect:/articles";
//    }
//
//}

package com.bitacademy.projectboard.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bitacademy.projectboard.Service.ArticleService;
import com.bitacademy.projectboard.Service.PaginationService;
import com.bitacademy.projectboard.domain.constant.FormStatus;
import com.bitacademy.projectboard.domain.constant.SearchType;
import com.bitacademy.projectboard.dto.Response.ArticleResponse;
import com.bitacademy.projectboard.dto.Response.ArticleWithCommentsResponse;
import com.bitacademy.projectboard.dto.request.ArticleRequest;
import com.bitacademy.projectboard.dto.security.BoardPrincipal;

import lombok.RequiredArgsConstructor;

// controller는 실질적으로 Data 올려주는 곳 

@RequestMapping("/articles") // 모든 view endpoint가 articles로 시작 
@RestController
public class ArticleController {

    private final ArticleService articleService;
    private final PaginationService paginationService;
    
    public ArticleController(ArticleService articleService, PaginationService paginationService) {
        this.articleService = articleService;
        this.paginationService = paginationService;
    }

    // 게시글 리스트 페이지 불러오기 
    @GetMapping
    public Page<ArticleResponse> articles(
    		// 검색어를 입력하면 검색기능으로 넘어가게 설정 
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            // 게시판 정렬 기능 
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
    	// DTO를 가공한 map을 사용하여 ArticleResponse 게시글(댓글 필요 없음) 불러온다. 
        Page<ArticleResponse> articles = articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from);
        return articles;
    }

    // 게시글 단건 조회 
    @GetMapping("/{articleId}")
    public ArticleWithCommentsResponse article(@PathVariable Long articleId) {
        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticleWithComments(articleId));
        return article;
    }

    @GetMapping("/search-hashtag")
    public Page<ArticleResponse> searchArticleHashtag(
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ArticleResponse> articles = articleService.searchArticlesViaHashtag(searchValue, pageable).map(ArticleResponse::from);
        return articles;
    }

    @PostMapping("/form")
    public void postNewArticle(
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            ArticleRequest articleRequest
    ) {
        articleService.saveArticle(articleRequest.toDto(boardPrincipal.toDto()));
    }

    @PostMapping("/{articleId}/form")
    public void updateArticle(
            @PathVariable Long articleId,
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            ArticleRequest articleRequest
    ) {
        articleService.updateArticle(articleId, articleRequest.toDto(boardPrincipal.toDto()));
    }

    @PostMapping("/{articleId}/delete")
    public void deleteArticle(
            @PathVariable Long articleId,
            @AuthenticationPrincipal BoardPrincipal boardPrincipal
    ) {
        articleService.deleteArticle(articleId, boardPrincipal.getUsername());
    }
}

// ArticleControllerTest 보면서 controller 작성 

//@RequiredArgsConstructor
//@RequestMapping("/articles")
//@Controller
//public class ArticleController {

//private final ArticleService articleService;
//	
//	@GetMapping
//	public String articles(ModelMap map) {
//		map.addAttribute("articles", List.of());
//		
//		return "articles/index";
//		
//  <게시글 상세 페이지>
//	@GetMapping("/{articleId}") 경로 = 게시물ID 값 
//	public String article(@PathVariable Long articleId, ModelMap map) {
//		map.addAttribute("article", null); 게시물 
//		map.addAttribute("article_Comments", List.of()); 게시물의 댓글(리스트 형식으로 받는다) 
//
//		return "articles/detail"; // detail.html, detail.th.xml 만들어 주기 (오른쪽 사이드 바 & 네비게이션 바)  
//	}
//}