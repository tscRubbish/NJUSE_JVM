package com.njuse.jvmfinal.instructions.constant;

import com.njuse.jvmfinal.instructions.base.Index16Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class LDC_W extends Index16Instruction {
    public LDC_W() {
    }

    public void execute(StackFrame frame) {
        LDC.loadConstant(frame, this.index);
    }
}
