package com.njuse.jvmfinal.classloader.classfilereader.classpath;

import com.njuse.jvmfinal.util.IOUtil;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * format : dir/subdir/target.jar
 */
public class ArchivedEntry extends Entry{
    public ArchivedEntry(String classpath) {
        super(classpath);
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        try{
            //System.out.println(classpath);
            //System.out.println(className);
            ZipFile zipFile=new ZipFile(classpath);
            for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements();){
                ZipEntry entry=e.nextElement();
                //System.out.println("文件名:"+entry.getName()+", 内容如下:");
                if (IOUtil.transform(entry.getName()).equals(IOUtil.transform(className))) return IOUtil.readFileByBytes(zipFile.getInputStream(entry));
            }
            throw new IOException();
        }catch (Exception e){
            throw new IOException();
        }
    }
}