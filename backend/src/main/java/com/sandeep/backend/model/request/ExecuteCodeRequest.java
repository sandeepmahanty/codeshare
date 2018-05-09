package com.sandeep.backend.model.request;

import lombok.Data;

@Data
public class ExecuteCodeRequest {
    private String content;
    private String language;
    private String input;
}
