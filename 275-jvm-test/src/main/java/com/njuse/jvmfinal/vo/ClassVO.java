package com.njuse.jvmfinal.vo;

import com.njuse.jvmfinal.memory.MethodArea;
import com.njuse.jvmfinal.memory.jclass.ClassMember;
import com.njuse.jvmfinal.memory.jclass.Field;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.DoubleWrapper;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.LongWrapper;
import com.njuse.jvmfinal.runtime.Vars;

import java.util.*;

public class ClassVO extends HeapContentVO {
    private String className;
    private Map<String, String> staticMembers;
    private List<String> rtcp;
    private boolean fresh;

    public ClassVO(JClass clazz) {
        this.className = clazz.getName();
        this.fresh = (Boolean) MethodArea.getClassState().get(this.className);
        if (!this.className.startsWith("[")) {
            this.staticMembers = this.parseStaticMembers(clazz);
            this.rtcp = this.parseRTCP(clazz);
        }

    }

    private Map<String, String> parseStaticMembers(JClass clazz) {
        Map<String, String> ret = new LinkedHashMap();
        Vars staticVars = clazz.getStaticVars();
        Arrays.stream(clazz.getFields()).filter(ClassMember::isStatic).forEach((f) -> {
            String type = Field.parseDescriptor(f.getDescriptor());
            String name = f.getName();
            int slotID = f.getSlotID();
            ret.put("static " + type + " " + name, this.getInfo(staticVars, type, slotID));
        });
        return ret;
    }

    private List<String> parseRTCP(JClass clazz) {
        List<String> ret = new ArrayList();
        Constant[] constants = clazz.getRuntimeConstantPool().getConstants();

        for(int i = 0; i < constants.length; ++i) {
            ret.add("index: " + i + " " + constants[i].toString());
            if (constants[i] instanceof DoubleWrapper || constants[i] instanceof LongWrapper) {
                ++i;
            }
        }

        return ret;
    }
}
