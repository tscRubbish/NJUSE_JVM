package com.njuse.jvmfinal.instructions.load;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.array.RefArrayObject;

public class AALOAD extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        int index=frame.getOperandStack().popInt();
        RefArrayObject arr=(RefArrayObject) frame.getOperandStack().popObjectRef();
        if (index>=0&&index<arr.getLen()){
            frame.getOperandStack().pushObjectRef(arr.getArray()[index]);
        }
        throw new ArrayIndexOutOfBoundsException();
    }
}
