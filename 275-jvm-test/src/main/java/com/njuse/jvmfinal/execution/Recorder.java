package com.njuse.jvmfinal.execution;

import com.njuse.jvmfinal.instructions.base.Instruction;
import com.njuse.jvmfinal.memory.JHeap;
import com.njuse.jvmfinal.memory.MethodArea;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.runtime.JThread;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.Vars;
import com.njuse.jvmfinal.runtime.struct.JObject;
import com.njuse.jvmfinal.vo.*;

import java.nio.ByteBuffer;
import java.util.*;

public class Recorder {
    private static ByteBuffer codeReader;


    public static StateVO recodeState(StackFrame ori, StackFrame next, JThread thread) {
        Set<JObject> objects = JHeap.getInstance().getObjects();
        Collection<JClass> methodArea = MethodArea.getClassMap().values();
        MemoryVO memoryVO = generateMemoryVO(objects, methodArea);
        FrameVO oriVO = generateFrameVO(ori, false);
        ThreadVO threadVO = generateThreadVO(thread);
        String nextInstruction;
        StateVO state;
        if (ori == next) {
            nextInstruction = getNextInstruction(ori);
            state = new StateVO(oriVO, (FrameVO)null, memoryVO, nextInstruction, threadVO, false);
        } else {
            nextInstruction = getNextInstruction(next);
            FrameVO nextVO = generateFrameVO(next, true);
            state = new StateVO(oriVO, nextVO, memoryVO, nextInstruction, threadVO, true);
        }

        makeOutdated(thread);
        return state;
    }

    public static StateVO initialState(StackFrame main, JThread thread) {
        Set<JObject> objects = JHeap.getInstance().getObjects();
        Collection<JClass> methodArea = MethodArea.getClassMap().values();
        MemoryVO memoryVO = generateMemoryVO(objects, methodArea);
        FrameVO mainVO = generateFrameVO(main, false);
        ThreadVO threadVO = generateThreadVO(thread);
        String nextInstruction = getNextInstruction(main);
        makeOutdated(thread);
        return new StateVO((FrameVO)null, mainVO, memoryVO, nextInstruction, threadVO, false);
    }

    private static MemoryVO generateMemoryVO(Set<JObject> heapObjects, Collection<JClass> methodArea) {
        MemoryVO ret = new MemoryVO();
        List<ObjectVO> objects = ret.getObjects();
        List<ClassVO> classes = ret.getClasses();

        assert heapObjects != null;

        heapObjects.forEach((obj) -> {
            objects.add(new ObjectVO(obj, (Boolean)JHeap.getObjectState().get(obj.getId())));
        });
        methodArea.forEach((clazz) -> {
            classes.add(new ClassVO(clazz));
        });
        return ret;
    }

    public static FrameVO generateFrameVO(StackFrame frame, boolean fresh) {
        Method method = frame.getMethod();
        if (!method.isParsed()) {
            method.parseCode();
        }

        assert method.isParsed();

        OperandStack stack = frame.getOperandStack();
        Vars localVars = frame.getLocalVars();
        int nextPC = frame.getNextPC();
        ArrayList<String> instList = method.getInstList();
        String methodName = method.getClazz().getName() + " : " + method.getName();
        return new FrameVO(stack, localVars, nextPC, instList, methodName, fresh);
    }

    public static ThreadVO generateThreadVO(JThread thread) {
        return new ThreadVO(thread);
    }

    private static void makeOutdated(JThread thread) {
        JHeap.getObjectState().keySet().forEach((id) -> {
            JHeap.getObjectState().put(id, false);
        });
        MethodArea.getClassState().keySet().forEach((name) -> {
            MethodArea.getClassState().put(name, false);
        });
        Stack<Boolean> frameState = thread.getStack().getFrameState();

        for(int i = 0; i < frameState.size(); ++i) {
            frameState.set(i, false);
        }

    }

    private static String getNextInstruction(StackFrame next) {
        byte[] code = next.getMethod().getCode();
        codeReader = ByteBuffer.wrap(code);
        int nextPC = next.getNextPC();
        codeReader.position(nextPC);
        int opcode = codeReader.get() & 255;
        Instruction instruction = Decoder.decode(opcode);
        instruction.fetchOperands(codeReader);
        return nextPC + " " + instruction.toString();
    }
}
