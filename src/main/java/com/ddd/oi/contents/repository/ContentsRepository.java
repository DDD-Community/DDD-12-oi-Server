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

	List<Contents> findByContentsTag(ContentsTag contentsTag);

	List<Contents> findByTitleContaining(String title);

	@Query("SELECT c FROM Contents c WHERE c.cost BETWEEN :minCost AND :maxCost")
	List<Contents> findByCostRange(@Param("minCost") Integer minCost, @Param("maxCost") Integer maxCost);

	@Query("SELECT c FROM Contents c WHERE c.duration <= :maxDuration")
	List<Contents> findByMaxDuration(@Param("maxDuration") Integer maxDuration);

	List<Contents> findByRecommendedSchedule(String recommendedSchedule);

	@Query("SELECT c FROM Contents c LEFT JOIN FETCH c.images LEFT JOIN FETCH c.spots WHERE c.id = :id")
	Optional<Contents> findByIdWithImagesAndSpots(@Param("id") Long id);
}
