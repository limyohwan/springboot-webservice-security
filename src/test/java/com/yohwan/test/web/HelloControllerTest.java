package com.yohwan.test.web;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.yohwan.test.security.CustomOauth2UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.yohwan.test.web.controller.HelloController;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
@MockBeans({
		@MockBean(JpaMetamodelMappingContext.class),
		@MockBean(CustomOauth2UserService.class)
})
public class HelloControllerTest {
	@Autowired
	private MockMvc mvc;
	
	@Test
	@WithMockUser(username = "user", roles = {"USER"})
	public void returnHello() throws Exception{
		String hello = "hello";
		mvc.perform(get("/hello")).andExpect(status().isOk()).andExpect(content().string(hello));
	}
	
	@Test
	@WithMockUser(username = "user", roles = {"USER"})
	public void returnHelloDto() throws Exception{
		String name = "hello";
		int amount = 1000;
		
		mvc.perform(
				get("/hello/dto")
					.param("name", name)
					.param("amount", String.valueOf(amount)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(name)))
				.andExpect(jsonPath("$.amount", is(amount)));
	}
}
