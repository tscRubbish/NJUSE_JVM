package com.njuse.jvmfinal.vo;

import com.njuse.jvmfinal.memory.jclass.Field;
import com.njuse.jvmfinal.runtime.Vars;
import com.njuse.jvmfinal.runtime.struct.ArrayObject;
import com.njuse.jvmfinal.runtime.struct.JObject;
import com.njuse.jvmfinal.runtime.struct.NonArrayObject;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ObjectVO extends HeapContentVO {
    private Map<String, String> members;
    private String id;
    private boolean fresh;

    public ObjectVO(JObject obj, boolean fresh) {
        this.fresh = fresh;
        this.members = new LinkedHashMap();
        this.setBasic(obj);
    }

    private void setBasic(JObject obj) {
        String className = null;
        if (obj instanceof NonArrayObject) {
            className = obj.getClazz().getName();
            this.members = this.parseMembers(((NonArrayObject)obj).getFieldInfoList(), ((NonArrayObject)obj).getFields());
        } else if (obj instanceof ArrayObject) {
            className = ((ArrayObject)obj).getType();
            this.members = this.parseArray((ArrayObject)obj);
        }

        this.id = obj.getId() + "@" + className;
    }

    private Map<String, String> parseMembers(ArrayList<Pair<String, Integer>> fieldInfoList, Vars instanceVars) {
        Map<String, String> ret = new LinkedHashMap();
        fieldInfoList.forEach((info) -> {
            String typeAndName = (String)info.getKey();
            int slotID = (Integer)info.getValue();
            String type = typeAndName.split(" ")[0];
            ret.put(typeAndName, this.getInfo(instanceVars, type, slotID));
        });
        return ret;
    }

    private Map<String, String> parseArray(ArrayObject obj) {
        Map<String, String> ret = new LinkedHashMap();
        ret.put("int len", "" + obj.getLen());
        String type = Field.parseDescriptor(obj.getType());
        String content = this.getArrayContent(obj);
        ret.put(type + " array", content);
        return ret;
    }
}

