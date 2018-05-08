package com.sandeep.backend.service;

import com.sandeep.backend.model.code.Code;
import com.sandeep.backend.model.request.ExecuteCodeRequest;
import com.sandeep.backend.repository.CodeRepository;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CodeService {
    private static final Logger log = LogManager.getLogger(CodeService.class.getName());
    public static final String DEFAULT_CODE = "void myScript(){return 100;}";
    @Autowired
    CodeRepository codeRepository;

    @Autowired
    JavaCompilerService javaCompilerService;

    public String getDefaultCode(String languageId) {
        try {
            Optional<Code> codeOptional = codeRepository.findById(languageId);
            if (codeOptional.isPresent()) {
                log.info(String.format("returning default code for {%s} : {%s}", languageId, codeOptional.get().getCode()));
                return codeOptional.get().getCode();
            }
        } catch (Exception ex) {
            System.out.println("Failed due to " + ex.getMessage());
        }
        log.info(String.format("returning default code for {%s} : {%s}", languageId, DEFAULT_CODE));
        return DEFAULT_CODE;
    }

    public String executeCode(ExecuteCodeRequest request) throws Exception {
        return javaCompilerService.compileCode(request);
    }
}
