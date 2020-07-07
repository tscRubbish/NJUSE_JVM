package com.njuse.jvmfinal.memory;

import com.njuse.jvmfinal.runtime.struct.JObject;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Setter
@Getter
public class JHeap {
    private static JHeap jHeap = new JHeap();
    private static Set<JObject> objects;
    private static int maxSize = 50;
    private static int currentSize = 0;

    public static JHeap getInstance() {
        return jHeap;
    }

    private JHeap() {
        objects = new LinkedHashSet<>();
    }

    public void addObj(JObject obj) {
        if (currentSize >= maxSize) throw new OutOfMemoryError();
        objects.add(obj);
        currentSize++;
    }

    public Set<JObject> getObjects() {
        return objects;
    }

    public static void reset() {
        objects = new LinkedHashSet<>();
    }
}
