package com.yohwan.test.web.dto.posts;

import com.yohwan.test.domain.posts.Post;

import lombok.Getter;

@Getter
public class PostsResponseDto {
	private Long id;
	private String title;
	private String content;
	private String author;
	
	public PostsResponseDto(Post entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.author = entity.getAuthor();
	}
	
	
}
