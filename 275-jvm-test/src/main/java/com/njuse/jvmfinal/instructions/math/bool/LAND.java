package com.njuse.jvmfinal.instructions.math.bool;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class LAND extends NoOperandsInstruction {
    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        long val2 = stack.popLong();
        long val1 = stack.popLong();
        stack.pushLong(val1&val2);
    }
}
