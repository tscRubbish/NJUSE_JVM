package com.njuse.jvmfinal.instructions.conversion;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class F2D extends NoOperandsInstruction {
    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        double val = (double) stack.popFloat();
        stack.pushDouble(val);
    }
}