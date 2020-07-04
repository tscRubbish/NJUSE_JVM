package com.njuse.jvmfinal.instructions.comparison;

import com.njuse.jvmfinal.instructions.base.BranchInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.JObject;

public class IFNONNULL extends BranchInstruction {
    public void execute(StackFrame frame) {
        JObject ref = frame.getOperandStack().popObjectRef();
        if (!ref.isNull()) {
            int branchPC = frame.getNextPC() - 3 + super.offset;
            frame.setNextPC(branchPC);
        }

    }
}
