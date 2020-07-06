package com.njuse.jvmfinal.instructions.references;

import com.njuse.jvmfinal.classloader.ClassLoader;
import com.njuse.jvmfinal.instructions.base.Index8Instruction;
import com.njuse.jvmfinal.memory.JHeap;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.ArrayObject;
import com.njuse.jvmfinal.runtime.struct.array.ArrayType;

public class NEWARRAY extends Index8Instruction {
    @Override
    public void execute(StackFrame frame) {
        int len=frame.getOperandStack().popInt();
        JClass clazz;
        String type= ArrayType.getName(index);
        ClassLoader classLoader=ClassLoader.getInstance();
        try {
            switch (index) {
                case 4:
                    clazz = classLoader.loadClass("[Z", null);
                    break;
                case 5:
                    clazz = classLoader.loadClass("[C", null);
                    break;
                case 6:
                    clazz = classLoader.loadClass("[F", null);
                    break;
                case 7:
                    clazz = classLoader.loadClass("[D", null);
                    break;
                case 8:
                    clazz = classLoader.loadClass("[B", null);
                    break;
                case 9:
                    clazz = classLoader.loadClass("[S", null);
                    break;
                case 10:
                    clazz = classLoader.loadClass("[I", null);
                    break;
                case 11:
                    clazz = classLoader.loadClass("[L", null);
                    break;
                default:
                    throw new RuntimeException();
            }
            ArrayObject arr=clazz.newArrayObject(len,type);
            JHeap.getInstance().addObj(arr);
            frame.getOperandStack().pushObjectRef(arr);
        }catch (ClassNotFoundException e){}

    }
}
