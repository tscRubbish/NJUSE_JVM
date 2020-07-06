package com.njuse.jvmfinal.instructions.store;

import com.njuse.jvmfinal.instructions.base.Index8Instruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.Vars;

public class FSTORE extends Index8Instruction {
    public void execute(StackFrame frame) {
        OperandStack stack=frame.getOperandStack();
        Vars vars=frame.getLocalVars();
        float val = stack.popFloat();
        vars.setFloat(this.index, val);
    }
}
