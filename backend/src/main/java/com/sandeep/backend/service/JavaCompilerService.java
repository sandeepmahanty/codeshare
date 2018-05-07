package com.sandeep.backend.service;

import com.sandeep.backend.model.request.ExecuteCodeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Method;

/**
 * Created by sandeep.kumar08 on 2/5/2016.
 */
@Service
public class JavaCompilerService {

    @Autowired
    CodeExecutionService codeExecutionService;


    public String compileCode(ExecuteCodeRequest request) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        ByteArrayInputStream bais = new ByteArrayInputStream(request.getContent().getBytes());
        PrintStream oldOutput = System.out;
        InputStream oldInput = System.in;
        String result = "";
        try {
            System.setOut(ps);
            System.setIn(bais);
            Class<?> helloClass = codeExecutionService.compile("Solution", request.getContent(), new PrintWriter(System.out));

            if (helloClass != null) {
                Method[] methods = helloClass.getMethods();
                String[] args = {"12344", "123"};
                Object[][] arguments = {args};
                methods[0].invoke(helloClass, arguments);
                System.out.flush();
                System.setOut(oldOutput);
                System.setIn(oldInput);
                result = baos.toString();
                System.out.println("Result: " + result);
                return result;
            } else {
                return baos.toString();
            }
        } catch (Exception e) {
            System.out.println("Failed due to :" + e.getMessage());
            return e.getMessage();
        } finally {
            System.setOut(oldOutput);
            System.setIn(oldInput);
        }
    }
}
