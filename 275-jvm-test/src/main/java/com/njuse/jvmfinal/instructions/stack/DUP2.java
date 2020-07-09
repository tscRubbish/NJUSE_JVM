package com.njuse.jvmfinal.instructions.stack;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.Slot;

public class DUP2 extends NoOperandsInstruction {
    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        Slot slot1 = stack.popSlot();
        Slot slot2 = stack.popSlot();
        stack.pushSlot(slot2.clone());
        stack.pushSlot(slot1.clone());
        stack.pushSlot(slot2.clone());
        stack.pushSlot(slot1.clone());
    }
}
