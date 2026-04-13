package com.schedule.dto;

import com.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateScheduleResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiredAt;

    public CreateScheduleResponseDto(Long id, String title, String content, String author, LocalDateTime createdAt, LocalDateTime modifiredAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.modifiredAt = modifiredAt;
    }

}
