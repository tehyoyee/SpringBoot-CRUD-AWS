package springboot.web.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springboot.domain.posts.Posts;
import springboot.domain.posts.PostsRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest     // 별다른 설정이 없으면 h2 데이터베이스를 자동으로 실행
public class PostsRepositoryTest {

	@Autowired
	PostsRepository postsRepository;

	@After      // 끌날 때 수행하는 메소드; 테스트간 간섭을 줄이기위해 데이트삭제용 메소드
	public void cleanup() {
		postsRepository.deleteAll();
	}

	@Test
	public void 게시글저장_불러오기() {
		//given
		String title = "테스트 게시글";
		String content = "테스트 본문";

		postsRepository.save(Posts.builder()        // 테이블 Posts에 insert/update 쿼리를 실행
				.title(title)
				.content(content)
				.author("tehyoyee")
				.build());

		//when
		List<Posts> postsList = postsRepository.findAll();  // 테이블 Posts의 모든 데이터를 조회

		//then
		Posts posts = postsList.get(0);
		assertThat(posts.getTitle()).isEqualTo(title);
		assertThat(posts.getContent()).isEqualTo(content);
	}

	@Test
	public void BaseTimeEntity_등록() {
		//given
		LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0);
		postsRepository.save(Posts.builder()
				.title("title")
				.content("content")
				.author("author")
				.build());

		//when
		List<Posts> postsList = postsRepository.findAll();

		//then
		Posts posts = postsList.get(0);

		System.out.println(">>>>>>>>>> createDate=" + now + "  modifiedDate=" + posts.getModifiedDate());
		System.out.println(">>>>>>>>>> createDate=" + posts.getCreatedDate() + "  modifiedDate=" + posts.getModifiedDate());

		assertThat(posts.getCreatedDate()).isAfter(now);
		assertThat(posts.getModifiedDate()).isAfter(now);

	}
}
