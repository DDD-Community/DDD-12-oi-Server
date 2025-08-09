package com.ddd.oi.contents.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ddd.oi.contents.domain.Contents;
import com.ddd.oi.contents.domain.enumType.ContentsTag;

@Repository
public interface ContentsRepository extends JpaRepository<Contents, Long> {
	@EntityGraph(attributePaths = {"spots"})
	Optional<Contents>  findById(Long id);
	@EntityGraph(attributePaths = {"spots"})

	List<Contents> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);}
