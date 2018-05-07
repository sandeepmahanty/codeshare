package com.sandeep.backend.service;

import com.sandeep.backend.model.request.ExecuteCodeRequest;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class CodeService {

    @Autowired
    JavaCompilerService javaCompilerService;
    public static final String BASE_PATH = "";

    public String getDefaultCode(String languageId) {
        try {
            String code = IOUtils.toString(ClassLoader.getSystemClassLoader().getResourceAsStream("default/java_default.txt"), "utf-8");
            return code;
        } catch (IOException ex) {
            System.out.println("Failed due to " + ex.getMessage());
        }
        return "void myScript(){return 100;}";
    }

    public String executeCode(ExecuteCodeRequest request) throws Exception {
        return javaCompilerService.compileCode(request);
    }

}
