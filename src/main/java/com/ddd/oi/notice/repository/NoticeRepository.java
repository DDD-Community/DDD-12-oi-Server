package com.ddd.oi.notice.repository;

import com.ddd.oi.notice.domain.Notice;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findTopByOrderByUpdatedAtDesc();
}
