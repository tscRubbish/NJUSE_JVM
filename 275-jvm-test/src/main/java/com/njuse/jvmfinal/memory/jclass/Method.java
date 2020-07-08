package com.njuse.jvmfinal.memory.jclass;

import com.njuse.jvmfinal.classloader.classfileparser.MethodInfo;
import com.njuse.jvmfinal.classloader.classfileparser.attribute.CodeAttribute;
import com.njuse.jvmfinal.execution.Decoder;
import com.njuse.jvmfinal.instructions.base.Instruction;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.ByteBuffer;
import java.util.ArrayList;


@Getter
@Setter
public class Method extends ClassMember {
    private int maxStack;
    private int maxLocal;
    private int argc;
    private byte[] code;
    private ArrayList<Pair<Instruction,Integer>> instList;
    boolean parsed = false;

    public Method(MethodInfo info, JClass clazz) {
        this.clazz = clazz;
        accessFlags = info.getAccessFlags();
        name = info.getName();
        descriptor = info.getDescriptor();

        CodeAttribute codeAttribute = info.getCodeAttribute();
        if (codeAttribute != null) {
            maxLocal = codeAttribute.getMaxLocal();
            maxStack = codeAttribute.getMaxStack();
            code = codeAttribute.getCode();
        }
        argc = calculateArgcFromDescriptor(descriptor);
    }

    private int calculateArgcFromDescriptor(String descriptor) {
        /**
         * Add some codes here.
         * Here are some examples in README!!!
         *
         * You should refer to JVM specification for more details
         *
         * Beware of long and double type
         */
        int len=descriptor.length(),sum=0;
        for (int i=descriptor.indexOf('(');i<len;i++) {
            char ch=descriptor.charAt(i);
            switch (ch){
                case 'J':
                case 'D':{
                    sum+=2;
                    break;
                }
                case 'L':{
                    sum+=1;
                    while (descriptor.charAt(i)!=';') i++;
                    break;
                }
                case '(':break;
                case ')':{
                    i=len;break;
                }
                case '[':break;
                case 'I':
                case 'F':
                case 'C':
                case 'Z':
                case 'S':
                case 'B': {
                    sum+=1;
                    break;
                }
            }
        }
        //System.out.println("Des="+getDescriptor());
        //System.out.println("Name="+getName());
        //System.out.println("sum="+sum);
        return sum;
    }
    public void parseCode() {
        if (this.code==null) return;
        ByteBuffer codeReader = ByteBuffer.wrap(this.code);
        int position = 0;
        codeReader.position(position);
        int size = this.code.length;

        for(this.instList = new ArrayList(); position <= size - 1; position = codeReader.position()) {
            int opcode = codeReader.get() & 255;
            Instruction instruction = Decoder.decode(opcode);
            instruction.fetchOperands(codeReader);
            this.instList.add(Pair.of(instruction,codeReader.position()));
            //this.instList.add(position + " " + instruction.toString());
            //System.out.println(getName()+":"+instruction.toString()+" "+codeReader.position());
        }

        this.parsed = true;
    }
}
