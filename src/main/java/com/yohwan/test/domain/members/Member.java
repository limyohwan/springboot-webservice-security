package com.yohwan.test.domain.members;

import javax.persistence.*;

import com.yohwan.test.domain.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;
	private String email;

	@Enumerated(EnumType.STRING)
	private Role role;
	private String provider;
	private String providerId;

	@Builder
	public Member(String username, String password, String email, Role role, String provider, String providerId) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.provider = provider;
		this.providerId = providerId;
	}
	
	public void changeEncPassword(String password) {
		this.password = password;
	}
}