package com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref;

import com.njuse.jvmfinal.classloader.classfileparser.constantpool.info.MethodrefInfo;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Optional;
import java.util.Stack;

@Getter
@Setter
public class MethodRef extends MemberRef {
    private Method method;

    public MethodRef(RuntimeConstantPool runtimeConstantPool, MethodrefInfo methodrefInfo) {
        super(runtimeConstantPool, methodrefInfo);
    }
    /**
     * TODO：实现这个方法
     * 这个方法用来实现对象方法的动态查找
     * @param clazz 对象的引用
     */
    public Method resolveMethodRef(JClass clazz) {
        assert clazz!=null;
        if (this.method!=null) return method;
        for (JClass jc = clazz; jc!=null; jc=jc.getSuperClass()){
            Optional optional=jc.resolveMethod(this.name,this.descriptor);
            if (optional.isPresent()) {
                method=(Method)optional.get();
                return method;
            }
        }
        if (clazz.getInterfaces()==null) return this.method;
//        for (JClass jc:clazz.getInterfaces()){
//            Optional optional=jc.resolveMethod(this.name,this.descriptor);
//            if (optional.isPresent()) {
//                method=(Method)optional.get();
//                return method;
//            }
//        }
        JClass[] ifs = clazz.getInterfaces();
        Stack<JClass> interfaces = new Stack();
        interfaces.addAll(Arrays.asList(ifs));

        while(!interfaces.isEmpty()) {
            JClass clz = (JClass)interfaces.pop();
            Optional optional= clz.resolveMethod(this.name, this.descriptor);
            if (optional.isPresent()) {
                this.method = (Method)optional.get();
                return method;
            }

            interfaces.addAll(Arrays.asList(clz.getInterfaces()));
        }
        return method;
    }

    /**
     * TODO: 实现这个方法
     * 这个方法用来解析methodRef对应的方法
     * 与上面的动态查找相比，这里的查找始终是从这个Ref对应的class开始查找的
     */
    public Method resolveMethodRef() {
        if (this.method!=null) return method;
        try {
            this.resolveClassRef();
            return resolveMethodRef(clazz);
        }catch (Exception e){
            e.printStackTrace();
        }
        return method;
    }


    @Override
    public String toString() {
        return "MethodRef to " + className;
    }
}
