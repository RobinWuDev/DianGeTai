package com.robinwu.diangetai.config;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by Robin on 16/6/15.
 */
public class Config {
    public static final int UPDATE_NUMBER = 3;

    public static final String DOMAIN = "http://172.25.121.183:8080";

    private static final String UPLOAD_PATH = "uploads";

    private static final String OUTPUT_DIR = "resources/file";

    public static String getUploadPath() {
        return getDir(UPLOAD_PATH);
    }

    public static String getOutputDir() {
        return getDir(OUTPUT_DIR);
    }

    public static String getDir(String name) {
        String dir = "/Users/Robin/Documents/Product/ShareMusic/target/ShareMusic/" + name;
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdir();
        }
        return dir;
    }
}
