package com.njuse.jvmfinal.execution;

import com.njuse.jvmfinal.instructions.base.Instruction;
import com.njuse.jvmfinal.instructions.base.OpCode;
import com.njuse.jvmfinal.instructions.comparison.*;
import com.njuse.jvmfinal.instructions.constant.*;
import com.njuse.jvmfinal.instructions.control.*;
import com.njuse.jvmfinal.instructions.conversion.*;
import com.njuse.jvmfinal.instructions.invoke.INVOKE_INTERFACE;
import com.njuse.jvmfinal.instructions.invoke.INVOKE_SPECIAL;
import com.njuse.jvmfinal.instructions.invoke.INVOKE_STATIC;
import com.njuse.jvmfinal.instructions.invoke.INVOKE_VIRTUAL;
import com.njuse.jvmfinal.instructions.load.*;
import com.njuse.jvmfinal.instructions.math.algorithm.*;
import com.njuse.jvmfinal.instructions.math.bool.*;
import com.njuse.jvmfinal.instructions.math.shift.ISHL;
import com.njuse.jvmfinal.instructions.math.shift.ISHR;
import com.njuse.jvmfinal.instructions.references.*;
import com.njuse.jvmfinal.instructions.stack.DUP;
import com.njuse.jvmfinal.instructions.stack.DUP2;
import com.njuse.jvmfinal.instructions.stack.POP;
import com.njuse.jvmfinal.instructions.store.*;
import com.njuse.jvmfinal.instructions.control.LRETURN;

import java.util.HashMap;

import static com.njuse.jvmfinal.instructions.base.OpCode.RETURN_;

public class Decoder {

    private static HashMap<Integer, Instruction> opMap;

