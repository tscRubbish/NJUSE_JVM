package com.njuse.jvmfinal.instructions.invoke;


import com.njuse.jvmfinal.instructions.base.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.InitState;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.MethodRef;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.Vars;
import com.njuse.jvmfinal.runtime.struct.Slot;

public class INVOKE_STATIC extends Index16Instruction {

    /**
     * TODO 实现这条指令，注意其中的非标准部分：
     * 1. TestUtil.equalInt(int a, int b): 如果a和b相等，则跳过这个方法，
     * 否则抛出`RuntimeException`, 其中，这个异常的message为
     * ：${第一个参数的值}!=${第二个参数的值}
     * 例如，TestUtil.equalInt(1, 2)应该抛出
     * RuntimeException("1!=2")
     *
     * 2. TestUtil.fail(): 抛出`RuntimeException`
     *
     * 3. TestUtil.equalFloat(float a, float b): 如果a和b相等，则跳过这个方法，
     * 否则抛出`RuntimeException`. 对于异常的message不作要求
     *
     */
    @Override
    public void execute(StackFrame frame) {
        JClass currentClz = frame.getMethod().getClazz();
        Constant methodRef = currentClz.getRuntimeConstantPool().getConstant(super.index);
        Method toInvoke = ((MethodRef) methodRef).resolveMethodRef();
        JClass toClazz=toInvoke.getClazz();

        if (((MethodRef)methodRef).getClassName().contains("TestUtil")) {
            if (toInvoke.getName().contains("equalInt")) {
                int v1 = frame.getOperandStack().popInt();
                int v2 = frame.getOperandStack().popInt();
                //System.out.println("v1: "+v1+" v2: "+v2);
                if (v1 != v2) {
                    throw new RuntimeException(v2+"!="+v1);
                }

                frame.getOperandStack().pushInt(v2);
                frame.getOperandStack().pushInt(v1);
                //frame.getOperandStack().pushInt(1);
            } else if (toInvoke.getName().contains("equalFloat")) {
                float v1 = frame.getOperandStack().popFloat();
                float v2 = frame.getOperandStack().popFloat();
                //System.out.println("v1: "+v1+" v2: "+v2);
                if ((double)(v1 - v2) > 1.0E-4D || (double)(v1 - v2) < -1.0E-4D) {
                    throw new RuntimeException(v2+"!="+v1);
                }

                frame.getOperandStack().pushFloat(v2);
                frame.getOperandStack().pushFloat(v1);
                //frame.getOperandStack().pushInt(1);
            } else if (toInvoke.getName().equals("fail")) {
                throw new RuntimeException();
            }else if (toInvoke.getName().equals("reach")){
                int v=frame.getOperandStack().popInt();
                System.out.print(v);
                frame.getOperandStack().pushInt(v);
            }
        }
        if (toClazz.getInitState()== InitState.PREPARED){
            frame.setNextPC(frame.getNextPC()-3);
            toClazz.initClass(frame.getThread(),toClazz);
            return;
        }
        Slot[] args = copyArguments(frame, toInvoke);

        StackFrame newFrame = new StackFrame(frame.getThread(), toInvoke,
                toInvoke.getMaxStack(), toInvoke.getMaxLocal());
        Vars localVars = newFrame.getLocalVars();

        int argc = toInvoke.getArgc();
        for (int i = 1; i < args.length + 1; i++) {
            localVars.setSlot(i, args[argc - i]);
        }

        frame.getThread().pushFrame(newFrame);
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

    private Slot[] copyArguments(StackFrame frame, Method method) {
        int argc = method.getArgc();
        Slot[] argv = new Slot[argc];
        for (int i = 0; i < argc; i++) {
            argv[i] = frame.getOperandStack().popSlot();
        }
        return argv;
    }
}
