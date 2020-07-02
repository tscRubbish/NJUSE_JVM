package com.njuse.jvmfinal.memory;

import com.njuse.jvmfinal.memory.jclass.JClass;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class MethodArea {
    private static MethodArea methodArea = new MethodArea();

    private MethodArea() {
        classMap = new LinkedHashMap<>();
    }

    @Setter @Getter
    private static Map<String, JClass> classMap;
    private static Map<String, Boolean> classState;
    public static MethodArea getInstance() {
        return methodArea;
    }

    public static void setClassMap(Map<String, JClass> classMap) {
        MethodArea.classMap = classMap;
    }

    public static Map<String, Boolean> getClassState() {
        return classState;
    }

    public static Map<String, JClass> getClassMap() {
        return classMap;
    }
    public JClass findClass(String className) {
        if (classMap.keySet().stream().anyMatch(name -> name.equals(className))) {
            return classMap.get(className);
        } else {
            return null;
        }
    }

    public void addClass(String className, JClass clazz) {
        classMap.put(className, clazz);
        classState.put(className, true);
    }
}
