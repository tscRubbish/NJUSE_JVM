package com.njuse.jvmfinal.instructions.load;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;

public abstract class LOAD_N extends NoOperandsInstruction {
    protected int index;
    static int[] valid = {0, 1, 2, 3};

    @Override
    public String toString() {
        String suffix = index + "";
        return this.getClass().getSimpleName().replace("N", suffix);
    }
}
