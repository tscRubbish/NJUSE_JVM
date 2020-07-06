package com.njuse.jvmfinal.instructions.constant;

import com.njuse.jvmfinal.instructions.base.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.DoubleWrapper;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.FloatWrapper;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.IntWrapper;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.LongWrapper;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class LDC2_W extends Index16Instruction {
    @Override
    public void execute(StackFrame frame) {
        loadConstant(frame, index);
    }

    public static void loadConstant(StackFrame frame, int index) {
        //当前操作数栈
        OperandStack stack = frame.getOperandStack();
        //运行时常量池中对应的元素
        Constant constant = frame.getMethod().getClazz().getRuntimeConstantPool().getConstant(index);
        if (constant instanceof LongWrapper) {
            //这个元素是LongWrapper
            frame.getOperandStack().pushLong(((LongWrapper) constant).getValue());
        }
        else if (constant instanceof DoubleWrapper) {
            //这个元素是DoubleWrapper
            frame.getOperandStack().pushDouble(((DoubleWrapper) constant).getValue());
        }

        else throw new ClassFormatError();

    }
}