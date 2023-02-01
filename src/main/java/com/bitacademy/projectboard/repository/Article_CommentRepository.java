package com.bitacademy.projectboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitacademy.projectboard.domain.Article_Comment;

public interface Article_CommentRepository extends JpaRepository<Article_Comment, Long> {

}
