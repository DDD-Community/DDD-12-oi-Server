package com.ddd.oi.schedule_tag.repository;
import com.ddd.oi.schedule_tag.domain.ScheduleTag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ScheduleTagRepository extends JpaRepository<ScheduleTag, Long> {
}
