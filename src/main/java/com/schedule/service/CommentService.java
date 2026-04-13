package com.schedule.service;

import com.schedule.dto.CreateCommentRequestDto;
import com.schedule.dto.CreateCommentResponseDto;
import com.schedule.dto.CreateScheduleRequestDto;
import com.schedule.entity.Comment;
import com.schedule.repository.CommentRepository;
import com.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateCommentResponseDto save(Long scheduleId, CreateCommentRequestDto request) {
        scheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalStateException("해당 id에 대한 일정이 없습니다. id = " + scheduleId));

        List<Comment> commentList = commentRepository.findByScheduleId(scheduleId);

        if (commentList.size() >= 10) {
            throw new IllegalStateException("댓글은 최대 10까지만 작성 할 수 있습니다.");
        }

        Comment comment = new Comment(request.getContent(), request.getAuthor(), request.getPassword(), scheduleId);

        return new CreateCommentResponseDto(commentRepository.save(comment));
    }
}
