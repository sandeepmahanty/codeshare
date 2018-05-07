package com.sandeep.backend.service;

import com.sandeep.backend.model.code.CompiledCode;
import com.sandeep.backend.model.code.SourceCode;
import org.springframework.stereotype.Service;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.io.PrintWriter;
import java.util.Arrays;

@Service
public class CodeExecutionService {
    JavaCompiler javac = ToolProvider.getSystemJavaCompiler();

    public Class<?> compile(String className, String sourceCodeInText,PrintWriter out) throws Exception {

        SourceCode sourceCode = new SourceCode(className, sourceCodeInText);
        CompiledCode compiledCode = new CompiledCode(className);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(sourceCode);
        DynamicClassLoader cl = new DynamicClassLoader(ClassLoader.getSystemClassLoader());
        ExtendedStandardJavaFileManager fileManager = new ExtendedStandardJavaFileManager(javac.getStandardFileManager(null, null, null), compiledCode, cl);

        JavaCompiler.CompilationTask task = javac.getTask(out, fileManager, null, null, null, compilationUnits);

        boolean result = task.call();

        return (result ? cl.loadClass(className) : null);
    }

}
