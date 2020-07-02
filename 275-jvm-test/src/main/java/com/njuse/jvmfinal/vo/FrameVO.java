package com.njuse.jvmfinal.vo;

import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.Vars;
import com.njuse.jvmfinal.runtime.struct.ArrayObject;
import com.njuse.jvmfinal.runtime.struct.NonArrayObject;
import com.njuse.jvmfinal.runtime.struct.Slot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FrameVO {
    private ArrayList<String> operandStack;
    private ArrayList<String> localVars;
    private int nextPC;
    private ArrayList<String> code;
    private String methodName;
    private boolean fresh;

    public FrameVO(OperandStack stack, Vars localVars, int nextPC, List<String> code, String methodName, boolean fresh) {
        this.nextPC = nextPC;
        this.code = new ArrayList(code);
        this.operandStack = this.printVars(stack.getSlots());
        this.localVars = this.printVars(localVars.getVarSlots());
        this.methodName = methodName;
        this.fresh = fresh;
    }

    private ArrayList<String> printVars(Slot[] vars) {
        ArrayList<String> ret = new ArrayList();
        Arrays.stream(vars).forEach((s) -> {
            assert s != null;

            String info = null;
            if (s.getValue() != null) {
                info = "value = " + String.format("0x%08X", s.getValue());
            } else if (s.getObject() != null) {
                if (s.getObject() instanceof NonArrayObject) {
                    info = "Object ref to " + s.getObject().getClazz().getName();
                } else if (s.getObject() instanceof ArrayObject) {
                    info = "Object ref to " + ((ArrayObject)s.getObject()).getType();
                }
            } else {
                info = "Empty";
            }

            ret.add(info);
        });
        return ret;
    }
}
