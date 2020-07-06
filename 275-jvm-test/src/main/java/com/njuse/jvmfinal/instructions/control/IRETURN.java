package com.njuse.jvmfinal.instructions.control;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.JThread;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class IRETURN extends NoOperandsInstruction {

    /**
     * TODO： 实现这条指令
     */
    @Override
    public void execute(StackFrame frame) {
        JThread thread = frame.getThread();
        OperandStack operandStack=frame.getOperandStack();
        int value = operandStack.popInt();
        thread.popFrame();
        thread.getTopFrame().getOperandStack().pushInt(value);
    }
}
