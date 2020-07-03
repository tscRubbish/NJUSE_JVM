package com.njuse.jvmfinal.instructions.math.algorithm;

import com.njuse.jvmfinal.instructions.base.Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.Vars;

import java.nio.ByteBuffer;

public class IINC extends Instruction {
    private int index;
    private int num;

    public void execute(StackFrame frame) {
        Vars vars = frame.getLocalVars();
        int val = vars.getInt(this.index);
        vars.setInt(this.index, val + this.num);
    }

    public void fetchOperands(ByteBuffer reader) {
        this.index = reader.get() & 15;
        this.num = reader.get();
    }

    public String toString() {
        return this.getClass().getSimpleName() + " index : " + this.index + "num : " + this.num;
    }
}
