package com.njuse.jvmfinal.runtime;

import com.njuse.jvmfinal.runtime.struct.JObject;
import com.njuse.jvmfinal.runtime.struct.Slot;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vars {
    private Slot[] varSlots;
    private int maxSize;

    public Vars(int maxVarSize) {
        assert maxVarSize >= 0;
        maxSize = maxVarSize;
        varSlots = new Slot[maxVarSize];
        for (int i = 0; i < maxVarSize; i++) varSlots[i] = new Slot();
    }

    public void setInt(int index, int value) {
        if (index < 0 || index >= maxSize) throw new IndexOutOfBoundsException();
        varSlots[index].setValue(value);
    }

    public int getInt(int index) {
        if (index < 0 || index >= maxSize) throw new IndexOutOfBoundsException();
        return varSlots[index].getValue();
    }

    public void setFloat(int index, float value) {
        if (index < 0 || index >= maxSize) throw new IndexOutOfBoundsException();
        varSlots[index].setValue(Float.floatToIntBits(value));
    }

    public float getFloat(int index) {
        if (index < 0 || index >= maxSize) throw new IndexOutOfBoundsException();
        return Float.intBitsToFloat(varSlots[index].getValue());
    }

    /**
     * TODO：将一个long类型的变量写入局部变量表
     * @param index 变量的起始下标
     * @param value 变量的值
     */
    public void setLong(int index, long value) {
        setInt(index,(int)value);
        setInt(index+1,(int)(value>>>32));
    }

    /**
     * TODO：从局部变量表读取一个long类型变量
     * @param index 变量的起始下标
     * @return 变量的值
     */
    public long getLong(int index) {
        return (long)getInt(index+1) << 32 | (long)getInt(index) & 4294967295L;
    }

    public void setDouble(int index, double value) {
        if (index < 0 || index + 1 >= maxSize) throw new IndexOutOfBoundsException();
        setLong(index, Double.doubleToLongBits(value));
    }

    public double getDouble(int index) {
        if (index < 0 || index + 1 >= maxSize) throw new IndexOutOfBoundsException();
        return Double.longBitsToDouble(getLong(index));
    }

    public void setObjectRef(int index, JObject ref) {
        if (index < 0 || index >= maxSize) throw new IndexOutOfBoundsException();
        varSlots[index].setObject(ref);
    }

    public JObject getObjectRef(int index) {
        if (index < 0 || index >= maxSize) throw new IndexOutOfBoundsException();
        return varSlots[index].getObject();
    }

    public void setSlot(int index, Slot slot) {
        if (index < 0 || index >= maxSize) throw new IndexOutOfBoundsException();
        varSlots[index] = slot;
    }

}
