package com.njuse.jvmfinal.vo;

import java.util.ArrayList;
import java.util.List;

public class MemoryVO {
    private List<ObjectVO> objects = new ArrayList();
    private List<ClassVO> classes = new ArrayList();

    public MemoryVO() {
    }

    public List<ObjectVO> getObjects() {
        return this.objects;
    }

    public List<ClassVO> getClasses() {
        return this.classes;
    }

    public void setObjects(List<ObjectVO> objects) {
        this.objects = objects;
    }

    public void setClasses(List<ClassVO> classes) {
        this.classes = classes;
    }
}
