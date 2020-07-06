package com.njuse.jvmfinal.instructions.math.algorithm;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class INEG extends NoOperandsInstruction {
    public void execute(StackFrame frame) {
        int val = frame.getOperandStack().popInt();
        frame.getOperandStack().pushInt(-val);
    }
}
