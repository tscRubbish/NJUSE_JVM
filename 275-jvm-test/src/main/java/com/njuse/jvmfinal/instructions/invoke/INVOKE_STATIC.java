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
        //System.out.println(frame.getMethod().getName()+"  "+toInvoke.getName());
        if (toClazz.getInitState()== InitState.PREPARED){
            frame.setNextPC(frame.getNextPC()-3);
            toClazz.initClass(frame.getThread(),toClazz);
            return;
        }
        checkTestUtil(methodRef,toInvoke,frame,currentClz);

        StackFrame newFrame = new StackFrame(frame.getThread(), toInvoke,
                toInvoke.getMaxStack(), toInvoke.getMaxLocal()+1);//注意本地变量表大小+1
        Vars localVars = newFrame.getLocalVars();
        //System.out.println(toInvoke.getMaxLocal()+1+"  "+args.length);
        int argc = toInvoke.getArgc();
        for (int i = argc-1; i >=0; i--) {
            localVars.setSlot(i, frame.getOperandStack().popSlot());//注意：这里的坐标从0开始，而其他的invoke操作因为要在0位置加入对象引用所以从1开始
        }

        frame.getThread().pushFrame(newFrame);
        checkNative(toInvoke,frame);
    }
    private void checkNative(Method toInvoke,StackFrame frame){
        if (toInvoke.isNative()) {
            if (toInvoke.getName().equals("registerNatives")) {
                frame.getThread().popFrame();
            } else {
                //System.out.println("Native method:" + toInvoke.getClazz().getName() + toInvoke.name + toInvoke.descriptor);
                frame.getThread().popFrame();
            }
        }
    }
    private void checkTestUtil(Constant methodRef,Method toInvoke,StackFrame frame,JClass currentClz) {
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
                //System.out.println(currentClz.getName());
                //System.out.println(toInvoke.getName());
                //System.out.println(frame.getMethod().getName());
                System.out.println(v);
                frame.getOperandStack().pushInt(v);
            }
        }
    }
}
