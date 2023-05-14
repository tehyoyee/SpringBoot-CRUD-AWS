package springboot.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import springboot.config.auth.SecurityConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = HelloController.class)
@WebMvcTest(controllers = HelloController.class,
		excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
		})
public class HelloControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	@WithMockUser(roles="USER")
	public void hello가_리턴된다() throws Exception {
		String hello = "hello";
		mvc.perform(get("/hello"))
				.andExpect(status().isOk())
				.andExpect(content().string(hello));
	}

	@Test
	@WithMockUser(roles="USER")
	public void helloDto가_리턴된다() throws Exception {
		String name = "hello";
		int amount = 1000;

		mvc.perform(get("/hello/dto")
				.param("name", name)
				.param("amount", String.valueOf(amount)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(name))
				.andExpect(jsonPath("$.amount").value(amount));
	}

	public static class HelloControllerDtoTest {

		@Test
		public void 롬복_기능_테스트() {
			//given
			String name = "test";
			int amount = 1000;

			//when
		}
	}
}
