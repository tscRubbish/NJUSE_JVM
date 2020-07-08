package com.njuse.jvmfinal.instructions.references;

import com.njuse.jvmfinal.instructions.base.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.Field;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.FieldRef;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.Vars;
import com.njuse.jvmfinal.runtime.struct.NonArrayObject;

public class GETFIELD extends Index16Instruction {

    /**
     * TODO 实现这条指令
     * 其中 对应的index已经读取好了
     */
    @Override
    public void execute(StackFrame frame) {
        RuntimeConstantPool rcp=frame.getMethod().getClazz().getRuntimeConstantPool();
        FieldRef fr=(FieldRef) rcp.getConstant(super.index);
        try {
            Field field = fr.getResolvedFieldRef();
            if (field.isStatic()) {
                throw new IncompatibleClassChangeError();
            }
            JClass target=field.getClazz();
            int SlotId=field.getSlotID();
            NonArrayObject ref=(NonArrayObject) frame.getOperandStack().popObjectRef();
            if (ref.isNull()) {
                throw new NullPointerException();
            }
            Vars fields=ref.getFields();
            switch (field.getDescriptor().charAt(0)){
                case 'I':
                case 'B':
                case 'C':
                case 'Z':
                case 'S':
                    frame.getOperandStack().pushInt(fields.getInt(SlotId));
                    break;
                case 'J':
                    frame.getOperandStack().pushLong(fields.getLong(SlotId));
                    break;
                case 'D':
                    frame.getOperandStack().pushDouble(fields.getDouble(SlotId));
                    break;
                case 'F':
                    frame.getOperandStack().pushFloat(fields.getFloat(SlotId));
                    break;
                case '[':
                case 'L':
                    frame.getOperandStack().pushObjectRef(fields.getObjectRef(SlotId));
                    break;
            }
        }catch (Exception e){}
    }
}
