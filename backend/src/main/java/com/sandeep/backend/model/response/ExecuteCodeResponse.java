package com.sandeep.backend.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExecuteCodeResponse {
    private String content;
    private String language;
}
