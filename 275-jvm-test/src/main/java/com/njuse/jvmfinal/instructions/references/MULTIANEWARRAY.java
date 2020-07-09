package com.njuse.jvmfinal.instructions.references;

import com.njuse.jvmfinal.instructions.base.Instruction;
import com.njuse.jvmfinal.memory.JHeap;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.ClassRef;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.ArrayObject;
import com.njuse.jvmfinal.runtime.struct.array.RefArrayObject;

import java.nio.ByteBuffer;

public class MULTIANEWARRAY extends Instruction {
    private int index;
    private int dimensions;

    public MULTIANEWARRAY() {
    }

    public void fetchOperands(ByteBuffer reader) {
        this.index = reader.getShort() & '\uffff';
        this.dimensions = reader.get() & 255;
    }

    public void execute(StackFrame frame) {
        RuntimeConstantPool runtimeConstantPool = frame.getMethod().getClazz().getRuntimeConstantPool();
        ClassRef classRef = (ClassRef)runtimeConstantPool.getConstant(this.index);

        try {
            JClass arrClass = classRef.getResolvedClass();
            OperandStack stack = frame.getOperandStack();
            int[] lenArr = this.popAndCheck(stack, this.dimensions);
            ArrayObject ref = this.createMultiDimensionArray(0, lenArr, arrClass);
            JHeap.getInstance().addObj(ref);
            stack.pushObjectRef(ref);
        } catch (ClassNotFoundException var8) {
            var8.printStackTrace();
        }

    }

    private int[] popAndCheck(OperandStack stack, int dimensions) {
        int[] lenArr = new int[dimensions];

        for(int i = dimensions - 1; i >= 0; --i) {
            lenArr[i] = stack.popInt();
            if (lenArr[i] < 0) {
                throw new NegativeArraySizeException();
            }
        }

        return lenArr;
    }

    private ArrayObject createMultiDimensionArray(int index, int[] lenArray, JClass arrClass) {
        int len = lenArray[index];
        ++index;
        ArrayObject arr = arrClass.newArrayObject(len);
        if (index <= lenArray.length - 1) {
            assert arr instanceof RefArrayObject;

            for(int i = 0; i < arr.getLen(); ++i) {
                ((RefArrayObject)arr).getArray()[i] = this.createMultiDimensionArray(index, lenArray, arrClass.getComponentClass());
            }
        }

        return arr;
    }

    public String toString() {
        return this.getClass().getSimpleName() + " index : " + this.index + "dimension : " + this.dimensions;
    }
}
