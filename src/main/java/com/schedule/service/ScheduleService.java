package com.schedule.service;

import com.schedule.dto.CreateScheduleRequestDto;
import com.schedule.dto.CreateScheduleResponseDto;
import com.schedule.dto.GetScheduleResponseDto;
import com.schedule.entity.Schedule;
import com.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateScheduleResponseDto save(CreateScheduleRequestDto request) {
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                request.getAuthor(),
                request.getPassword()
        );
        Schedule saved = scheduleRepository.save(schedule);

        return new CreateScheduleResponseDto(saved);
    }

    @Transactional(readOnly = true)
    public List<GetScheduleResponseDto> getAll(String author) {
        List<Schedule> schedules;

        if (author == null) {
            schedules = scheduleRepository.findAllByOrderByModifiedAtDesc(); // 작성자가 포함되어 있지 않으면 전체 조회, 수정일 내림차순
        } else {
            schedules = scheduleRepository.findByAuthorOrderByModifiedAtDesc(author); // 작성자가 포함되어 있으면 필터 조회, 수정일 내림차순
        }

        List<GetScheduleResponseDto> result = new ArrayList<>();

        for (Schedule schedule : schedules) {
            result.add(new GetScheduleResponseDto(schedule));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public GetScheduleResponseDto getOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("해당 id에 대한 일정이 없습니다. id = " + scheduleId)
        );
        return new GetScheduleResponseDto(schedule);
    }
}
