package com.schedule.dto;

import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {
    private String title;
    private String content;
    private String author;

    // 요청할때는 비밀번호 포함
    private String password;
}
