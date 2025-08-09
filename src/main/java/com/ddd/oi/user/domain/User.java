package com.ddd.oi.user.domain;

import java.time.LocalDateTime;

import com.ddd.oi.common.domain.BaseEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "user")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "provider_info", nullable = false)
	private ProviderInfo providerInfo;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "profile_image_url")
	private String profileImageUrl;

	@Column(name = "email")
	private String email;

	@Enumerated(EnumType.STRING)
	private RoleType role;

	@Column(name = "last_read_at")
	private LocalDateTime lastReadAt;
	public void updateLastReadAt(LocalDateTime lastReadAt) {
		this.lastReadAt = lastReadAt;
	}
	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updateProfileUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

}

