package com.njuse.jvmfinal.instructions.references;

import com.njuse.jvmfinal.instructions.base.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.Field;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.FieldRef;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.JObject;
import com.njuse.jvmfinal.runtime.struct.NonArrayObject;

public class PUTFIELD extends Index16Instruction {
    /**
     * TODO 实现这条指令
     * 其中 对应的index已经读取好了
     */
    @Override
    public void execute(StackFrame frame) {
        RuntimeConstantPool rcp=frame.getMethod().getClazz().getRuntimeConstantPool();
        FieldRef fr=(FieldRef) rcp.getConstant(super.index);
        try{
            Field field=fr.getResolvedFieldRef();
            JClass target=field.getClazz();
            int SlotId=field.getSlotID();
            JObject jObject;
            Object value;
            switch (field.getDescriptor().charAt(0)){
                case 'I':
                case 'B':
                case 'C':
                case 'Z':
                case 'S':
                    value=frame.getOperandStack().popInt();
                    jObject=frame.getOperandStack().popObjectRef();
                    ((NonArrayObject)jObject).getFields().setInt(SlotId,(int)value);
                    break;
                case 'J':
                    value=frame.getOperandStack().popLong();
                    jObject=frame.getOperandStack().popObjectRef();
                    ((NonArrayObject)jObject).getFields().setLong(SlotId,(long) value);
                    break;
                case 'D':
                    value=frame.getOperandStack().popDouble();
                    jObject=frame.getOperandStack().popObjectRef();
                    ((NonArrayObject)jObject).getFields().setDouble(SlotId,(double)value);
                    break;
                case 'F':
                    value=frame.getOperandStack().popFloat();
                    jObject=frame.getOperandStack().popObjectRef();
                    ((NonArrayObject)jObject).getFields().setFloat(SlotId,(float) value);
                    break;
                case '[':
                case 'L':
                    value=frame.getOperandStack().popObjectRef();
                    jObject=frame.getOperandStack().popObjectRef();
                    ((NonArrayObject)jObject).getFields().setObjectRef(SlotId,(JObject) value);
                    break;
            }
        }catch (ClassNotFoundException e){e.printStackTrace();}
    }

}
