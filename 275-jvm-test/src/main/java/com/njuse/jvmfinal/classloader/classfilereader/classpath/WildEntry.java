package com.njuse.jvmfinal.classloader.classfilereader.classpath;


import com.njuse.jvmfinal.util.IOUtil;

import java.io.File;
import java.io.IOException;

/**
 * format : dir/.../*
 */
public class WildEntry extends Entry{
    public WildEntry(String classpath) {
        super(classpath);
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        if (classpath.endsWith("*")) classpath=classpath.replace("*","");
        //System.out.println(classpath);
        //System.out.println(className);
        File f=new File(IOUtil.transform(classpath));
        try{
            String Path="";
            for (String x:f.list()) Path+=classpath+x+PATH_SEPARATOR;
            CompositeEntry entry=new CompositeEntry(Path);
            return entry.readClass(className);
        }catch (Exception e){}
        throw new IOException();
    }
}
