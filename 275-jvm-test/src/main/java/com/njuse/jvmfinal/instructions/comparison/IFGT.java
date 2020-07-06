package com.njuse.jvmfinal.instructions.comparison;

public class IFGT extends IFCOND {
    @Override
    public boolean condition(int value) {
        if (value>0)
            return true;
        return false;
    }
}
