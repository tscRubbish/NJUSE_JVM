package com.njuse.jvmfinal.instructions.store;

import com.njuse.jvmfinal.instructions.base.Index8Instruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.Vars;

public class LSTORE extends Index8Instruction {
    public void execute(StackFrame frame) {
        OperandStack stack=frame.getOperandStack();
        Vars vars=frame.getLocalVars();
        long val = stack.popLong();
        vars.setLong(this.index, val);
    }
}
