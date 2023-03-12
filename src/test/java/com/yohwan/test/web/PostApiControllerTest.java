package com.yohwan.test.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.yohwan.test.domain.posts.Post;
import com.yohwan.test.domain.posts.PostRepository;
import com.yohwan.test.web.dto.posts.PostsSaveRequestDto;
import com.yohwan.test.web.dto.posts.PostsUpdateRequestDto;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostApiControllerTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	@After
	public void tearDown() {
		postRepository.deleteAll();
	}
	
	@Test
	@WithMockUser(roles = "USER")
	public void createPosts() throws Exception{
		String title = "title";
		String content = "content";
		PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
				.title(title)
				.content(content)
				.author("yhlim")
				.build();
		
		String url = "http://localhost:" + port + "/api/v1/posts";
		
		ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

		mvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(requestDto)))
				.andExpect(status().isOk());
		
//		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//		assertThat(responseEntity.getBody()).isGreaterThan(0L);
		
		List<Post> all = postRepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo(title);
		assertThat(all.get(0).getContent()).isEqualTo(content);
	}
	
	@Test
	@WithMockUser(roles = "USER")
	public void updatePosts() throws Exception{
		Post savedPost = postRepository.save(Post.builder()
				.title("title")
				.content("content")
				.author("author")
				.build());
		
		Long updateId = savedPost.getId();
		String expectedTitle = "title2";
		String expectedContent = "content2";
		
		PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
				.title(expectedTitle)
				.content(expectedContent)
				.build();
		
		String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;
		
		HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
		
		ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

		mvc.perform(put(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(requestDto)))
				.andExpect(status().isOk());
		
//		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//		assertThat(responseEntity.getBody()).isGreaterThan(0L);
		
		List<Post> all = postRepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
		assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
		
	}

}
