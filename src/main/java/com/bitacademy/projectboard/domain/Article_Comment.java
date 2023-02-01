package com.bitacademy.projectboard.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Table(indexes = {
		@Index(columnList = "content"),
		@Index(columnList = "createdAt"),
		@Index(columnList = "createdBy"),
})


@Entity
public class Article_Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Setter @ManyToOne(optional = false) private Article article; //게시글(ID)   
	@Setter @Column(nullable = false, length = 500)private String content; // 본문 
	
	//Meta Data
	@CreatedDate @Column(nullable = false) private LocalDateTime createdAt; // 생성 일시 
	@CreatedBy @Column(nullable = false, length = 100) private String createdBy; // 생성자 = 인증기능설정(JPA config)
	@LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt; // 수정일시 
	@LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy; // 수정자 
	
	protected Article_Comment() {}
	
	private Article_Comment(Article article, String content) {
		this.article = article;
		this.content = content;
	}
	
	public static Article_Comment of(Article article, String content) {
		return new Article_Comment(article, content);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Article_Comment)) return false;
		Article_Comment that = (Article_Comment) o;
		// id가 영속화, 즉 부여되지 않았다고 하면 동등성 검사를 하지 않고 처리하지 않겠다. 
		return id != null && id.equals(that.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
