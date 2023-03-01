package com.bitacademy.projectboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bitacademy.projectboard.domain.Article_Comment;
import com.bitacademy.projectboard.domain.QArticle_Comment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;

@RepositoryRestResource
public interface Article_CommentRepository extends
        JpaRepository<Article_Comment, Long>,
        QuerydslPredicateExecutor<Article_Comment>,
        QuerydslBinderCustomizer<QArticle_Comment> {

    // 게시글 id를 통해 게시글id에 해당하는 댓글 리스트를 뽑는다 
    // 댓글 요소로 검색하는 것이 아닌 게시글로 댓글 검색 
    List<Article_Comment> findByArticle_Id(Long articleId); 
    void deleteByIdAndUserAccount_UserId(Long article_CommentId, String userId);

    @Override
    default void customize(QuerydslBindings bindings, QArticle_Comment root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.content, root.createdAt, root.createdBy);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }

}
