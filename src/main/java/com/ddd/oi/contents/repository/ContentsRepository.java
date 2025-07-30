package com.ddd.oi.contents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.ddd.oi.contents.domain.Contents;
import com.ddd.oi.contents.domain.enumType.ContentsTag;

@Repository
public interface ContentsRepository extends JpaRepository<Contents, Long> {
	List<Contents> findByContentsTagOrderByViewCountDesc(ContentsTag contentsTag);
	List<Contents> findByContentsTagOrderByRecommendationScoreDesc(ContentsTag contentsTag);
	List<Contents> findByContentsTagOrderByCreatedAtDesc(ContentsTag contentsTag);
	List<Contents> findAllByOrderByViewCountDesc();
	List<Contents> findAllByOrderByRecommendationScoreDesc();
	List<Contents> findAllByOrderByCreatedAtDesc();
	@Query("SELECT c FROM Contents c LEFT JOIN FETCH c.images LEFT JOIN FETCH c.spots WHERE c.id = :id")
	Optional<Contents> findByIdWithImagesAndSpots(@Param("id") Long id);
}
