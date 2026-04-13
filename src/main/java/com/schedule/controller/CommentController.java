package com.schedule.controller;

import com.schedule.dto.CreateCommentRequestDto;
import com.schedule.dto.CreateCommentResponseDto;
import com.schedule.dto.CreateScheduleRequestDto;
import com.schedule.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/{scheduleId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CreateCommentResponseDto> createComment(@PathVariable Long scheduleId, @RequestBody CreateCommentRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(scheduleId, request));
    }
}
