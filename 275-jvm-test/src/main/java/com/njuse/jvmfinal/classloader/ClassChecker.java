package com.njuse.jvmfinal.classloader;

import com.njuse.jvmfinal.classloader.classfileparser.ClassFile;
import com.njuse.jvmfinal.classloader.classfileparser.constantpool.info.ConstantPoolInfo;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.Method;

import java.util.ArrayList;
import java.util.Collection;

public class ClassChecker {
    static void checkClassFile(ClassFile classFile){
        if (classFile.getMagic() != 0xCAFEBABE) {
            throw new UnsupportedOperationException(
                    "Wrong magic number! Expect 0xCAFEBABE but actual is " + Integer.toHexString(classFile.getMagic()));
        }
        if (classFile.getMinorVersion()<0||classFile.getMajorVersion()>57){
            throw new UnsupportedOperationException(
                    "Wrong version number! ");
        }
        for (ConstantPoolInfo cpi:classFile.getConstantPool().getInfos()){
            if (!cpi.checkFlag(cpi.getTag()))
                throw new UnsupportedOperationException("Unsupported constant pool tag" );
        }
    }
    static void checkData(JClass clazz){
        if (!clazz.getSuperClassName().equals("")&&clazz.getSuperClass()==null) throw new RuntimeException("No SuperClass");
        if (!clazz.getSuperClassName().equals("")&&clazz.getSuperClass().isFinal()) throw new RuntimeException("SuperClass is Final");
        if (!clazz.isAbstract()&&!clazz.getSuperClassName().equals("")){
            JClass jClass=clazz.getSuperClass();
            if (jClass.getName().equals("")) return;
            ArrayList<Method> methods=new ArrayList<Method>();
            for (Method m:clazz.getMethods()) methods.add(m);
            for (Method m:jClass.getMethods()){
                if (m.isAbstract()&&!methods.contains(m)) throw new RuntimeException("Don't achieve abstract method");
                if (m.isFinal()&&methods.contains(m)) throw new RuntimeException("achieve final method");
            }
            for (JClass intface:clazz.getInterfaces()){
                for (Method m:intface.getMethods()){
                    if (!methods.contains(m)) throw new RuntimeException("Don't achieve interface method");
                }
            }
        }
    }
    static void checkByte(JClass clazz){

    }
    static void checkReference(JClass clazz){

    }
}
