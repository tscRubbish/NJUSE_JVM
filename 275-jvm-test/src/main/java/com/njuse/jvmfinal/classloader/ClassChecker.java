package com.njuse.jvmfinal.classloader;

import com.njuse.jvmfinal.classloader.classfileparser.ClassFile;
import com.njuse.jvmfinal.classloader.classfileparser.constantpool.info.ConstantPoolInfo;
import com.njuse.jvmfinal.instructions.base.BranchInstruction;
import com.njuse.jvmfinal.instructions.base.Instruction;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.ClassRef;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;

public class ClassChecker {
    static void checkClassFile(ClassFile classFile){
        if (classFile.getMagic() != 0xCAFEBABE) {
            throw new UnsupportedOperationException(
                    "Wrong magic number! Expect 0xCAFEBABE ");
        }
        if (classFile.getMinorVersion()<0||classFile.getMajorVersion()>57){
            throw new UnsupportedOperationException(
                    "Wrong version number! ");
        }
        for (ConstantPoolInfo cpi:classFile.getConstantPool().getInfos()){
            if (cpi==null) continue;
            if (!cpi.checkFlag(cpi.getTag()))
                throw new UnsupportedOperationException("Unsupported constant pool tag" );
        }
    }
    static void checkData(JClass clazz){
        //验证除了object类以外的类是否有父类
        if (!clazz.getSuperClassName().equals("")&&clazz.getSuperClass()==null&&clazz.getName().equals("java/lang/Object"))
            throw new RuntimeException("No SuperClass");
        //验证父类是否是final
        if (!clazz.getSuperClassName().equals("")&&clazz.getSuperClass().isFinal())
            throw new RuntimeException("SuperClass is Final");
        if (!clazz.isAbstract()&&!clazz.getSuperClassName().equals("")){
            JClass jClass=clazz.getSuperClass();
            if (jClass.getName().equals("")) return;
            ArrayList<Pair<String,String> > methods=new ArrayList<Pair<String,String>>();
            for (Method m:clazz.getMethods()) methods.add(Pair.of(m.getName(),m.getDescriptor()));
            for (Method m:jClass.getMethods()){
                //如果不是抽象类，是否实现了父类的所有抽象方法
                if (m.isAbstract()&&!methods.contains(Pair.of(m.getName(),m.getDescriptor()))) throw new RuntimeException("Don't achieve abstract method");
                //是否覆盖了父类的final方法
                if (m.isFinal()&&methods.contains(Pair.of(m.getName(),m.getDescriptor()))) throw new RuntimeException("achieve final method");
            }
            //System.out.println(clazz.getName()+" "+clazz.getInterfaces().length);
            if (clazz.getInterfaceNames().length==0) return;
            //如果不是抽象类，是否实现了接口的所有方法
            for (JClass intface:clazz.getInterfaces()){
                for (Method m:intface.getMethods()){
                    if (!m.isFinal()&&!methods.contains(Pair.of(m.getName(),m.getDescriptor())))
                        throw new RuntimeException("Don't achieve interface method");
                }
            }
        }
    }
    static void checkByte(JClass clazz){
        for (Method m:clazz.getMethods()){
            //忽略本地方法
            if (m.getName().equals("registerNatives")) continue;
            if (!m.isParsed()) m.parseCode();
            if (m.getInstList()==null) return;
            //检测方法体中的跳转不会跳转到方法体之外
            for (Pair<Instruction,Integer> instruction:m.getInstList()){
                //System.out.println(instruction.getKey().toString());
                if (instruction.getKey() instanceof BranchInstruction){
                    //System.out.println(instruction.getValue()+((BranchInstruction) instruction.getKey()).getOffset()-3>=m.getCode().length);
                    if (instruction.getValue()+((BranchInstruction) instruction.getKey()).getOffset()-3>=m.getCode().length)
                        throw new RuntimeException("PC out of method");
                }
            }
        }
    }
    static void checkReference(JClass clazz) {
        //验证符号引用中的类的可访问性
        RuntimeConstantPool rcp=clazz.getRuntimeConstantPool();
        for (Constant c:rcp.getConstants()){
            if (c instanceof ClassRef) {
                if (((ClassRef) c).clazz==null) continue;
                JClass toclazz = ((ClassRef) c).clazz;
                if (!toclazz.isAccessibleTo(clazz))
                    throw new RuntimeException(((ClassRef) c).className + " is not accessible to " + clazz.getName());
            }
        }
    }
}
