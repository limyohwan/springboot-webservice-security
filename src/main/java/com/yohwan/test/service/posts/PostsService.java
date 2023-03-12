package com.yohwan.test.service.posts;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yohwan.test.domain.posts.Posts;
import com.yohwan.test.domain.posts.PostsRepository;
import com.yohwan.test.web.dto.posts.PostsListResponseDto;
import com.yohwan.test.web.dto.posts.PostsResponseDto;
import com.yohwan.test.web.dto.posts.PostsSaveRequestDto;
import com.yohwan.test.web.dto.posts.PostsUpdateRequestDto;

import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostsService {
	
	private final PostsRepository postsRepository;
	
	@Transactional
	public Long save(PostsSaveRequestDto requestDto) {
		return postsRepository.save(requestDto.toEntity()).getId();
	}
	
	@Transactional
	public Long update(Long id, PostsUpdateRequestDto requestDto) {
		Posts posts = postsRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("해당게시글이 없습니다. id = " + id));
		
		posts.update(requestDto.getTitle(), requestDto.getContent());
		
		return id;
	}
	
	public PostsResponseDto findById(Long id) {
		Posts entity = postsRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("해당게시글이 없습니다. id = " + id));
		
		return new PostsResponseDto(entity);
	}
	
	@Transactional(readOnly=true)
	public List<PostsListResponseDto> findAllDesc(){
		return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
	}
	
	@Transactional
	public void delete(Long id) {
		Posts posts = postsRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("해당게시글이 없습니다. id = " + id));
		
		postsRepository.delete(posts);
	}

}
