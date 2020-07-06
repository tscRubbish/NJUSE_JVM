package com.njuse.jvmfinal.instructions.control;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.JThread;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class DRETURN extends NoOperandsInstruction {
    public void execute(StackFrame frame) {
        JThread thread = frame.getThread();
        OperandStack operandStack=frame.getOperandStack();
        double value = operandStack.popDouble();
        thread.popFrame();
        thread.getTopFrame().getOperandStack().pushDouble(value);
    }
}
