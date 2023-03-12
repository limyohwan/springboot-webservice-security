package com.yohwan.test.service.posts;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yohwan.test.domain.posts.Post;
import com.yohwan.test.domain.posts.PostRepository;
import com.yohwan.test.web.dto.posts.PostsListResponseDto;
import com.yohwan.test.web.dto.posts.PostsResponseDto;
import com.yohwan.test.web.dto.posts.PostsSaveRequestDto;
import com.yohwan.test.web.dto.posts.PostsUpdateRequestDto;

import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostsService {
	
	private final PostRepository postRepository;
	
	@Transactional
	public Long save(PostsSaveRequestDto requestDto) {
		return postRepository.save(requestDto.toEntity()).getId();
	}
	
	@Transactional
	public Long update(Long id, PostsUpdateRequestDto requestDto) {
		Post post = postRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("해당게시글이 없습니다. id = " + id));
		
		post.update(requestDto.getTitle(), requestDto.getContent());
		
		return id;
	}
	
	public PostsResponseDto findById(Long id) {
		Post entity = postRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("해당게시글이 없습니다. id = " + id));
		
		return new PostsResponseDto(entity);
	}
	
	@Transactional(readOnly=true)
	public List<PostsListResponseDto> findAllDesc(){
		return postRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
	}
	
	@Transactional
	public void delete(Long id) {
		Post post = postRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("해당게시글이 없습니다. id = " + id));
		
		postRepository.delete(post);
	}

}
