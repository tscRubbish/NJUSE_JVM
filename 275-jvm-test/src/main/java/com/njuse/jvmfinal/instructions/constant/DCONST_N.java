package com.njuse.jvmfinal.instructions.constant;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class DCONST_N extends NoOperandsInstruction {
    private double val;
    private static double[] valid = new double[]{0.0D, 1.0D};

    public DCONST_N(double val) {
        for (double d:valid)
            if (d==val) {
                this.val=val;
                return;
            }
        throw new IllegalArgumentException();
    }

    public void execute(StackFrame frame) {
        frame.getOperandStack().pushDouble(this.val);
    }

    public String toString() {
        String suffix = (int)this.val + "";
        String simpleName = this.getClass().getSimpleName();
        return simpleName + suffix;
    }
}
