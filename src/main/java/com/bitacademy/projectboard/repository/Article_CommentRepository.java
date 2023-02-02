package com.bitacademy.projectboard.repository;

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
		
	@Override
	default void customize(QuerydslBindings bindings, QArticle_Comment root) {
		bindings.excludeUnlistedProperties(true);
		bindings.including(root.content, root.createdAt, root.createdBy);
	    bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
	    bindings.bind(root.createdAt).first(DateTimeExpression::eq);
	    bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
	} 	
}

