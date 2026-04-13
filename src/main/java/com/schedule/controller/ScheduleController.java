package com.schedule.controller;

import com.schedule.dto.*;
import com.schedule.exception.InvalidPasswordException;
import com.schedule.exception.InvalidRequestException;
import com.schedule.exception.ScheduleNotFoundException;
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
    public ResponseEntity<?> createSchedule(@RequestBody CreateScheduleRequestDto request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(request));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(e.getMessage()));
        }
    }

    // 요청 url : schedules?author=홍길동
    @GetMapping
    public ResponseEntity<List<GetScheduleResponseDto>> getAllSchedules(@RequestParam(required = false) String author) {

        List<GetScheduleResponseDto> responseList = scheduleService.getAll(author);
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<?> getOneSchedule(@PathVariable Long scheduleId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getOne(scheduleId));
        } catch (ScheduleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<?> updateSchedule(@PathVariable Long scheduleId, @RequestBody UpdateScheduleRequestDto request) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(scheduleId, request));
        } catch (ScheduleNotFoundException | InvalidPasswordException | InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long scheduleId, @RequestBody DeleteScheduleRequestDto request) {

        try {
            scheduleService.delete(scheduleId, request);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (InvalidPasswordException | ScheduleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(e.getMessage()));
        }
    }
}
