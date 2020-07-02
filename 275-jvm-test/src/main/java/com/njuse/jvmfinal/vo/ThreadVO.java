package com.njuse.jvmfinal.vo;

import com.njuse.jvmfinal.execution.Recorder;
import com.njuse.jvmfinal.runtime.JThread;
import com.njuse.jvmfinal.runtime.StackFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ThreadVO {
    private List<FrameVO> threadStack = new ArrayList();

    public ThreadVO(JThread thread) {
        Stack<StackFrame> stack = thread.getStack().getStack();
        Stack<Boolean> frameState = thread.getStack().getFrameState();

        assert stack.size() == frameState.size();

        for(int i = 0; i < stack.size(); ++i) {
            this.threadStack.add(Recorder.generateFrameVO((StackFrame)stack.get(i), (Boolean)frameState.get(i)));
        }

    }
}
