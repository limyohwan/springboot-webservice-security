package com.yohwan.test.domain.posts;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {
	
	@Autowired
	PostsRepository postsRepository;
	
	@After
	public void cleanup() {
		postsRepository.deleteAll();
	}
	
	@Test
	public void getPosts() {
		String title = "test title";
		String content = "test content";
		
		postsRepository.save(Posts.builder()
				.title(title)
				.content(content)
				.author("yhlim")
				.build());
		
		List<Posts> postsList = postsRepository.findAll();
		
		Posts posts = postsList.get(0);
		assertThat(posts.getTitle()).isEqualTo(title);
		assertThat(posts.getContent()).isEqualTo(content);
	}
	
	@Test
	public void createBaseTimeEntity() {
		LocalDateTime now = LocalDateTime.of(2022, 8,16,0,0,0);
		postsRepository.save(Posts.builder()
				.title("title")
				.content("content")
				.author("yhlim")
				.build());
		
		List<Posts> postsList = postsRepository.findAll();
		
		Posts posts = postsList.get(0);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> crd = " + posts.getCreatedDate() + ", mod = " + posts.getModifiedDate());
		assertThat(posts.getCreatedDate()).isAfter(now);
		assertThat(posts.getModifiedDate()).isAfter(now);
		
	}
}
