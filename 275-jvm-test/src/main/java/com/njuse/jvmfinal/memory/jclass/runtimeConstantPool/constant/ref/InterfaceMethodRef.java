package com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref;

import com.njuse.jvmfinal.classloader.classfileparser.constantpool.info.InterfaceMethodrefInfo;
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
public class InterfaceMethodRef extends MemberRef {
    private Method method;

    public InterfaceMethodRef(RuntimeConstantPool runtimeConstantPool, InterfaceMethodrefInfo interfaceMethodrefInfo) {
        super(runtimeConstantPool, interfaceMethodrefInfo);
        //method
    }
    public Method resolveInterfaceMethodRef(JClass clazz) {
        resolve(clazz);
        return method;
    }
    public Method resolveInterfaceMethodRef(JClass clazz,short access_flag) {
        resolve(clazz,access_flag);
        return method;
    }

    public Method resolveInterfaceMethodRef() {
        try {
            resolveClassRef();
            resolve(clazz);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return method;
    }

    public void resolve(JClass clazz) {
        assert clazz != null;

        Optional<Method> optionalMethod = Optional.empty();

        for (JClass currentClazz = clazz;
             currentClazz != null;
             currentClazz = currentClazz.getSuperClass()) {

            optionalMethod = currentClazz.resolveMethod(name, descriptor);

            if (optionalMethod.isPresent()) {
                method = optionalMethod.get();
                return;
            }

        }

        //if not found in class hierarchy
        JClass[] ifs = clazz.getInterfaces();
        Stack<JClass> interfaces = new Stack<>();
        interfaces.addAll(Arrays.asList(ifs));
        while (!interfaces.isEmpty()) {
            JClass clz = interfaces.pop();
            optionalMethod = clz.resolveMethod(name, descriptor);
            if (optionalMethod.isPresent()) {
                method = optionalMethod.get();
                return;
            }
            interfaces.addAll(Arrays.asList(clz.getInterfaces()));
        }
    }
    public void resolve(JClass clazz,short access_flags) {
        assert clazz != null;

        Optional<Method> optionalMethod = Optional.empty();

        for (JClass currentClazz = clazz;
             currentClazz != null;
             currentClazz = currentClazz.getSuperClass()) {

            optionalMethod = currentClazz.resolveMethod(name, descriptor,access_flags);

            if (optionalMethod.isPresent()) {
                method = optionalMethod.get();
                return;
            }

        }

        //if not found in class hierarchy
        JClass[] ifs = clazz.getInterfaces();
        Stack<JClass> interfaces = new Stack<>();
        interfaces.addAll(Arrays.asList(ifs));
        while (!interfaces.isEmpty()) {
            JClass clz = interfaces.pop();
            optionalMethod = clz.resolveMethod(name, descriptor,access_flags);
            if (optionalMethod.isPresent()) {
                method = optionalMethod.get();
                return;
            }
            interfaces.addAll(Arrays.asList(clz.getInterfaces()));
        }
    }
    @Override
    public String toString() {
        return "InterfaceMethodRef to " + className;
    }
}
