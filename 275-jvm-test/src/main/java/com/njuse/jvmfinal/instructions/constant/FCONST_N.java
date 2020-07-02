package com.njuse.jvmfinal.instructions.constant;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class FCONST_N extends NoOperandsInstruction {
    private float val;
    private static float[] valid = new float[]{0.0F, 1.0F, 2.0F};

    public FCONST_N(float val) {
        float[] var2 = valid;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            float n = var2[var4];
            if (n == val) {
                this.val = val;
                return;
            }
        }

        throw new IllegalArgumentException();
    }

    public void execute(StackFrame frame) {
        frame.getOperandStack().pushFloat(this.val);
    }

    public String toString() {
        String suffix = (int)this.val + "";
        String simpleName = this.getClass().getSimpleName();
        return simpleName.substring(0, simpleName.length() - 1) + suffix;
    }
}
