package com.njuse.jvmfinal.instructions.references;

import com.njuse.jvmfinal.classloader.ClassLoader;
import com.njuse.jvmfinal.instructions.base.Index16Instruction;
import com.njuse.jvmfinal.memory.JHeap;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.ClassRef;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.ArrayObject;

public class ANEWARRAY extends Index16Instruction {
    @Override
    public void execute(StackFrame frame) {
        RuntimeConstantPool rcp=frame.getMethod().getClazz().getRuntimeConstantPool();
        try {
            JClass clazz = ((ClassRef)rcp.getConstant(this.index)).getResolvedClass();
            OperandStack stack = frame.getOperandStack();
            int len = stack.popInt();
            JClass arrClass = ClassLoader.getInstance().loadClass(getClassName(clazz),null);
            ArrayObject ref = arrClass.newArrayObject(len,"Ref");
            JHeap.getInstance().addObj(ref);
            stack.pushObjectRef(ref);
        } catch (ClassNotFoundException var9) {
            var9.printStackTrace();
        }
    }
    String getClassName(JClass clazz){
        String name="L"+clazz.getName()+";";
        return "["+name;
    }
}
