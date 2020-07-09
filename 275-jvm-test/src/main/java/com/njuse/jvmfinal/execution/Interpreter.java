package com.njuse.jvmfinal.execution;

import com.njuse.jvmfinal.instructions.base.Instruction;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.runtime.JThread;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.struct.Slot;

import java.io.File;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Interpreter {
    private static ByteBuffer codeReader;

    public static void interpret(JThread thread) {
        initCodeReader(thread);
        loop(thread);
        return;
    }

    /**
     * This method set the code reader according to topFrame
     * When topFrame changes, this method should be called
     */
    private static void initCodeReader(JThread thread) {
        byte[] code = thread.getTopFrame().getMethod().getCode();
        codeReader = ByteBuffer.wrap(code);
        int nextPC = thread.getTopFrame().getNextPC();
        codeReader.position(nextPC);
    }

    private static void loop(JThread thread) {
        while (true) {
            StackFrame oriTop = thread.getTopFrame();
            Method method = oriTop.getMethod();
            if (!method.isParsed()) {
                method.parseCode();
            }
            //set the reader's position to nextPC
            //System.out.println("next pc="+oriTop.getNextPC());
            codeReader.position(oriTop.getNextPC());
            //fetch and decode
            int opcode = codeReader.get() & 0xff;
            Instruction instruction = Decoder.decode(opcode);
            instruction.fetchOperands(codeReader);
            //System.out.println(oriTop.getMethod().getName()+" "+instruction.toString()+" "+codeReader.position());
            //set nextPC to reader's position
            int nextPC = codeReader.position();
            oriTop.setNextPC(nextPC);
            instruction.execute(oriTop);
           //System.out.println(oriTop.getMethod().getName()+" "+instruction.toString()+" "+codeReader.position());
//            System.out.println(thread.getTopFrame().getMethod().getName()+" "+thread.getTopFrame().getOperandStack().getTop());
            //check whether there's a new frame
            //and whether there's more frame to exec

            StackFrame newTop = thread.getTopFrame();

            if (newTop == null) {
                return ;
            }
            //System.out.println("newFrame="+newTop.getMethod().getName()+" ordFrame="+oriTop.getMethod().getName());
            if (oriTop != newTop) {
                initCodeReader(thread);
            }
        }

    }

}
