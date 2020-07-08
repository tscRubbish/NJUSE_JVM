package com.njuse.jvmfinal.classloader;

import com.njuse.jvmfinal.classloader.classfileparser.ClassFile;
import com.njuse.jvmfinal.classloader.classfilereader.ClassFileReader;
import com.njuse.jvmfinal.classloader.classfilereader.classpath.EntryType;
import com.njuse.jvmfinal.memory.MethodArea;
import com.njuse.jvmfinal.memory.jclass.Field;
import com.njuse.jvmfinal.memory.jclass.InitState;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.DoubleWrapper;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.FloatWrapper;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.IntWrapper;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.LongWrapper;
import com.njuse.jvmfinal.runtime.Vars;
import com.njuse.jvmfinal.runtime.struct.NullObject;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;

public class ClassLoader {
    private static ClassLoader classLoader = new ClassLoader();
    private ClassFileReader classFileReader;
    private MethodArea methodArea;

    private ClassLoader() {
        classFileReader = ClassFileReader.getInstance();
        methodArea = MethodArea.getInstance();
    }

    public static ClassLoader getInstance() {
        return classLoader;
    }

    /**
     * load phase
     *
     * @param className       name of class
     * @param initiatingEntry null value represents load MainClass
     */
    public JClass loadClass(String className, EntryType initiatingEntry) throws ClassNotFoundException {
        JClass ret;
        if ((ret = methodArea.findClass(className)) == null) {
            if (className.charAt(0)=='[')  return loadArrayClass(className,initiatingEntry);
            else return loadNonArrayClass(className, initiatingEntry);
        }
        return ret;
    }

    private JClass loadArrayClass(String className,EntryType initiatingEntry) throws ClassNotFoundException{
        JClass arr=new JClass();
        arr.setInitState(InitState.SUCCESS);
        arr.setAccessFlags((short)1);
        arr.setName(className);
        arr.setSuperClass(loadClass("java/lang/Object", initiatingEntry));
        arr.setInterfaces(new JClass[]{loadClass("java/lang/Cloneable", initiatingEntry),loadClass("java/io/Serializable", initiatingEntry)});
        this.methodArea.addClass(className,arr);
        return arr;
    }

    private JClass loadNonArrayClass(String className, EntryType initiatingEntry) throws ClassNotFoundException {
        try {
            Pair<byte[], Integer> res = classFileReader.readClassFile(className, initiatingEntry);
            byte[] data = res.getKey();
            EntryType definingEntry = new EntryType(res.getValue());
            //System.out.println(data.toString());
            //define class
            JClass clazz = defineClass(data, definingEntry);
            //System.out.println(className);
            //link class
            linkClass(clazz);

            return clazz;
        } catch (IOException e) {
            throw new ClassNotFoundException();
        }
    }

    /**
     *
     * define class
     * @param data binary of class file
     * @param definingEntry defining loader of class
     */
    private JClass defineClass(byte[] data, EntryType definingEntry) throws ClassNotFoundException {
        ClassFile classFile = new ClassFile(data);
        //System.out.println(classFile.getClassName()+" "+classFile.getSuperClassName());
        JClass clazz = new JClass(classFile);
        //System.out.println(clazz.getName()+" "+clazz.getSuperClassName());
        //todo
        /**
         * Add some codes here.
         *
         * update load entry of the class
         * load superclass recursively
         * load interfaces of this class
         * add to method area
         */
        clazz.setLoadEntryType(definingEntry);
        resolveSuperClass(clazz,definingEntry);
        resolveInterfaces(clazz);
        methodArea.addClass(clazz.getName(),clazz);
        //System.out.println(MethodArea.getClassMap().keySet());
        return clazz;
    }

    /**
     * load superclass before add to method area
     */
    private void resolveSuperClass(JClass clazz,EntryType definingEntry) throws ClassNotFoundException {
        //todo
        /**
         * Add some codes here.
         *
         * Use the load entry(defining entry) as initiating entry of super class
         */
        if (clazz.getSuperClassName().equals("")) return;
        //System.out.println(clazz.getName()+"'s  father="+clazz.getSuperClassName());
        clazz.setSuperClass(loadClass(clazz.getSuperClassName(),clazz.getLoadEntryType()));
    }

    /**
     * load interfaces before add to method area
     */
    private void resolveInterfaces(JClass clazz) throws ClassNotFoundException {
        //todo
        /**
         * Add some codes here.
         *
         * Use the load entry(defining entry) as initiating entry of interfaces
         */
        int cnt=0,len=clazz.getInterfaceNames().length;
        JClass[] interfaces=new JClass[len];
        String[] interfaceNames=clazz.getInterfaceNames();
        for (int i=0;i<len;i++){
            interfaces[i]=ClassLoader.getInstance().loadClass(interfaceNames[i],clazz.getLoadEntryType());
            //System.out.println(clazz.getName()+"'sinterface="+interfaces[i].getName());
        }
        clazz.setInterfaces(interfaces);
    }

    /**
     * link phase
     */
    private void linkClass(JClass clazz) {
        //verify(clazz);
        prepare(clazz);
    }

