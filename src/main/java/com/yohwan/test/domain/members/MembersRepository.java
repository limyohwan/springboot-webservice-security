package com.yohwan.test.domain.members;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MembersRepository extends JpaRepository<Members, Long> {
	
	Members findByUsername(String username);
}
