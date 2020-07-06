package com.njuse.jvmfinal.instructions.math.algorithm;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class FADD extends NoOperandsInstruction {
    public void execute(StackFrame frame) {
        float val2 = frame.getOperandStack().popFloat();
        float val1 = frame.getOperandStack().popFloat();
        frame.getOperandStack().pushFloat(val1+val2);
    }
}
