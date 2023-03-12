package com.yohwan.test.web.dto.posts;

import java.time.LocalDateTime;

import com.yohwan.test.domain.posts.Post;

import lombok.Getter;

@Getter
public class PostsListResponseDto {
	private Long id;
	private String title;
	private String author;
	private LocalDateTime modifiedDate;
	
	public PostsListResponseDto(Post entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.author = entity.getAuthor();
		this.modifiedDate = entity.getModifiedDate();
	}
}
