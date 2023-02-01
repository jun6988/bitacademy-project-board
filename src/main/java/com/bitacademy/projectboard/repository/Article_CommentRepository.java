package com.bitacademy.projectboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bitacademy.projectboard.domain.Article_Comment;

@RepositoryRestResource
public interface Article_CommentRepository extends JpaRepository<Article_Comment, Long> {

}
