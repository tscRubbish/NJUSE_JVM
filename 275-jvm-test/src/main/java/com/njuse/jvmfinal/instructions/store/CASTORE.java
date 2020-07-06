package com.njuse.jvmfinal.instructions.store;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.array.CharArrayObject;
import com.njuse.jvmfinal.runtime.struct.array.IntArrayObject;

public class CASTORE extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame){
        char value=(char)frame.getOperandStack().popInt();
        int index=frame.getOperandStack().popInt();
        CharArrayObject carr=(CharArrayObject) frame.getOperandStack().popObjectRef();
        if (index>=0&&index<carr.getLen()){
            carr.getArray()[index]=value;
        }
        throw new ArrayIndexOutOfBoundsException();
    }
}
