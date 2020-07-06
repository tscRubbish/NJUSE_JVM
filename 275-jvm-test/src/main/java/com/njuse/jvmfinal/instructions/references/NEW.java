package com.njuse.jvmfinal.instructions.references;


import com.njuse.jvmfinal.instructions.base.Index16Instruction;
import com.njuse.jvmfinal.memory.JHeap;
import com.njuse.jvmfinal.memory.jclass.InitState;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.ClassRef;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.NonArrayObject;

public class NEW extends Index16Instruction {
    /**
     * TODO 实现这条指令
     * 其中 对应的index已经读取好了
     */
    @Override
    public void execute(StackFrame frame) {
        RuntimeConstantPool rcp=frame.getMethod().getClazz().getRuntimeConstantPool();
        ClassRef cr=(ClassRef) rcp.getConstant(super.index);
        try {
            JClass clazz = cr.getResolvedClass();
            if (clazz.getInitState()== InitState.PREPARED){
                frame.setNextPC(frame.getNextPC()-3);
                clazz.initClass(frame.getThread(),clazz);
                return;
            }
            NonArrayObject ref=clazz.newObject();
            JHeap.getInstance().addObj(ref);
            frame.getOperandStack().pushObjectRef(ref);
        }catch (Exception e){}
    }
}
