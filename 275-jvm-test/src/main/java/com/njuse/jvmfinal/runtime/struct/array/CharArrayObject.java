package com.njuse.jvmfinal.runtime.struct.array;

import com.njuse.jvmfinal.runtime.struct.ArrayObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharArrayObject extends ArrayObject {
    private char[] array;

    public CharArrayObject(int len, String type) {
        super(len, type);
        array = new char[len];
    }
}