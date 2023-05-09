package springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter     // Getter 메소드 자동 생성
@NoArgsConstructor      // 기본생성자 자동 추가
@Entity     // JPA 어노테이션
// Post 클래스는 DB 테이블과 매칭될 클래스 ; 엔티티 클래스라 칭함.
// JPA는 실제 쿼리보다는 이 Entity 클래스의 수정을 통해 작업.
public class Posts {

	@Id     // 테이블의 PK 필드
	@GeneratedValue(strategy = GenerationType.IDENTITY)     // auto_increment설정
	private Long id;

	@Column(length = 500, nullable = false)     // column으로 사용. 선언없이도 column으로 됨.
	private String title;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	private String author;

	@Builder        // 해당 클래스의 빌더 패턴 클래스 생성
	public Posts(Long id, String title, String content, String author) {
		this.title = title;
		this.content = content;
		this.author = author;
	}

	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
