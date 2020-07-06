package com.njuse.jvmfinal.instructions.load;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.array.CharArrayObject;
import com.njuse.jvmfinal.runtime.struct.array.IntArrayObject;


public class CALOAD extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame){
        int index=frame.getOperandStack().popInt();
        CharArrayObject arr=(CharArrayObject) frame.getOperandStack().popObjectRef();
        if (index>=0&&index<arr.getLen()){
            frame.getOperandStack().pushInt(arr.getArray()[index]);
        }
        else throw new ArrayIndexOutOfBoundsException();
    }
}
