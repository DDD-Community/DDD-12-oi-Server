package com.ddd.oi.contents_image.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.ddd.oi.contents_image.domain.ContentsImage;

@Repository
public interface ContentsImageRepository extends JpaRepository<ContentsImage, Long> {
}
