package com.njuse.jvmfinal;

import com.njuse.jvmfinal.classloader.ClassLoader;
import com.njuse.jvmfinal.classloader.classfilereader.ClassFileReader;
import com.njuse.jvmfinal.classloader.classfilereader.classpath.Entry;
import com.njuse.jvmfinal.classloader.classfilereader.classpath.EntryType;
import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.runtime.JThread;
import com.njuse.jvmfinal.runtime.StackFrame;

import java.io.File;

public class Starter {

    public static void main(String[] args) {

    }

    /**
     * ⚠️警告：不要改动这个方法签名，这是和测试用例的唯一接口
     */
    public static void runTest(String mainClassName, String cp) throws RuntimeException {
        ClassLoader classLoader = ClassLoader.getInstance();
        ClassFileReader.setBootClasspath(cp);
        ClassFileReader.setUserClasspath(cp);
        ClassFileReader.setExtClasspath(cp);
        try {
            JClass clazz = classLoader.loadClass(mainClassName.replace(".", "/"), (EntryType) null);
            Method mainMethod = clazz.getMainMethod();
            JThread jThread = new JThread();
            StackFrame stackFrame = new StackFrame(jThread, mainMethod, mainMethod.getMaxStack(), mainMethod.getMaxLocal());
            jThread.pushFrame(stackFrame);
            Interpreter.interpret(jThread);
        } catch (ClassNotFoundException cfe) {
            throw new RuntimeException();
        } catch (RuntimeException re) {
            throw new RuntimeException();
        }
    }
}
