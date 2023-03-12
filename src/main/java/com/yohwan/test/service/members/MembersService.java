package com.yohwan.test.service.members;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yohwan.test.domain.members.Members;
import com.yohwan.test.domain.members.MembersRepository;

import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
@Service
public class MembersService {
	private final MembersRepository membersRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Transactional
	public Long save(Members members) {
		String encPassword = passwordEncoder.encode(members.getPassword());
		members.changeEncPassword(encPassword);
		return membersRepository.save(members).getId();
	}

}