package com.njuse.jvmfinal.instructions.load;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.ArrayObject;
import com.njuse.jvmfinal.runtime.struct.array.IntArrayObject;

public class IALOAD extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame){
        int index=frame.getOperandStack().popInt();
        IntArrayObject arr=(IntArrayObject) frame.getOperandStack().popObjectRef();
        if (index>=0&&index<arr.getLen()){
            frame.getOperandStack().pushInt(arr.getArray()[index]);
        }
        throw new ArrayIndexOutOfBoundsException();
    }
}
