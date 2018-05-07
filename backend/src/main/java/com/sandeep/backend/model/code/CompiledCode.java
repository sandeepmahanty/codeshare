package com.sandeep.backend.model.code;


import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * Created by sandeep.kumar08 on 2/3/2016.
 */
public class CompiledCode extends SimpleJavaFileObject {
    private ByteArrayOutputStream baos = new ByteArrayOutputStream();

    public CompiledCode(String className) throws Exception {
        super(new URI(className), Kind.CLASS);
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return baos;
    }

    public byte[] getByteCode() {
        return baos.toByteArray();
    }
}

