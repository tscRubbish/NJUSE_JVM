package com.njuse.jvmfinal.runtime;

import com.njuse.jvmfinal.runtime.struct.JObject;
import com.njuse.jvmfinal.runtime.struct.Slot;
import lombok.Getter;
import lombok.Setter;

import java.util.EmptyStackException;

@Getter
@Setter
public class OperandStack {
    private int top;
    private int maxStackSize;
    private Slot[] slots;

    public OperandStack(int maxStackSize) {
        assert maxStackSize >= 0;
        this.maxStackSize = maxStackSize;
        slots = new Slot[maxStackSize];
        for (int i = 0; i < maxStackSize; i++) slots[i] = new Slot();
        top = 0;
    }

    /**
     * TODO：向操作数栈顶端push一个int型变量
     *
     * @param value 变量的值
     */
    public void pushInt(int value) {
        if (top>=maxStackSize) throw new StackOverflowError();
        slots[top].setValue(value);
        top++;
    }

    /**
     * TODO：从操作数栈顶端pop一个int型变量
     *
     * @return 返回这个int的值
     */
    public int popInt() {
        top--;
        if (top<0) throw new EmptyStackException();
        int ret=slots[top].getValue();
        slots[top]=new Slot();
        return ret;
    }

    public void pushFloat(float value) {
        if (top >= maxStackSize) throw new StackOverflowError();
        slots[top].setValue(Float.floatToIntBits(value));
        top++;
    }

    public float popFloat() {
        top--;
        if (top < 0) throw new EmptyStackException();
        float ret = Float.intBitsToFloat(slots[top].getValue());
        slots[top] = new Slot();
        return ret;
    }

    /**
     * TODO：向操作数栈顶push一个 long 类型的变量
     *
     * @param value 变量的值
     */
    public void pushLong(long value) {
        pushInt((int)(value));
        pushInt((int)(value>>>32));//超级玄学的位运算
        //System.out.println("in"+top+":  "+value+" "+(int)(0xFFFFFFFFL&value)+" "+(int)((0xFFFFFFFF00000000L&value)>>32));
    }

    /**
     * TODO：从操作数栈顶端pop一个long型变量
     *
     * @return 返回这个long的值
     */
    public long popLong() {
        long l=0;
        int i1=popInt(),i2=popInt();
        //l=(long)i1<<32|(long)i2&4294967295L;//超级玄学的位运算
        //System.out.println("Out"+top+":   "+l+" "+(((long)i1<<32)&0xFFFFFFFF00000000L)+" "+((long)i2&0x00000000ffffffffL));
        return (long)i1 << 32 | (long)i2 & 4294967295L;
    }

    public void pushDouble(double value) {
        pushLong(Double.doubleToLongBits(value));
    }

    /**
     * TODO：从操作数栈顶端pop一个double型变量
     *
     * @return 返回这个double的值
     */
    public double popDouble() {
        return Double.longBitsToDouble(popLong());
    }

    public void pushObjectRef(JObject ref) {
        if (top >= maxStackSize) throw new StackOverflowError();
        slots[top].setObject(ref);
        top++;
    }

    public JObject popObjectRef() {
        top--;
        if (top < 0) throw new EmptyStackException();
        JObject ret = slots[top].getObject();
        slots[top] = new Slot();
        return ret;
    }

    public void pushSlot(Slot slot) {
        if (top >= maxStackSize) throw new StackOverflowError();
        slots[top] = slot;
        top++;
    }

    public Slot popSlot() {
        top--;
        if (top < 0) throw new EmptyStackException();
        Slot ret = slots[top];
        slots[top] = new Slot();
        return ret;
    }

}
