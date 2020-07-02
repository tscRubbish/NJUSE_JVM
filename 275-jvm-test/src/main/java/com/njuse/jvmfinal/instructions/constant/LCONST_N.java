package com.njuse.jvmfinal.instructions.constant;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class LCONST_N extends NoOperandsInstruction {
    private long val;
    private static long[] valid = new long[]{0L, 1L};

    public LCONST_N(int val) {
        if ((long)val >= valid[0] && (long)val <= valid[valid.length - 1]) {
            this.val = (long)val;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void execute(StackFrame frame) {
        frame.getOperandStack().pushLong(this.val);
    }

    public String toString() {
        String simpleName = this.getClass().getSimpleName();
        return simpleName.substring(0, simpleName.length() - 1) + this.val;
    }
}
