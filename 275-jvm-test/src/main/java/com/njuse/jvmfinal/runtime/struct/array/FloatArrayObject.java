package com.njuse.jvmfinal.runtime.struct.array;

import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.runtime.struct.ArrayObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FloatArrayObject extends ArrayObject {
    private float[] array;

    public FloatArrayObject(int len, String type, JClass clazz) {
        super(len, type,clazz);
        array = new float[len];
    }
}
