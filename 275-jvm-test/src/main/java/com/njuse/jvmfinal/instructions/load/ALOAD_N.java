package com.njuse.jvmfinal.instructions.load;

import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.JObject;

public class ALOAD_N extends LOAD_N {
    public ALOAD_N(int index) {
        if (index >= valid[0] && index <= valid[valid.length - 1])
            this.index = index;
    }

    public void execute(StackFrame frame) {
        frame.getOperandStack().pushObjectRef(frame.getLocalVars().getObjectRef(index));
    }
}
