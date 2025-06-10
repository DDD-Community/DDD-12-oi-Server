package com.ddd.oi.grouptag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.ddd.oi.grouptag.domain.GroupTag;

@Repository
public interface GroupTagRepository extends JpaRepository<GroupTag, Long> {
}
