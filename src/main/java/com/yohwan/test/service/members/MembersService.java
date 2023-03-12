package com.yohwan.test.service.members;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yohwan.test.domain.members.Member;
import com.yohwan.test.domain.members.MemberRepository;

import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
@Service
public class MembersService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Transactional
	public Long save(Member member) {
		String encPassword = passwordEncoder.encode(member.getPassword());
		member.changeEncPassword(encPassword);
		return memberRepository.save(member).getId();
	}

}