package com.bitacademy.projectboard.Service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bitacademy.projectboard.domain.Article;
import com.bitacademy.projectboard.domain.UserAccount;
import com.bitacademy.projectboard.domain.constant.SearchType;
import com.bitacademy.projectboard.dto.ArticleDto;
import com.bitacademy.projectboard.dto.ArticleWithCommentsDto;
import com.bitacademy.projectboard.repository.ArticleRepository;
import com.bitacademy.projectboard.repository.UserAccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // 필수 핅드를 자동으로 만들어주는 annotation
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository; // aritcle service는 article repository를 사용한다. 
    private final UserAccountRepository userAccountRepository;

    // 검색 기능 
    // Page 안에는 map method가 있다. 
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        
    	if (searchKeyword == null || searchKeyword.isBlank()) {
            return articleRepository.findAll(pageable).map(ArticleDto::from); 
            // 출력 type = articledto / service는 domain과 dto까지만 알고 controller에서는 실제로 어떤 data를 보여줄지는 모른다. 
            // 실제로 일어나는 것은 articleDto에서 from 되어 있는 곳! 
        }

    	// SearchType에 있는 항목들 
    	// title, content, id, nickname, hashtag 마다 따로 따로 검색 호출 
    	// containing = 부분 검색 (Repository에서도 findBy~ 수정해줘야함) 
        return switch (searchType) {
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            // ID 검색은 user_account 안에 있다.  
            case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
            // hashtag는 단순한 분류 
            // #을 빼놓고 검색 가능 
            case HASHTAG -> articleRepository.findByHashtag("#" + searchKeyword, pageable).map(ArticleDto::from);
        };
    }

    // 게시글 단건 조회, 검색 
    // article_id로 검색  
    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    // 게시글 정보를 입력하면 게시글 생성 및 저장하는 기능  
    public void saveArticle(ArticleDto dto) {
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
        articleRepository.save(dto.toEntity(userAccount)); 
        // toEntity : ArticleDto에서 entity를 생성해서 save = 새로운 게시물 내용 생성하여 저장 
    }

    // 게시글 수정 기능 
    public void updateArticle(Long articleId, ArticleDto dto) {
    	// findById vs ReferenceById 
    	// findById : 단건 조회 / select query 발생 
        try {
            Article article = articleRepository.getReferenceById(articleId);
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
            if(article.getUserAccount().equals(userAccount)) {
            	// if 문이 null이 아닐때만 동작시켜라 
            	if (dto.title() != null) { article.setTitle(dto.title()); }
            	if (dto.content() != null) { article.setContent(dto.content()); }
            	article.setHashtag(dto.hashtag());
            }
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 수정하는데 필요한 정보를 찾을 수 없습니다 - dto: {}", e.getLocalizedMessage());
        }
    }

    // 게시글 삭제 기능 
    // 게시글에서 삭제 부분 article id = user id 같을 때만 보이도록 
    public void deleteArticle(long articleId, String userId) {
        articleRepository.deleteByIdAndUserAccount_UserId(articleId, userId);
    }

    public long getArticleCount() {
        return articleRepository.count();
    }

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {
        if (hashtag == null || hashtag.isBlank()) {
            return Page.empty(pageable);
        }

        return articleRepository.findByHashtag(hashtag, pageable).map(ArticleDto::from);
    }

    public List<String> getHashtags() {
        return articleRepository.findAllDistinctHashtags();
    }
    
    public List<Article> getAll() {
    	return articleRepository.findAll();
    }
    
    // 게시판 조회수 
//    @Transactional
//    public void updateHit(Long articleId) {
//        Article article = articleRepository.findById(articleId)
//                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
//        article.setHit(article.getHit() + 1);
//        articleRepository.save(article);
//    }
    public void updateHit(Long id) {
    	articleRepository.updateHit(id);
    }
    

}