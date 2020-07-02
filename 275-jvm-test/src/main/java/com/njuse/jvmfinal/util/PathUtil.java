package com.njuse.jvmfinal.util;

import java.io.File;

public class PathUtil {
    public PathUtil() {
    }

    public static String transform(String pathName) {
        return pathName.contains("/") ? pathName.replace("/", File.separator) : pathName;
    }
}