    /**
     * todo
     * You don't need to write any code here.
     */
    private void verify(JClass clazz) {
        ClassChecker.checkClassFile(clazz.getClassFile());
        ClassChecker.checkData(clazz);
        ClassChecker.checkByte(clazz);
        ClassChecker.checkReference(clazz);
    }

    private void prepare(JClass clazz) {
        //todo
        /**
         * Add some codes here.
         *
         * step1 (We do it for you here)
         *      count the fields slot id in instance
         *      count the fields slot id in class
         * step2
         *      alloc memory for fields(We do it for you here) and init static vars
         * step3
         *      set the init state
         */
        calInstanceFieldSlotIDs(clazz);
        calStaticFieldSlotIDs(clazz);
        allocAndInitStaticVars(clazz);
        clazz.setInitState(InitState.PREPARED);
    }

    /**
     * count the number of field slots in instance
     * long and double takes two slots
     * the field is not static
     */
    private void calInstanceFieldSlotIDs(JClass clazz) {
        int slotID = 0;
        if (clazz.getSuperClass() != null) {
            slotID = clazz.getSuperClass().getInstanceSlotCount();
        }
        Field[] fields = clazz.getFields();
        for (Field f : fields) {
            if (!f.isStatic()) {
                f.setSlotID(slotID);
                slotID++;
                if (f.isLongOrDouble()) slotID++;
            }
        }
        clazz.setInstanceSlotCount(slotID);
    }

    /**
     * count the number of field slots in class
     * long and double takes two slots
     * the field is static
     */
    private void calStaticFieldSlotIDs(JClass clazz) {
        int slotID = 0;
        Field[] fields = clazz.getFields();
        for (Field f : fields) {
            if (f.isStatic()) {
                f.setSlotID(slotID);
                slotID++;
                if (f.isLongOrDouble()) slotID++;
            }
        }
        clazz.setStaticSlotCount(slotID);

    }

    /**
     * primitive type is set to 0
     * ref type is set to null
     */
    private void initDefaultValue(JClass clazz, Field field) {
        //todo
        /**
         * Add some codes here.
         * step 1
         *      get static vars of class
         * step 2
         *      get the slotID index of field
         * step 3
         *      switch by descriptor or some part of descriptor
         *      Handle basic type ZBCSIJDF and references (with new NullObject())
         */
        Vars vars=clazz.getStaticVars();
        if (!field.isStatic()) return;
        int sid=field.getSlotID();
        switch (field.descriptor.charAt(0)){
            case 'I':
            case 'C':
            case 'S':
            case 'B':
            case 'Z':
                vars.setInt(sid,0);
                break;
            case 'F':
                vars.setFloat(sid,0.0F);
                break;
            case 'D':
                vars.setDouble(sid,0.0D);
                break;
            case 'J':
                vars.setLong(sid,0L);
                break;
            case 'L':
                vars.setObjectRef(sid,new NullObject());
                break;
            default:
                vars.setObjectRef(sid,new NullObject());
        }
    }

    /**
     * load const value from runtimeConstantPool for primitive type
     * String is not support now
     */
    private void loadValueFromRTCP(JClass clazz, Field field) {
        //todo
        /**
         * Add some codes here.
         *
         * step 1
         *      get static vars and runtime constant pool of class
         * step 2
         *      get the slotID and constantValue index of field
         * step 3
         *      switch by descriptor or some part of descriptor
         *      just handle basic type ZBCSIJDF, you don't have to throw any exception
         *      use wrapper to get value
         *
         *  Example
         *      long longVal = ((LongWrapper) runtimeConstantPool.getConstant(constantPoolIndex)).getValue();
         */
        Vars var=clazz.getStaticVars();
        int sid=field.getSlotID(),cvi=field.getConstValueIndex();
        //System.out.println(field.descriptor+" "+field.name+" "+sid);
        switch (field.descriptor.charAt(0)){
            case 'I':
            case 'C':
            case 'S':
            case 'B':
            case 'Z':
                var.setInt(sid,((IntWrapper)clazz.getRuntimeConstantPool().getConstant(cvi)).getValue());
                break;
            case 'F':
                var.setFloat(sid,((FloatWrapper)clazz.getRuntimeConstantPool().getConstant(cvi)).getValue());
                break;
            case 'D':
                var.setDouble(sid,((DoubleWrapper)clazz.getRuntimeConstantPool().getConstant(cvi)).getValue());
                break;
            case 'J':
                var.setLong(sid,((LongWrapper)clazz.getRuntimeConstantPool().getConstant(cvi)).getValue());
                break;
        }
    }

    /**
     * the value of static final field is in com.njuse.seecjvm.runtime constant pool
     * others will be set to default value
     */
    private void allocAndInitStaticVars(JClass clazz) {
        clazz.setStaticVars(new Vars(clazz.getStaticSlotCount()));
        Field[] fields = clazz.getFields();
        for (Field f : fields) {
            //todo
            /**
             * Add some codes here.
             *
             * Refer to manual for details.
             */
            if (f.isStatic()&&f.isFinal()) loadValueFromRTCP(clazz,f);
            else if (f.isStatic()) initDefaultValue(clazz,f);

        }
    }
}
