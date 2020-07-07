package com.njuse.jvmfinal.instructions.references;

import com.njuse.jvmfinal.instructions.base.Instruction;
import com.njuse.jvmfinal.memory.JHeap;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.ClassRef;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.ArrayObject;
import com.njuse.jvmfinal.runtime.struct.array.RefArrayObject;

import java.nio.ByteBuffer;

public class MULTIANEWARRAY extends Instruction {
    private int index,dimensions;
    @Override
    public void fetchOperands(ByteBuffer reader) {
        this.index=(int)reader.getShort()&'\uffff';
        this.dimensions=(int)reader.get()&255;
    }

    @Override
    public void execute(StackFrame frame) {
        RuntimeConstantPool rcp=frame.getMethod().getClazz().getRuntimeConstantPool();
        try {
            JClass clazz = ((ClassRef) rcp.getConstant(index)).getResolvedClass();
            int[] lenarr=new int[dimensions];
            for (int i=dimensions-1;i>=0;i--)
                lenarr[i]=frame.getOperandStack().popInt();
            ArrayObject arr=getMutiArray(0,clazz,lenarr);
            JHeap.getInstance().addObj(arr);
            frame.getOperandStack().pushObjectRef(arr);
        }catch (ClassNotFoundException e){}
    }
    ArrayObject getMutiArray(int D,JClass clazz,int[] lenarr){
        ArrayObject arr=clazz.newArrayObject(lenarr[D]);
        if (D==dimensions-1)
            return arr;
        //ArrayObject target=getMutiArray(D+1,clazz.getComponentClass(),lenarr);
        for (int i=0;i<lenarr[D];i++){
            ((RefArrayObject)arr).getArray()[i]=getMutiArray(D+1,clazz.getComponentClass(),lenarr);//必须分别调用方法
            //否则每一项指向的对象相同
        }
        return arr;
    }
}
