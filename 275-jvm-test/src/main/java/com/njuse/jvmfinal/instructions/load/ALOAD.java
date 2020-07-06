package com.njuse.jvmfinal.instructions.load;

import com.njuse.jvmfinal.instructions.base.Index8Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.JObject;

public class ALOAD extends Index8Instruction {
    public void execute(StackFrame frame) {
        frame.getOperandStack().pushObjectRef(frame.getLocalVars().getObjectRef(index));
    }
}
