package com.njuse.jvmfinal.classloader.classfilereader;

import com.njuse.jvmfinal.classloader.classfilereader.classpath.*;
import com.njuse.jvmfinal.util.PathUtil;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.IOException;

/**
 * This class is the simulated implementation of Java Classloader.
 */
public class ClassFileReader {
    private static ClassFileReader reader = new ClassFileReader();
    private static final String FILE_SEPARATOR = File.separator;
    private static final String PATH_SEPARATOR = File.pathSeparator;
    private ClassFileReader() {
    }

    public static ClassFileReader getInstance() {
        return reader;
    }

    private static Entry bootClasspath = null;//bootstrap class entry
    private static Entry extClasspath = null;//extension class entry
    private static Entry userClasspath = null;//user class entry

    public static void setBootClasspath(String classpath) {
        bootClasspath = chooseEntryType(classpath);
    }

    public static void setExtClasspath(String classpath) {
        extClasspath = chooseEntryType(classpath);
    }

    public static void setUserClasspath(String classpath) {
        userClasspath = chooseEntryType(classpath);
    }

    /**
     * select Entry by type of classpath
     */
    public static Entry chooseEntryType(String classpath) {
        if (classpath.contains(PATH_SEPARATOR)) {
            return new CompositeEntry(classpath);
        }
        if (classpath.endsWith("*")) {
            return new WildEntry(classpath);
        }
        if (classpath.endsWith(".jar") || classpath.endsWith(".JAR")
                || classpath.endsWith(".zip") || classpath.endsWith(".ZIP")) {
            return new ArchivedEntry(classpath);
        }
        return new DirEntry(classpath);
    }

    /**
     * @param className class to be read
     * @param privilege privilege of relevant class
     * @return content of class file and the privilege of loaded class
     */
    public Pair<byte[], Integer> readClassFile(String className, EntryType privilege) throws IOException, ClassNotFoundException {
        String realClassName = className + ".class";
        realClassName = PathUtil.transform(realClassName);
        //todo
        /**
         * Add some codes here.
         *
         * You can pass realClassName to readClass()
         *
         * Read class file in privilege order
         * USER_ENTRY has highest privileges and Boot_Entry has lowest privileges.
         * If there is no relevant class loaded before, use default privilege.
         * Default privilege is USER_ENTRY
         *
         * Return the result once you read it.
         */
        byte[] data;
        if (privilege==null) privilege=new EntryType(EntryType.USER_ENTRY);
        try {
            if (privilege.getValue() >= EntryType.BOOT_ENTRY) {
                data=bootClasspath.readClass(realClassName);
                if (data!=null)
                return Pair.of(data, EntryType.BOOT_ENTRY);
            }
        }catch (Exception e){}
        try {
            if (privilege.getValue() >= EntryType.EXT_ENTRY) {
                data=extClasspath.readClass(realClassName);
                if (data!=null)
                return Pair.of(data, EntryType.EXT_ENTRY);
            }
        }catch (Exception e){}
        try {
            if (privilege.getValue() >= EntryType.USER_ENTRY) {
                data=userClasspath.readClass(realClassName);
                if (data!=null)
                return Pair.of(data, EntryType.USER_ENTRY);
            }
        }catch (Exception e){}
        throw new ClassNotFoundException();
    }
}
