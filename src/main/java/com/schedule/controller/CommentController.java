package com.schedule.controller;

import com.schedule.dto.CreateCommentRequestDto;
import com.schedule.dto.CreateCommentResponseDto;
import com.schedule.dto.ErrorResponseDto;
import com.schedule.exception.InvalidRequestException;
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
    public ResponseEntity<?> createComment(@PathVariable Long scheduleId, @RequestBody CreateCommentRequestDto request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(scheduleId, request));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(e.getMessage()));
        }
    }
}
