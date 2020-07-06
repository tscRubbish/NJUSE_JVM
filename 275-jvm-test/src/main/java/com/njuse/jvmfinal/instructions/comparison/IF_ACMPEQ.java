package com.njuse.jvmfinal.instructions.comparison;

import com.njuse.jvmfinal.runtime.struct.JObject;

public class IF_ACMPEQ extends IF_ACMPCOND {
    protected boolean condition(JObject v1, JObject v2) {
        return v1.equals(v2);
    }
}
