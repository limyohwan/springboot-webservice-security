package com.yohwan.test.web.controller;

import com.yohwan.test.security.auth.PrincipalDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.yohwan.test.domain.members.Members;
import com.yohwan.test.service.members.MembersService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SignController {
	private static final Logger log = LoggerFactory.getLogger(SignController.class);
	
	private final MembersService membersService;

	@GetMapping("/login")
	public String index() {
		return "sign/signin_view";
	}
	
	@GetMapping("/signup")
	public String getSignup() {
		return "sign/signup_view";
	}
	
	@PostMapping("/signup")
	public String signup(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
		Members member = Members.builder()
								.username(username)
								.password(password)
								.email(email)
								.role("ROLE_USER")
								.build();
		membersService.save(member);
		return "redirect:/login";
	}

	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("name : " + principalDetails.getName() );
		System.out.println("getUsername : " + principalDetails.getUsername());
		System.out.println("getAttributes : " + principalDetails.getAttributes() );

		return "user";
	}

	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}

	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/info")
	public @ResponseBody String info(){
		return "개인정보";
	}

	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/data")
	public @ResponseBody String data(){
		return "데이터정보";
	}

	@GetMapping("/test/login")
	public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails){
		System.out.println("/test/login");
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("PrincipalDetails : + " + principalDetails.getUsername());

		System.out.println("userDetails : " + userDetails.getUsername());
		return "세션정보확인하기";
	}

	@GetMapping("/test/oauth/login")
	public @ResponseBody String testOAuthLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth){
		System.out.println("/test/login");
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		System.out.println("oauth2 : " + oAuth2User.getAttributes());
		System.out.println("oauth2123123123 : " + oauth.getAttributes());

		return "oauth세션정보확인하기";
	}
}
