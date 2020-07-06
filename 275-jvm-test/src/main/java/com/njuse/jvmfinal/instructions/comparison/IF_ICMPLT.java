package com.njuse.jvmfinal.instructions.comparison;

public class IF_ICMPLT extends IF_ICMPCOND {
    @Override
    protected boolean condition(int v1, int v2) {
        if (v1<v2)
            return true;
        return false;
    }
}
