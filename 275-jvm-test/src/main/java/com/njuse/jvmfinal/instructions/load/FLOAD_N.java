package com.njuse.jvmfinal.instructions.load;

import com.njuse.jvmfinal.runtime.StackFrame;

public class FLOAD_N extends LOAD_N {
    public FLOAD_N(int index) {
        if (index >= valid[0] && index <= valid[valid.length - 1])
        this.index = index;
    }

    public void execute(StackFrame frame) {
        frame.getOperandStack().pushFloat(frame.getLocalVars().getFloat(index));
    }
}
