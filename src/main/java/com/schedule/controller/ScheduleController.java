package com.schedule.controller;

import com.schedule.dto.CreateScheduleRequestDto;
import com.schedule.dto.CreateScheduleResponseDto;
import com.schedule.dto.GetScheduleResponseDto;
import com.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(@RequestBody CreateScheduleRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(request));
    }

    // 요청 url : schedules?author=홍길동
    @GetMapping
    public ResponseEntity<List<GetScheduleResponseDto>> getAllSchedules(@RequestParam(required = false) String author) {

        List<GetScheduleResponseDto> responseList = scheduleService.getAll(author);
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetScheduleResponseDto> getOneSchedules(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getOne(scheduleId));
    }
}
