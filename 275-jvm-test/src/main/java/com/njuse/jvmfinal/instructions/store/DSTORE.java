package com.njuse.jvmfinal.instructions.store;

import com.njuse.jvmfinal.instructions.base.Index8Instruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.Vars;

public class DSTORE extends Index8Instruction {
    public void execute(StackFrame frame) {
        OperandStack stack=frame.getOperandStack();
        Vars vars=frame.getLocalVars();
        double val = stack.popDouble();
        vars.setDouble(this.index, val);
    }
}
