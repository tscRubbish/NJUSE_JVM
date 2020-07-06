package com.njuse.jvmfinal.instructions.conversion;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class D2F extends NoOperandsInstruction {
    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        float f = (float) stack.popDouble();
        stack.pushFloat(f);
    }
}
