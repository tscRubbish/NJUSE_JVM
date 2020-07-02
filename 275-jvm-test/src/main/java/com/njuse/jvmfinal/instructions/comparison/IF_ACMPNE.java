package com.njuse.jvmfinal.instructions.comparison;

import com.njuse.jvmfinal.runtime.struct.JObject;

public class IF_ACMPNE extends IF_ACMPCOND {
    public IF_ACMPNE() {
    }

    protected boolean condition(JObject v1, JObject v2) {
        return !v1.equals(v2);
    }
}
