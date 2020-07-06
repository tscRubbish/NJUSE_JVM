package com.njuse.jvmfinal.instructions.load;

import com.njuse.jvmfinal.instructions.base.Index8Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class FLOAD extends Index8Instruction {
    public void execute(StackFrame frame) {
        frame.getOperandStack().pushFloat(frame.getLocalVars().getFloat(index));
    }
}
