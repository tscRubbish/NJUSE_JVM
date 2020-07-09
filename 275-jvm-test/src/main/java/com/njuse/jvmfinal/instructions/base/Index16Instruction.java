package com.njuse.jvmfinal.instructions.base;

import java.nio.ByteBuffer;

public abstract class Index16Instruction extends Instruction {
    public int index; //type of index is unsigned short

    public void fetchOperands(ByteBuffer reader) {
        index = (int) reader.getShort() & 0xFFFF;
        if (index<0) throw new RuntimeException("Wrong index");
    }

    public String toString() {
        return this.getClass().getSimpleName() + " index: " + (index & 0xFFFF);
    }
}
