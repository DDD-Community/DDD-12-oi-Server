package com.ddd.oi.contents_spot.repository;


import com.ddd.oi.contents_spot.domain.ContentsSpot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentsSpotRepository extends JpaRepository<ContentsSpot, Long> {
}
