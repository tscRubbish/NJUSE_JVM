package com.njuse.jvmfinal.instructions.control;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.JThread;
import com.njuse.jvmfinal.runtime.StackFrame;

public class RETURN extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        JThread thread = frame.getThread();
        thread.popFrame();
        //if (thread.getTopFrame()!=null)
        //System.out.println("    "+thread.getTopFrame().getMethod().getName());
    }
}
