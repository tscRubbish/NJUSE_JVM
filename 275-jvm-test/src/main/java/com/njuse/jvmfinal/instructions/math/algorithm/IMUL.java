package com.njuse.jvmfinal.instructions.math.algorithm;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class IMUL extends NoOperandsInstruction {

    /**
     * TODO：实现这条指令
     */
    @Override
    public void execute(StackFrame frame) {
        int d2=frame.getOperandStack().popInt();
        int d1=frame.getOperandStack().popInt();
        frame.getOperandStack().pushInt(d1*d2);
    }
}
