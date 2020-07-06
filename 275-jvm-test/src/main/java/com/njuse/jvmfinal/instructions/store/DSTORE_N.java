package com.njuse.jvmfinal.instructions.store;

import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.Vars;

public class DSTORE_N extends STORE_N {
    public DSTORE_N(int index) {
        checkIndex(index);
        this.index = index;
    }

    public void execute(StackFrame frame) {
        OperandStack stack=frame.getOperandStack();
        Vars vars=frame.getLocalVars();
        double val = stack.popDouble();
        vars.setDouble(this.index, val);
    }
}
