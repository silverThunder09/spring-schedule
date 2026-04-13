package com.schedule.service;

import com.schedule.dto.CreateScheduleRequestDto;
import com.schedule.dto.CreateScheduleResponseDto;
import com.schedule.entity.Schedule;
import com.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public CreateScheduleResponseDto save(CreateScheduleRequestDto request) {
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                request.getAuthor(),
                request.getPassword()
        );
        Schedule saved = scheduleRepository.save(schedule);

        return new CreateScheduleResponseDto(
                saved.getId(),
                saved.getTitle(),
                saved.getContent(),
                saved.getAuthor(),
                saved.getCreateAt(),
                saved.getModifiedAt());
    }
}
