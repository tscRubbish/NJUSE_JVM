package com.njuse.jvmfinal.instructions.load;

import com.njuse.jvmfinal.runtime.StackFrame;

public class LLOAD_N extends LOAD_N {
    public LLOAD_N(int index) {
        if (index >= valid[0] && index <= valid[valid.length - 1])
        this.index = index;
    }

    public void execute(StackFrame frame) {
        frame.getOperandStack().pushLong(frame.getLocalVars().getLong(index));
    }
}
