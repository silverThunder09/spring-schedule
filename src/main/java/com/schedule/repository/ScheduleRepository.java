package com.schedule.repository;

import com.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // 전체 조회 - 수정일 내림차순
    List<Schedule> findAllByOrderByModifiedAtDesc();

    // 작성자 필터 조회 — 수정일 내림차순
    List<Schedule> findByAuthorOrderByModifiedAtDesc(String author);
}
