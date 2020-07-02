package com.njuse.jvmfinal.runtime;

import com.njuse.jvmfinal.memory.jclass.Method;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StackFrame {
    private OperandStack operandStack;
    private Vars localVars;
    private JThread thread;
    private int nextPC;
    private Method method;

    public OperandStack getOperandStack() {
        return operandStack;
    }
    public Method getMethod() {
        return method;
    }

    public StackFrame(JThread thread, Method method, int maxStackSize, int maxVarSize) {
        this.thread = thread;
        this.method = method;
        operandStack = new OperandStack(maxStackSize);
        localVars = new Vars(maxVarSize);
    }

}
