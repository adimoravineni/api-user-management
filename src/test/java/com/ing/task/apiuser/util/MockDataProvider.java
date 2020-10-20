package com.ing.task.apiuser.util;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class MockDataProvider {

    private static String readFile(String path) throws IOException {
        Resource resource = new DefaultResourceLoader().getResource(path);
        return IOUtils.toString(resource.getInputStream(), "UTF-8");
    }

    public static String userPatch_sample_json() throws IOException {
        return readFile("classpath:json/user-patch-sample.json");
    }

    public static String userPatch_malformed_json() throws IOException {
        return readFile("classpath:json/user-patch-malformed.json");
    }
}
