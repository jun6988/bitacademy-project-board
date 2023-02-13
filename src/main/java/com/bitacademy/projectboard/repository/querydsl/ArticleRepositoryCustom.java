package com.bitacademy.projectboard.repository.querydsl;

import java.util.List;

public interface ArticleRepositoryCustom {
	List<String> findAllDistinctHashtags();

}
