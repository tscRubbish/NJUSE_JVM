package com.njuse.jvmfinal.instructions.comparison;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class LCMP extends NoOperandsInstruction {
    public LCMP() {
    }

    public void execute(StackFrame frame) {
        long value2 = frame.getOperandStack().popLong();
        long value1 = frame.getOperandStack().popLong();
        frame.getOperandStack().pushInt(Long.compare(value1, value2));
    }
}
