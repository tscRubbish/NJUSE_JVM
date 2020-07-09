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
import java.io.IOException;
import java.io.PrintWriter;

public class Starter {
    static String cp = "src/test/java/";

    public static void main(String[] args) {
        Starter.runTest("cases.light.Test6", cp);
        //Starter.runTest("cases.medium.DarkMediumInstruction2", cp);
    }

    /**
     * ⚠️警告：不要改动这个方法签名，这是和测试用例的唯一接口
     */
    public static void runTest(String mainClassName, String cp) {
        ClassLoader classLoader = ClassLoader.getInstance();
        ClassFileReader.setUserClasspath(cp);
        try {
            JClass clazz = classLoader.loadClass(mainClassName.replace('.','/'), (EntryType) null);
            //System.out.println(mainClassName.replace('.','/'));
            Method mainMethod = clazz.getMainMethod();
            JThread jThread = new JThread();
            StackFrame stackFrame = new StackFrame(jThread, mainMethod, mainMethod.getMaxStack(), mainMethod.getMaxLocal()+1);
            jThread.pushFrame(stackFrame);
            clazz.initClass(jThread,clazz);
            /*for (Method m:clazz.getMethods()){
                //System.out.println(m.getName());
                if (m.getName().equals("<clinit>")){
                    stackFrame = new StackFrame(jThread, m, m.getMaxStack(), m.getMaxLocal()+1);
                    jThread.pushFrame(stackFrame);
                }
            }*/
            try{
                PrintWriter pw=new PrintWriter(new File("src/test/java/"+mainClassName+".txt"));
                Interpreter.interpret(jThread,pw);
                pw.close();
            }catch (IOException e){}
        } catch (ClassNotFoundException cfe) {
            cfe.printStackTrace();
        }
    }
}
