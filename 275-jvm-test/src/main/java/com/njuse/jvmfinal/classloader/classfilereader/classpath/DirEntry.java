package com.njuse.jvmfinal.classloader.classfilereader.classpath;

import com.njuse.jvmfinal.util.IOUtil;
import com.njuse.jvmfinal.util.PathUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * format : dir/subdir/.../
 */
public class DirEntry extends Entry{
    public DirEntry(String classpath) {
        super(classpath);
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        File dir = new File(this.classpath);
        String absDirPath = dir.getAbsolutePath();
        String truePath = PathUtil.transform(String.join(this.FILE_SEPARATOR, absDirPath, className));
        File file = new File(truePath);
        return file.isFile() && file.exists() ? IOUtil.readFileByBytes(new FileInputStream(truePath)) : null;
    }
}
