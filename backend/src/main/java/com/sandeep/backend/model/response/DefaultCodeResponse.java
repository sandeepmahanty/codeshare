package com.sandeep.backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DefaultCodeResponse extends AbstractResponse{
    private String content;
    private String language;
}
