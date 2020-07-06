package com.njuse.jvmfinal.instructions.store;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.array.IntArrayObject;

public class IASTORE extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame){
        int value=frame.getOperandStack().popInt();
        int index=frame.getOperandStack().popInt();
        IntArrayObject iarr=(IntArrayObject) frame.getOperandStack().popObjectRef();
        if (index>=0&&index<iarr.getLen()){
            iarr.getArray()[index]=value;
        }
        throw new ArrayIndexOutOfBoundsException();
    }
}
