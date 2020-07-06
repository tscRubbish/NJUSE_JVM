package com.njuse.jvmfinal.instructions.references;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.ArrayObject;

public class ARRAYLENGTH extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        ArrayObject arr=(ArrayObject) frame.getOperandStack().popObjectRef();
        frame.getOperandStack().pushInt(arr.getLen());
    }
}
