package com.schedule.dto;

import lombok.Getter;

@Getter
public class CreateCommentRequestDto {
    private String content;
    private String author;
    private String password;
}
