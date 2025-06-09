package com.ddd.oi.contents_image.domain;


import com.ddd.oi.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contents_image")
@Getter
@Setter
@NoArgsConstructor
public class ContentsImage extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contents_image_id")
	private Long contentsImageId;

	@Column(name = "image_url", nullable = false)
	private String imageUrl;
}
