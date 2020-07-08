package com.njuse.jvmfinal.instructions.base;

import lombok.Getter;

import java.nio.ByteBuffer;
@Getter
public abstract class BranchInstruction extends Instruction {
    protected short offset;//type of offset is signed short

    public void fetchOperands(ByteBuffer reader) {
        offset = reader.getShort();
    }

    public String toString() {
        return this.getClass().getSimpleName() + " offset: " + offset;
    }
}
