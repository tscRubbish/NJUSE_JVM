package com.njuse.jvmfinal.instructions.references;

import com.njuse.jvmfinal.instructions.base.Instruction;
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
        this.index=reader.getShort();
        this.dimensions=reader.get();
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
        }catch (ClassNotFoundException e){}
    }
    ArrayObject getMutiArray(int D,JClass clazz,int[] lenarr){
        if (D==dimensions-1)
            return clazz.newArrayObject(lenarr[D],"Ref");
        ArrayObject arr=new RefArrayObject(lenarr[D],"Ref");
        ArrayObject target=getMutiArray(D+1,clazz,lenarr);
        for (int i=0;i<lenarr[D];i++){
            ((RefArrayObject)arr).getArray()[i]=target;
        }
        return arr;
    }
}
