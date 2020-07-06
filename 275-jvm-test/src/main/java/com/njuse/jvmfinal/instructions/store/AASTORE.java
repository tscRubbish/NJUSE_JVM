package com.njuse.jvmfinal.instructions.store;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.ArrayObject;
import com.njuse.jvmfinal.runtime.struct.JObject;
import com.njuse.jvmfinal.runtime.struct.array.RefArrayObject;

public class AASTORE extends NoOperandsInstruction {
    @Override
    public void execute(StackFrame frame) {
        JObject value=frame.getOperandStack().popObjectRef();
        int index=frame.getOperandStack().popInt();
        RefArrayObject arr=(RefArrayObject)frame.getOperandStack().popObjectRef();
        if (index<arr.getLen()&&index>=0) {
            arr.getArray()[index]=value;
        }
        throw new ArrayIndexOutOfBoundsException();
    }
}
