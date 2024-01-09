package com.bbc.common;

import org.springframework.boot.system.ApplicationHome;

import java.io.File;

public class JarUtil {

    private static String getJarFilePath() {
        ApplicationHome home = new ApplicationHome(JarUtil.class);
        File jarFile = home.getSource();
        return jarFile.getParentFile().toString();
    }
}
