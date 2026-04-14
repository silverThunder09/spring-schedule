package com.schedule.service;

import com.schedule.dto.*;
import com.schedule.entity.Comment;
import com.schedule.entity.Schedule;
import com.schedule.exception.InvalidPasswordException;
import com.schedule.exception.InvalidRequestException;
import com.schedule.exception.ScheduleNotFoundException;
import com.schedule.repository.CommentRepository;
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
    private final CommentRepository commentRepository;

    @Transactional
    public CreateScheduleResponseDto save(CreateScheduleRequestDto request) {

        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new InvalidRequestException("제목은 필수 값 입니다.");
        }

        if (request.getContent() == null || request.getContent().isBlank()) {
            throw new InvalidRequestException("내용은 필수 값 입니다.");
        }

        if (request.getAuthor() == null || request.getAuthor().isBlank()) {
            throw new InvalidRequestException("작성자명은 필수 값 입니다.");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new InvalidRequestException("비밀번호는 필수 값 입니다.");
        }

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
                () -> new ScheduleNotFoundException("해당 id에 대한 일정이 없습니다. id = " + scheduleId));

        // 댓글 목록 조회
        List<Comment> comments = commentRepository.findByScheduleId(scheduleId);

        List<CreateCommentResponseDto> dtos = new ArrayList<>();
        for(Comment comment : comments) {
            dtos.add((new CreateCommentResponseDto(comment)));
        }

        return new GetScheduleResponseDto(schedule, dtos);
    }

    @Transactional
    public UpdateScheduleResponseDto update(Long scheduleId, UpdateScheduleRequestDto request) {

        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new InvalidRequestException("제목은 필수 값 입니다.");
        }

        if (request.getAuthor() == null || request.getAuthor().isBlank()) {
            throw new InvalidRequestException("작성자명은 필수 값 입니다.");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new InvalidRequestException("비밀번호는 필수 값 입니다.");
        }

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("해당 id에 대한 일정이 없습니다. id = " + scheduleId)
        );

        if(!schedule.getPassword().equals(request.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }

        // 더티 체킹
        schedule.update(request.getTitle(), request.getAuthor());

        return new UpdateScheduleResponseDto(schedule);
    }

    @Transactional
    public void delete(Long scheduleId, DeleteScheduleRequestDto request) {

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("해당 id에 대한 일정이 없습니다. id = " + scheduleId));

        if (!schedule.getPassword().equals(request.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }

        scheduleRepository.deleteById(scheduleId);
    }


}
