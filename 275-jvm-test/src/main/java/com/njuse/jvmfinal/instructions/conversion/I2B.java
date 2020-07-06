package com.njuse.jvmfinal.instructions.conversion;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class I2B extends NoOperandsInstruction {

    /**
     * TODO：（加分项）实现这条指令
     * 这是一条可选测试用例才会涉及的指令
     */
    @Override
    public void execute(StackFrame frame) {
        byte b=(byte)frame.getOperandStack().popInt();;
        frame.getOperandStack().pushInt(b);
    }
}
