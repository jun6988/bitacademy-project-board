package com.bitacademy.projectboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitacademy.projectboard.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
