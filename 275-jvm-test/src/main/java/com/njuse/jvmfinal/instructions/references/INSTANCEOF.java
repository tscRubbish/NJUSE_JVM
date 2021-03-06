package com.njuse.jvmfinal.instructions.references;

import com.njuse.jvmfinal.instructions.base.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.ClassRef;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.JObject;

public class INSTANCEOF extends Index16Instruction {
    @Override
    public void execute(StackFrame frame) {
        JObject ref=frame.getOperandStack().popObjectRef();
        if (ref.isNull()) {
            frame.getOperandStack().pushInt(0);
            return;
        }
        RuntimeConstantPool rcp=frame.getMethod().getClazz().getRuntimeConstantPool();
        try{
            JClass clazz=((ClassRef)rcp.getConstant(index)).getResolvedClass();
            //System.out.println("Super= "+ref.getClazz().getSuperClass().getName());
            //System.out.println(ref.getClazz().getName()+" "+clazz.getName()+" "+clazz.isArray());
            if (clazz.isAssignableFrom(ref.getClazz()))
                frame.getOperandStack().pushInt(1);
            else frame.getOperandStack().pushInt(0);
        }catch (ClassNotFoundException e){e.printStackTrace();}//异常种类注意是ClassNotFountException
    }
}
