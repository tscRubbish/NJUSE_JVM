package com.njuse.jvmfinal.instructions.references;

import com.njuse.jvmfinal.classloader.ClassLoader;
import com.njuse.jvmfinal.classloader.classfilereader.classpath.EntryType;
import com.njuse.jvmfinal.instructions.base.Index8Instruction;
import com.njuse.jvmfinal.memory.JHeap;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.ArrayObject;
import com.njuse.jvmfinal.runtime.struct.array.ArrayType;

import java.nio.ByteBuffer;

public class NEWARRAY extends Index8Instruction {

    @Override
    public void fetchOperands(ByteBuffer reader) {
        index=reader.get()&0xFF;
    }

    @Override
    public void execute(StackFrame frame) {
        int len=frame.getOperandStack().popInt();
        JClass clazz;
        String type= ArrayType.getName(index);
        ClassLoader classLoader=ClassLoader.getInstance();
        EntryType en=frame.getMethod().getClazz().getLoadEntryType();
        try {
            switch (index) {
                case 4:
                    clazz = classLoader.loadClass("[Z", en);
                    break;
                case 5:
                    clazz = classLoader.loadClass("[C", en);
                    break;
                case 6:
                    clazz = classLoader.loadClass("[F", en);
                    break;
                case 7:
                    clazz = classLoader.loadClass("[D", en);
                    break;
                case 8:
                    clazz = classLoader.loadClass("[B", en);
                    break;
                case 9:
                    clazz = classLoader.loadClass("[S", en);
                    break;
                case 10:
                    clazz = classLoader.loadClass("[I", en);
                    break;
                case 11:
                    clazz = classLoader.loadClass("[J", en);
                    break;
                default:
                    throw new RuntimeException();
            }
            ArrayObject arr=clazz.newArrayObject(len);
            JHeap.getInstance().addObj(arr);
            frame.getOperandStack().pushObjectRef(arr);
        }catch (ClassNotFoundException e){e.printStackTrace();}

    }
}
