package springboot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springboot.config.auth.LoginUser;
import springboot.config.auth.dto.SessionUser;
import springboot.service.PostsService;
import springboot.web.dto.PostsResponseDto;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

	private final PostsService postsService;
//	private final HttpSession httpSession;

	
	// @LoginUser로 기존의 httpSession.getAttribute("user")로 가져오던 세션 정보값이 개선되었다
	// 어느 컨트롤러든지 @LoginUser만 사용하면 세션 정보를 가져올 수 있다
	@GetMapping("/")
	public String index(Model model, @LoginUser SessionUser user) {
		model.addAttribute("posts", postsService.findallDesc());
//		SessionUser user = (SessionUser) httpSession.getAttribute("user");
		// CustomOAuth2UserService에서 로그인 성공시 세션에 SessionUser를 저장
		// 로그인 성공시 httpSession.getAttribute("user")에서 값을 가져올 수 있다
		if (user != null) {
			model.addAttribute("userName", user.getName());
			// 세션에 저장된 값이 있을 때만 model에 userName으로 등록
		}
		return "index";
	}

	@GetMapping("/posts/save")
	public String postsSave() {
		return "posts-save";
	}

	@GetMapping("/posts/update/{id}")
	public String postsUpdate(@PathVariable Long id, Model model) {
		PostsResponseDto dto = postsService.findById(id);
		model.addAttribute("post", dto);

		return "posts-update";
	}
}
