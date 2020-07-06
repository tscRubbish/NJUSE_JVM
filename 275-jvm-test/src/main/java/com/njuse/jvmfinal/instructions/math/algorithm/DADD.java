package com.njuse.jvmfinal.instructions.math.algorithm;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class DADD extends NoOperandsInstruction {
    public void execute(StackFrame frame) {
        double val2 = frame.getOperandStack().popDouble();
        double val1 = frame.getOperandStack().popDouble();
        frame.getOperandStack().pushDouble(val1+val2);
    }
}
