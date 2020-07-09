package com.njuse.jvmfinal.runtime.struct.array;

import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.runtime.struct.ArrayObject;
import com.njuse.jvmfinal.runtime.struct.JObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefArrayObject extends ArrayObject {
    private JObject[] array;

    public RefArrayObject(int len, String type, JClass clazz) {
        super(len, type,clazz);
        array = new JObject[len];

    }
}
