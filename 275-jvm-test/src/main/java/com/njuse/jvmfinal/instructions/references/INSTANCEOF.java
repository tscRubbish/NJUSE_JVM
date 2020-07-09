package com.njuse.jvmfinal.instructions.references;

import com.njuse.jvmfinal.instructions.base.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.ClassRef;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.JObject;

public class INSTANCEOF extends Index16Instruction {
    public INSTANCEOF() {
    }

    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        JObject ref = stack.popObjectRef();
        if (ref.isNull()) {
            stack.pushInt(0);
        } else {
            RuntimeConstantPool runtimeConstantPool = frame.getMethod().getClazz().getRuntimeConstantPool();
            ClassRef classRef = (ClassRef)runtimeConstantPool.getConstant(this.index);

            try {
                JClass clazz = classRef.getResolvedClass();
                if (ref.isInstanceOf(clazz)) {
                    stack.pushInt(1);
                } else {
                    stack.pushInt(0);
                }
            } catch (ClassNotFoundException var7) {
                var7.printStackTrace();
            }

        }
    }
}
