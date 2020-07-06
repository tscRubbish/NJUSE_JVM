package com.njuse.jvmfinal.instructions.comparison;


import com.njuse.jvmfinal.instructions.base.BranchInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public abstract class IF_ICMPCOND extends BranchInstruction {//todo:可以加一个IF_CMPCOND作为ICMP和ACMP的继承父类
    /**
     * TODO：实现这条指令
     * 其中，condition 方法是对具体条件的计算，当条件满足时返回true，否则返回false
     */
    @Override
    public void execute(StackFrame frame) {
        int i2=frame.getOperandStack().popInt();
        int i1=frame.getOperandStack().popInt();
        if (condition(i1,i2)){
            frame.setNextPC(frame.getNextPC()-3+offset);
        }
    }

    protected abstract boolean condition(int v1, int v2);
}
