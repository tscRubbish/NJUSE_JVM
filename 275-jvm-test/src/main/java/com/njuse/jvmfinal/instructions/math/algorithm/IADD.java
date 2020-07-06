package com.njuse.jvmfinal.instructions.math.algorithm;


import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class IADD extends NoOperandsInstruction {

    /**
     * TODO：实现这条指令
     * 这是一个已完成的例子
     */
    @Override
    public void execute(StackFrame frame) {
        //读取操作数栈
        //读取第二个操作数
        //（注意"栈"这个数据结构是后进先出的，所以第二个操作数先被读取）
        int val2 = frame.getOperandStack().popInt();
        //读取第一个操作数
        int val1 = frame.getOperandStack().popInt();
        //计算结果
        //将结果放入操作数栈
        frame.getOperandStack().pushInt(val1+val2);
    }
}
