package com.njuse.jvmfinal.instructions.control;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.JThread;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.JObject;

public class ARETURN extends NoOperandsInstruction {

    public void execute(StackFrame frame) {
        JThread thread = frame.getThread();
        JObject ref = frame.getOperandStack().popObjectRef();
        thread.popFrame();
        thread.getTopFrame().getOperandStack().pushObjectRef(ref);
    }
}
