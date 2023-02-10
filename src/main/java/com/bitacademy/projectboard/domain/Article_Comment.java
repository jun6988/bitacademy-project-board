package com.bitacademy.projectboard.domain;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Getter
//@ToString(callSuper = true)
//@Table(indexes = {
//		@Index(columnList = "content"),
//	@Index(columnList = "createdAt"),
//	@Index(columnList = "createdBy"),
//})
//
//
//@Entity
//public class Article_Comment extends AuditingFields {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//	
//	@Setter @ManyToOne(optional = false) private Article article; //게시글(ID)   
//@Setter @ManyToOne(optional = false) private UserAccount userAccount; // 유저 정보 (ID)
//@Setter @Column(nullable = false, length = 500)private String content; // 본문 
//
////Meta Data
//// @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; // 생성 일시 
//// @CreatedBy @Column(nullable = false, length = 100) private String createdBy; // 생성자 = 인증기능설정(JPA config)
//// @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt; // 수정일시 
//// @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy; // 수정자 
//
//protected Article_Comment() {}
//
//private Article_Comment(Article article, UserAccount userAccount, String content) {
//	this.article = article;
//	this.userAccount = userAccount;
//	this.content = content;
//}
//
//public static Article_Comment of(Article article, UserAccount userAccount, String content) {
//	return new Article_Comment(article, userAccount, content);
//}
//
//@Override
//public boolean equals(Object o) {
//	if (this == o) return true;
//	if (!(o instanceof Article_Comment)) return false;
//	Article_Comment that = (Article_Comment) o;
//	// id가 영속화, 즉 부여되지 않았다고 하면 동등성 검사를 하지 않고 처리하지 않겠다. 
//		return id != null && id.equals(that.id);
//	}
//	
//	@Override
//	public int hashCode() {
//		return Objects.hash(id);
//	}
//}

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article_Comment extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    private Article article; // 게시글 (ID)

    @Setter
    @JoinColumn(name = "userId")
    @ManyToOne(optional = false)
    private UserAccount userAccount; // 유저 정보 (ID)

    @Setter
    @Column(updatable = false)
    private Long parentCommentId; // 부모 댓글 ID

    @ToString.Exclude
    @OrderBy("createdAt ASC")
    @OneToMany(mappedBy = "parentCommentId", cascade = CascadeType.ALL)
    private Set<Article_Comment> childComments = new LinkedHashSet<>();

    @Setter @Column(nullable = false, length = 500) private String content; // 본문


    protected Article_Comment() {}

    private Article_Comment(Article article, UserAccount userAccount, Long parentCommentId, String content) {
        this.article = article;
        this.userAccount = userAccount;
        this.parentCommentId = parentCommentId;
        this.content = content;
    }

    public static Article_Comment of(Article article, UserAccount userAccount, String content) {
        return new Article_Comment(article, userAccount, null, content);
    }

    public void addChildComment(Article_Comment child) {
        child.setParentCommentId(this.getId());
        this.getChildComments().add(child);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article_Comment that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
