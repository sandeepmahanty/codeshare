package com.sandeep.backend.service;

import com.sandeep.backend.model.request.ExecuteCodeRequest;
import com.sandeep.backend.model.response.ExecuteCodeResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Method;

/**
 * Created by sandeep.kumar08 on 2/5/2016.
 */
@Service
public class JavaCompilerService {
    private static final Logger log = LogManager.getLogger(CodeService.class.getName());
    public static final String CLASS_NAME = "Solution";

    @Autowired
    CodeExecutionService codeExecutionService;

    public ExecuteCodeResponse compileCode(ExecuteCodeRequest request) throws Exception {
        ExecuteCodeResponse response = new ExecuteCodeResponse();
        response.setLanguage(request.getLanguage());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        //TODO: fix for no custom input
        ByteArrayInputStream bais = new ByteArrayInputStream(request.getInput().getBytes());
        PrintStream oldOutput = System.out;
        InputStream oldInput = System.in;
        String result = "";
        try {
            System.setOut(ps);
            System.setIn(bais);
            Class<?> helloClass = codeExecutionService.compile(CLASS_NAME, request.getContent(), new PrintWriter(System.out));

            if (helloClass != null) {
                Method[] methods = helloClass.getMethods();
                String[] args = {"12344", "123"};
                Object[][] arguments = {args};
                methods[0].invoke(helloClass, arguments);
                System.out.flush();
                System.setOut(oldOutput);
                System.setIn(oldInput);
                result = baos.toString();
                log.info(String.format("compiled code {code} to: {result}", request.getContent(), result));
                response.setError(false);
                response.setContent(result);
            } else {
                result = baos.toString();
                log.info(String.format("failed to compile code {code} result: {result}", request.getContent(), result));
                response.setError(true);
                response.setContent(result);
            }
        } catch (Exception e) {
            log.error("failed due to " + e.getMessage());
        } finally {
            System.setOut(oldOutput);
            System.setIn(oldInput);
        }
        return response;
    }
}
