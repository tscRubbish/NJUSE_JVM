package com.njuse.jvmfinal.instructions.comparison;

import com.njuse.jvmfinal.instructions.base.BranchInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public abstract class IFCOND extends BranchInstruction {

    /**
     * TODO：实现这条指令
     * 其中，condition 方法是对具体条件的计算，当条件满足时返回true，否则返回false
     */
    @Override
    public void execute(StackFrame frame) {
        int i=frame.getOperandStack().popInt();
        //System.out.println(frame.getMethod().getName()+" "+this.toString()+"  "+i);
        if (condition(i)){
            frame.setNextPC(frame.getNextPC()-3+offset);
        }
    }

    protected abstract boolean condition(int value);

}
