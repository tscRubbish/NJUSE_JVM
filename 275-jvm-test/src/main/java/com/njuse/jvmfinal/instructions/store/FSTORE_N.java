package com.njuse.jvmfinal.instructions.store;

import com.njuse.jvmfinal.runtime.StackFrame;

public class FSTORE_N extends STORE_N {
    public FSTORE_N(int index) {
        checkIndex(index);
        this.index = index;
    }

    public void execute(StackFrame frame) {
        float val = frame.getOperandStack().popFloat();
        frame.getLocalVars().setFloat(this.index, val);
    }
}
