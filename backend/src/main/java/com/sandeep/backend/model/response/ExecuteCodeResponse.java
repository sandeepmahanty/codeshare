package com.sandeep.backend.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteCodeResponse extends AbstractResponse{
    private String content;
    private String language;
    private boolean isError;
    private double time;
}
