package com.ddd.oi.user.domain;

import com.ddd.oi.common.domain.BaseEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@Enumerated(EnumType.STRING)
	@Column(name = "login_type", nullable = false)
	private LoginType loginType;

	@Column(name = "provider", nullable = false)
	private String provider;

	@Column(name = "is_dormant", nullable = false)
	private Boolean isDormant = false;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "profile_url")
	private String profileUrl;

	@Column(name = "email")
	private String email;

	public enum LoginType {
		KAKAO, GOOGLE, NAVER, APPLE
	}
}
