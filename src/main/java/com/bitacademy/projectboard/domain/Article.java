package com.bitacademy.projectboard.domain;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter // 모든 빌드는 접근이 가능해야한다. 
@ToString // 쉽게 출력하기 위함. 
@Table(indexes = {
		@Index(columnList = "title"),
		@Index(columnList = "hashtag"),
		@Index(columnList = "createdAt"),
		@Index(columnList = "createdBy"),
}) // 검색을 하기 위함. 


@Entity
public class Article extends AuditingFields { // 추출한 공통 data 상속으로 연결 
	
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // mysql auto increment type
	private Long id;
	
	// not null
	// Setter = 도메인에서 수정 가능, 각 필드에 거는 이유 : 사용자가 특정 필드에 접근하지 못하도록 한다 ex. id 
	@Setter @Column(nullable = false) private String title; // 제목 
	@Setter @Column(nullable = false, length = 10000) private String Content; // 본문  
	
	// optional : null 가능 
	@Setter private String hashtag; // 해시태그 
	
	// 양방향 Binding Data Mapping
	@ToString.Exclude
	@OrderBy("id")
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
	private final Set<Article_Comment> article_Comments = new LinkedHashSet<>();
	
	
	// Hibernate 구현체 사용하면 기본 생성자를 가지고 있어야 한다. 
	// 기본 생성자는 평소에 구현하지 않을거니까 protected로 생성 (privat은 오류) 
	protected Article() {}

	// 도메인에서 생성자를 통해  관련 있는 정보만 open하기 위해서 생성 (나머지는 자동 생성). 
	private Article(String title, String content, String hashtag) {
		this.title = title;
		this.Content = content;
		this.hashtag = hashtag;
	}
	
	// 위 생성자 쉽게 사용하기 위함. 
	public static Article of(String title, String content, String hashtag) {
		return new Article(title, content, hashtag);
	}
	
	
	@Override 
	// 동등성 검사 
	// id가 영속화, 즉 부여되지 않았다고 하면 동등성 검사를 하지 않고 처리하지 않겠다. 
	// Database에서 서로 다른 raw에서 같은 조건이 무엇인가에 대한 답 = id
	// id가 다르면 다른것들은 다 다르다고 판단하겠다. 
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Article)) return false;
		Article article = (Article) o;
		return id != null && id.equals(article.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
