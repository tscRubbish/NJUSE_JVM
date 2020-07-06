package com.njuse.jvmfinal.instructions.store;

import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.Vars;

public class LSTORE_N extends STORE_N {
    public LSTORE_N(int index) {
        checkIndex(index);
        this.index = index;
    }

    public void execute(StackFrame frame) {
        OperandStack stack=frame.getOperandStack();
        Vars vars=frame.getLocalVars();
        long val = stack.popLong();
        vars.setLong(this.index, val);
    }
}
