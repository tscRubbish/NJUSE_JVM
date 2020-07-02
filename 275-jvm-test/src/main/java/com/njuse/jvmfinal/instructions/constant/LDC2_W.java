package com.njuse.jvmfinal.instructions.constant;

import com.njuse.jvmfinal.instructions.base.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.DoubleWrapper;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.LongWrapper;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class LDC2_W extends Index16Instruction {
    public LDC2_W() {
    }

    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        Constant constant = frame.getMethod().getClazz().getRuntimeConstantPool().getConstant(this.index);
        if (constant instanceof LongWrapper) {
            stack.pushLong(((LongWrapper)constant).getValue());
        } else {
            if (!(constant instanceof DoubleWrapper)) {
                throw new ClassFormatError();
            }

            stack.pushDouble(((DoubleWrapper)constant).getValue());
        }

    }
}