package com.njuse.jvmfinal.instructions.invoke;


import com.njuse.jvmfinal.instructions.base.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.MethodRef;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.Vars;
import com.njuse.jvmfinal.runtime.struct.JObject;
import com.njuse.jvmfinal.runtime.struct.Slot;

public class INVOKE_VIRTUAL extends Index16Instruction {
    @Override
    public void execute(StackFrame frame) {
        JClass currentClz = frame.getMethod().getClazz();
        Constant methodRef = currentClz.getRuntimeConstantPool().getConstant(super.index);
        assert methodRef instanceof MethodRef;
        Method method = ((MethodRef) methodRef).resolveMethodRef();

        //copy arguments
        int argc = method.getArgc();
        Slot[] argv = new Slot[argc];
        for (int i = 0; i < argc; i++) {
            argv[i] = frame.getOperandStack().popSlot();
        }
        //lookup virtual function
        JObject objectRef = frame.getOperandStack().popObjectRef();
        JClass clazz = objectRef.getClazz();
        Method toInvoke = ((MethodRef) methodRef).resolveMethodRef(clazz);
        //System.out.println(clazz.getName()+" "+toInvoke.getName());
        StackFrame newFrame = prepareNewFrame(frame, argc, argv, objectRef, toInvoke);

        frame.getThread().pushFrame(newFrame);

        checkNative(method,frame);
    }
    private void checkNative(Method toInvoke,StackFrame frame){
        if (toInvoke.isNative()) {
            if (toInvoke.getName().equals("registerNatives")) {
                frame.getThread().popFrame();
            } else {
                System.out.println("Native method:"
                        + toInvoke.getClazz().getName()
                        + toInvoke.name
                        + toInvoke.descriptor);
                frame.getThread().popFrame();
            }
        }
    }
    private StackFrame prepareNewFrame(StackFrame frame, int argc, Slot[] argv, JObject objectRef, Method toInvoke) {
        StackFrame newFrame = new StackFrame(frame.getThread(), toInvoke,
                toInvoke.getMaxStack(), toInvoke.getMaxLocal() + 1);
        Vars localVars = newFrame.getLocalVars();
        Slot thisSlot = new Slot();
        thisSlot.setObject(objectRef);
        localVars.setSlot(0, thisSlot);
        for (int i = 1; i < argc + 1; i++) {
            localVars.setSlot(i, argv[argc - i]);
        }
        return newFrame;
    }
}
