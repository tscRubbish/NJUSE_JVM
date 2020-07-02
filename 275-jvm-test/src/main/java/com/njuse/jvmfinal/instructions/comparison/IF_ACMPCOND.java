package com.njuse.jvmfinal.instructions.comparison;

import com.njuse.jvmfinal.instructions.base.BranchInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.JObject;

public abstract class IF_ACMPCOND extends BranchInstruction {
    public IF_ACMPCOND() {
    }

    public void execute(StackFrame frame) {
        JObject value2 = frame.getOperandStack().popObjectRef();
        JObject value1 = frame.getOperandStack().popObjectRef();
        if (this.condition(value1, value2)) {
            int nextPC = frame.getNextPC();
            int branchNext = nextPC - 3 + this.offset;
            frame.setNextPC(branchNext);
        }

    }

    protected abstract boolean condition(JObject var1, JObject var2);
}
