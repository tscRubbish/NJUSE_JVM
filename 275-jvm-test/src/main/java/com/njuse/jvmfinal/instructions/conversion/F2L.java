package com.njuse.jvmfinal.instructions.conversion;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class F2L extends NoOperandsInstruction {
    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        long val = (long) stack.popFloat();
        stack.pushLong(val);
    }
}
