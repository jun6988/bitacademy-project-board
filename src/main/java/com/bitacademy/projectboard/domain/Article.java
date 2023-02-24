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

@Getter // 모든 필드는 접근이 가능해야 한다.
@ToString(callSuper = true) // 쉽게 관찰 할수있게.
@Table(indexes = { // index = 검색할 수 있게.
		@Index(columnList = "id"),
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
// @EntityListeners(AuditingEntityListener.class) - JpaRepository에서 불러올 수 있다.
@Entity // Entity라는 명시는 꼭 해줘야한다. 
public class Article extends AuditingFields { // AuditingFields 상속으로 내용을 받아 온다. 

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL autoincrement 방식 
    private Long id; // Setter를 안걸어주는 이유는 JPA가 자동으로 생성해주기 때문.  

    // @Setter = 도메인에서 수정 가능하도록. 자동으로 바꿔주는 것을 방지.
    // meta data는 JPA가 자동으로 변경 가능하도록 해야하기 때문에 걸어주지 않는다. --> meta data는 JpaConfig로 이동.
    @Setter @ManyToOne(optional = false) @JoinColumn(name = "userId") private UserAccount userAccount; // 유저 정보 (ID)

    // @Column(nullable = false, length = 10000) - not null 및 길이 설정.
    @Setter @Column(nullable = false) private String title; // 제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 본문

    @Setter private String hashtag; // 해시태그

    // 양방향 binding - article에 연동되어 있는 comment들은 중복을 허용하지 않고 모아서 List로 보겠다. 
    @ToString.Exclude // 댓글로 부터 글을 참조 / 댓글 리스트를 다 뽑아 볼 필요가 없기 때문에 끊어준다 (Article_Comment로 들어갔다가 끊어준다) 
    @OrderBy("createdAt DESC")
    // cascade = 연관관계 있는 것에 영향을 주어야한다. 
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL) // Article에 연동되어 있는 댓글들은 중복을 허용하지 않고 모아서 connection으로 보겠다. 
    private final Set<Article_Comment> article_Comments = new LinkedHashSet<>();


    // JPA Entity 들은 기본 생성자를 가지고 있어야한다.
    // new로 생성하지 못하게 하고 평소에 오픈하지 않을거기 때문에 protected
    // id 직접 생성 x / meta data는 자동 생성
    // 도메인과 관련있는 것만 open
    protected Article() {}

    private Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // 도메인 article을 생성할 때 어떤 정보가 필요한지 guide
    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
    }

    // 동등성 검사 
    // Entity 중 ID만 같으면 같다.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article that)) return false;
        return id != null && id.equals(that.getId()); // 영속화 하지 않아(ID가 데이터 베이스애 넣어주지 않았다.) ID가 없으면 동등성 검사를 하지 않고, not null이면 같은지 확인.
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

//public class Article {
//	private Long id;
//	private String title;
//	private String content;
//	private String hashtag;
//	
//	private String createdBy;
//	private String modifiedBy;
//	private LocalDateTime createdAt;
//	private LocalDateTime modifiedAt;
//	
//}
