package com.njuse.jvmfinal.instructions.constant;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class FCONST_N extends NoOperandsInstruction {
    private float val;
    private static float[] valid = new float[]{0.0F, 1.0F, 2.0F};

    public FCONST_N(float val) {
        for (float d:valid)
            if (d==val) {
                this.val=val;
                return;
            }
        throw new IllegalArgumentException();
    }

    public void execute(StackFrame frame) {
        frame.getOperandStack().pushFloat(this.val);
    }

    public String toString() {
        String suffix = (int)this.val + "";
        String simpleName = this.getClass().getSimpleName();
        return simpleName + suffix;
    }
}
