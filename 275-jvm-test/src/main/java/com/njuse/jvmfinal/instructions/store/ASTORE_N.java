package com.njuse.jvmfinal.instructions.store;

import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.Vars;
import com.njuse.jvmfinal.runtime.struct.JObject;

public class ASTORE_N extends STORE_N {
    public ASTORE_N(int index) {
        checkIndex(index);
        this.index = index;
    }

    public void execute(StackFrame frame) {
        OperandStack stack=frame.getOperandStack();
        Vars vars=frame.getLocalVars();
        JObject ref = stack.popObjectRef();
        vars.setObjectRef(this.index, ref);
    }
}