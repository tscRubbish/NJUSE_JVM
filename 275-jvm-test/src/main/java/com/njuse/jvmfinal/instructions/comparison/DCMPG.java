package com.njuse.jvmfinal.instructions.comparison;


import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class DCMPG extends NoOperandsInstruction {

    /**
     * TODO：实现这条指令
     */
    @Override
    public void execute(StackFrame frame) {
        double d2=frame.getOperandStack().popDouble();//栈先进后出，第二个参数先被读取
        double d1=frame.getOperandStack().popDouble();
        if (!Double.isNaN(d1)&&!Double.isNaN(d2)){
            frame.getOperandStack().pushInt(Double.compare(d1,d2));
        }else{
            frame.getOperandStack().pushInt(1);
        }
    }
}
