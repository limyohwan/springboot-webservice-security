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
public class PostRepositoryTest {
	
	@Autowired
	PostRepository postRepository;
	
	@After
	public void cleanup() {
		postRepository.deleteAll();
	}
	
	@Test
	public void getPosts() {
		String title = "test title";
		String content = "test content";
		
		postRepository.save(Post.builder()
				.title(title)
				.content(content)
				.author("yhlim")
				.build());
		
		List<Post> postList = postRepository.findAll();
		
		Post post = postList.get(0);
		assertThat(post.getTitle()).isEqualTo(title);
		assertThat(post.getContent()).isEqualTo(content);
	}
	
	@Test
	public void createBaseTimeEntity() {
		LocalDateTime now = LocalDateTime.of(2022, 8,16,0,0,0);
		postRepository.save(Post.builder()
				.title("title")
				.content("content")
				.author("yhlim")
				.build());
		
		List<Post> postList = postRepository.findAll();
		
		Post post = postList.get(0);

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> crd = " + post.getCreatedDate() + ", mod = " + post.getModifiedDate());
		assertThat(post.getCreatedDate()).isAfter(now);
		assertThat(post.getModifiedDate()).isAfter(now);
		
	}
}
