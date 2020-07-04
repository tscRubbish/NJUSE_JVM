package com.njuse.jvmfinal.instructions.store;

import com.njuse.jvmfinal.runtime.StackFrame;

public class LSTORE_N extends STORE_N {
    public LSTORE_N(int index) {
        checkIndex(index);
        this.index = index;
    }

    public void execute(StackFrame frame) {
        long val = frame.getOperandStack().popLong();
        frame.getLocalVars().setLong(this.index, val);
    }
}