    static {
        opMap = new HashMap<>();
        opMap.put(OpCode.NOP, new NOP());
        opMap.put(OpCode.ACONST_NULL, new ACONST_NULL());
        opMap.put(OpCode.ICONST_M1, new ICONST_N(-1));
        opMap.put(OpCode.ICONST_0, new ICONST_N(0));
        opMap.put(OpCode.ICONST_1, new ICONST_N(1));
        opMap.put(OpCode.ICONST_2, new ICONST_N(2));
        opMap.put(OpCode.ICONST_3, new ICONST_N(3));
        opMap.put(OpCode.ICONST_4, new ICONST_N(4));
        opMap.put(OpCode.ICONST_5, new ICONST_N(5));
        opMap.put(OpCode.LCONST_0, new LCONST_N(0L));
        opMap.put(OpCode.LCONST_1,new LCONST_N(1L));
        opMap.put(OpCode.DCONST_0,new DCONST_N(0.0D));
        opMap.put(OpCode.DCONST_1,new DCONST_N(1.0D));
        opMap.put(OpCode.FCONST_0,new FCONST_N(0.0F));
        opMap.put(OpCode.FCONST_1,new FCONST_N(1.0F));
        opMap.put(OpCode.FCONST_2,new FCONST_N(2.0F));
        opMap.put(OpCode.BIPUSH, new BIPUSH());
        opMap.put(OpCode.SIPUSH, new SIPUSH());
        opMap.put(OpCode.LDC, new LDC());
        opMap.put(OpCode.LDC_W, new LDC_W());
        opMap.put(OpCode.LDC2_W, new LDC2_W());

        opMap.put(OpCode.ISTORE,new ISTORE());
        opMap.put(OpCode.POP, new POP());
//        opMap.put(OpCode.POP2, new POP2());
        opMap.put(OpCode.DUP, new DUP());
        opMap.put(OpCode.DUP2,new DUP2());

        opMap.put(OpCode.IADD,new IADD());
        opMap.put(OpCode.IDIV,new IDIV());
        opMap.put(OpCode.IINC,new IINC());
        opMap.put(OpCode.IMUL,new IMUL());
        opMap.put(OpCode.ISUB,new ISUB());
        opMap.put(OpCode.IAND,new IAND());
        opMap.put(OpCode.IXOR,new IXOR());
        opMap.put(OpCode.IOR,new IOR());
        opMap.put(OpCode.INEG,new INEG());
        opMap.put(OpCode.IREM,new IREM());
        opMap.put(OpCode.LADD,new LADD());
        opMap.put(OpCode.LAND,new LAND());
        opMap.put(OpCode.LXOR,new LXOR());
        opMap.put(OpCode.LOR,new LOR());
        opMap.put(OpCode.FADD,new FADD());
        opMap.put(OpCode.DADD,new DADD());

        opMap.put(OpCode.I2B,new I2B());
        opMap.put(OpCode.I2C,new I2C());
        opMap.put(OpCode.I2S,new I2S());
        opMap.put(OpCode.I2D,new I2D());
        opMap.put(OpCode.I2F,new I2F());
        opMap.put(OpCode.I2L,new I2L());
        opMap.put(OpCode.D2F,new D2F());
        opMap.put(OpCode.D2I,new D2I());
        opMap.put(OpCode.D2L,new D2L());
        opMap.put(OpCode.F2D,new F2D());
        opMap.put(OpCode.F2I,new F2I());
        opMap.put(OpCode.F2L,new F2L());
        opMap.put(OpCode.L2D,new L2D());
        opMap.put(OpCode.L2F,new L2F());
        opMap.put(OpCode.L2I,new L2I());

        opMap.put(OpCode.ISHL,new ISHL());
        opMap.put(OpCode.ISHR,new ISHR());

        opMap.put(OpCode.LCMP,new LCMP());
        opMap.put(OpCode.FCMPL,new FCMPL());
        opMap.put(OpCode.FCMPG,new FCMPG());
        opMap.put(OpCode.DCMPL,new DCMPL());
        opMap.put(OpCode.DCMPG,new DCMPG());

        opMap.put(OpCode.ILOAD, new ILOAD());
        opMap.put(OpCode.LLOAD,new LLOAD());
        opMap.put(OpCode.FLOAD,new FLOAD());
        opMap.put(OpCode.DLOAD,new DLOAD());
        opMap.put(OpCode.ALOAD,new ALOAD());
        opMap.put(OpCode.AALOAD,new AALOAD());
        opMap.put(OpCode.CALOAD,new CALOAD());
        opMap.put(OpCode.ILOAD_0, new ILOAD_N(0));
        opMap.put(OpCode.ILOAD_1, new ILOAD_N(1));
        opMap.put(OpCode.ILOAD_2, new ILOAD_N(2));
        opMap.put(OpCode.ILOAD_3, new ILOAD_N(3));
        opMap.put(OpCode.LLOAD_0, new LLOAD_N(0));
        opMap.put(OpCode.LLOAD_1, new LLOAD_N(1));
        opMap.put(OpCode.LLOAD_2, new LLOAD_N(2));
        opMap.put(OpCode.LLOAD_3, new LLOAD_N(3));
        opMap.put(OpCode.ALOAD_0, new ALOAD_N(0));
        opMap.put(OpCode.ALOAD_1, new ALOAD_N(1));
        opMap.put(OpCode.ALOAD_2, new ALOAD_N(2));
        opMap.put(OpCode.ALOAD_3, new ALOAD_N(3));
        opMap.put(OpCode.DLOAD_0, new DLOAD_N(0));
        opMap.put(OpCode.DLOAD_1, new DLOAD_N(1));
        opMap.put(OpCode.DLOAD_2, new DLOAD_N(2));
        opMap.put(OpCode.DLOAD_3, new DLOAD_N(3));
        opMap.put(OpCode.FLOAD_0, new FLOAD_N(0));
        opMap.put(OpCode.FLOAD_1, new FLOAD_N(1));
        opMap.put(OpCode.FLOAD_2, new FLOAD_N(2));
        opMap.put(OpCode.FLOAD_3, new FLOAD_N(3));
        opMap.put(OpCode.IALOAD, new IALOAD());
//        opMap.put(OpCode.LALOAD, new LALOAD());

        opMap.put(OpCode.AASTORE,new AASTORE());
        opMap.put(OpCode.ASTORE, new ASTORE());
        opMap.put(OpCode.LSTORE,new LSTORE());
        opMap.put(OpCode.FSTORE,new FSTORE());
        opMap.put(OpCode.DSTORE,new DSTORE());
        opMap.put(OpCode.IASTORE,new IASTORE());
        opMap.put(OpCode.CASTORE,new CASTORE());
        opMap.put(OpCode.ISTORE_0, new ISTORE_N(0));
        opMap.put(OpCode.ISTORE_1, new ISTORE_N(1));
        opMap.put(OpCode.ISTORE_2, new ISTORE_N(2));
        opMap.put(OpCode.ISTORE_3, new ISTORE_N(3));
        opMap.put(OpCode.LSTORE_0, new LSTORE_N(0));
        opMap.put(OpCode.LSTORE_1,new LSTORE_N(1));
        opMap.put(OpCode.LSTORE_2,new LSTORE_N(2));
        opMap.put(OpCode.LSTORE_3,new LSTORE_N(3));
//        opMap.put(OpCode.DSTORE_3, new DSTORE_N(3));
        opMap.put(OpCode.ASTORE_0, new ASTORE_N(0));
        opMap.put(OpCode.ASTORE_1, new ASTORE_N(1));
        opMap.put(OpCode.ASTORE_2, new ASTORE_N(2));
        opMap.put(OpCode.ASTORE_3, new ASTORE_N(3));
        opMap.put(OpCode.FSTORE_0, new FSTORE_N(0));
        opMap.put(OpCode.FSTORE_1, new FSTORE_N(1));
        opMap.put(OpCode.FSTORE_2, new FSTORE_N(2));
        opMap.put(OpCode.FSTORE_3, new FSTORE_N(3));
        opMap.put(OpCode.DSTORE_0, new DSTORE_N(0));
        opMap.put(OpCode.DSTORE_1, new DSTORE_N(1));
        opMap.put(OpCode.DSTORE_2, new DSTORE_N(2));
        opMap.put(OpCode.DSTORE_3, new DSTORE_N(3));

        opMap.put(OpCode.IF_ACMPEQ,new IF_ACMPEQ());
        opMap.put(OpCode.IF_ICMPNE,new IF_ICMPNE());
        opMap.put(OpCode.IF_ICMPLT,new IF_ICMPLT());
        opMap.put(OpCode.IF_ACMPNE,new IF_ACMPNE());
        opMap.put(OpCode.IF_ICMPEQ,new IF_ICMPEQ());
        opMap.put(OpCode.IF_ICMPGE,new IF_ICMPGE());
        opMap.put(OpCode.IF_ICMPGT,new IF_ICMPGT());
        opMap.put(OpCode.IF_ICMPLE,new IF_ICMPLE());
        opMap.put(OpCode.IFEQ,new IFEQ());
        opMap.put(OpCode.IFNE,new IFNE());
        opMap.put(OpCode.IFGE,new IFGE());
        opMap.put(OpCode.IFGT,new IFGT());
        opMap.put(OpCode.IFLE,new IFLE());
        opMap.put(OpCode.IFNULL,new IFNULL());
        opMap.put(OpCode.IFNONNULL,new IFNONNULL());
        opMap.put(OpCode.GOTO_, new GOTO());

//        opMap.put(OpCode.JSR, new JSR());
//        opMap.put(OpCode.RET, new RET());

//        opMap.put(OpCode.TABLESWITCH, new TABLESWITCH());
//        opMap.put(OpCode.LOOKUPSWITCH, new LOOKUPSWITCH());
        opMap.put(OpCode.IRETURN, new IRETURN());
        opMap.put(OpCode.LRETURN, new LRETURN());
        opMap.put(OpCode.FRETURN, new FRETURN());
        opMap.put(OpCode.DRETURN, new DRETURN());
        opMap.put(OpCode.ARETURN, new ARETURN());
        opMap.put(RETURN_, new RETURN());

        opMap.put(OpCode.GETSTATIC, new GETSTATIC());
        opMap.put(OpCode.PUTSTATIC, new PUTSTATIC());
        opMap.put(OpCode.GETFIELD, new GETFIELD());
        opMap.put(OpCode.PUTFIELD, new PUTFIELD());
        opMap.put(OpCode.INVOKEVIRTUAL, new INVOKE_VIRTUAL());
        opMap.put(OpCode.INVOKESPECIAL, new INVOKE_SPECIAL());
        opMap.put(OpCode.ARRAYLENGTH,new ARRAYLENGTH());
        opMap.put(OpCode.NEWARRAY,new NEWARRAY());
        opMap.put(OpCode.MULTIANEWARRAY,new MULTIANEWARRAY());
        opMap.put(OpCode.ANEWARRAY,new ANEWARRAY());
        opMap.put(OpCode.INSTANCEOF_,new INSTANCEOF());
        //TODO: ?????? invoke static
        opMap.put(OpCode.INVOKESTATIC,new INVOKE_STATIC());

        opMap.put(OpCode.INVOKEINTERFACE, new INVOKE_INTERFACE());
        opMap.put(OpCode.NEW_, new NEW());

    }

    public static Instruction decode(int opcode) {
        Instruction instruction = opMap.get(opcode);
        //System.out.println(instruction.toString());
        if (instruction == null) {
            throw new UnsupportedOperationException("Unsupported instruction " + String.format("0x%08X", opcode));
        }
        return instruction;
    }
}
