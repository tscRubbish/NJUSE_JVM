package com.njuse.jvmfinal.runtime.struct.array;

import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.runtime.struct.ArrayObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ByteArrayObject extends ArrayObject {
    private byte[] array;

    public ByteArrayObject(int len, String type, JClass clazz) {
        super(len, type,clazz);
        array = new byte[len];
    }
}
