package com.yohwan.test.web.controller;

import com.yohwan.test.security.auth.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.yohwan.test.service.posts.PostsService;
import com.yohwan.test.web.dto.posts.PostsResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PostsController {
	
	private final PostsService postsService;
	
	@GetMapping("/")
	public String index(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		model.addAttribute("posts", postsService.findAllDesc());
		if(principalDetails != null){
			model.addAttribute("userName", principalDetails.getUsername());
		}
		return "posts/posts_view";
	}
	
	@GetMapping("/posts/save")
	public String postsSave() {
		return "posts/posts_save";
	}
	
	@GetMapping("/posts/update/{id}")
	public String postsUpdate(@PathVariable Long id, Model model) {
		PostsResponseDto dto = postsService.findById(id);
		model.addAttribute("post", dto);
		return "posts/posts_update";
	}
}
