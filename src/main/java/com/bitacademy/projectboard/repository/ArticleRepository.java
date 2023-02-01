package com.bitacademy.projectboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bitacademy.projectboard.domain.Article;

@RepositoryRestResource
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
