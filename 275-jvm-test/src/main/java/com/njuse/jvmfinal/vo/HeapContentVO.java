package com.njuse.jvmfinal.vo;

import com.njuse.jvmfinal.runtime.Vars;
import com.njuse.jvmfinal.runtime.struct.ArrayObject;
import com.njuse.jvmfinal.runtime.struct.JObject;
import com.njuse.jvmfinal.runtime.struct.NonArrayObject;
import com.njuse.jvmfinal.runtime.struct.NullObject;
import com.njuse.jvmfinal.runtime.struct.array.*;

import java.util.Arrays;

public class HeapContentVO {
    public HeapContentVO() {
    }

    public String getInfo(Vars slots, String type, int slotID) {
        byte var5 = -1;
        switch(type.hashCode()) {
            case -1325958191:
                if (type.equals("double")) {
                    var5 = 7;
                }
                break;
            case 104431:
                if (type.equals("int")) {
                    var5 = 2;
                }
                break;
            case 3039496:
                if (type.equals("byte")) {
                    var5 = 0;
                }
                break;
            case 3052374:
                if (type.equals("char")) {
                    var5 = 3;
                }
                break;
            case 3327612:
                if (type.equals("long")) {
                    var5 = 6;
                }
                break;
            case 64711720:
                if (type.equals("boolean")) {
                    var5 = 4;
                }
                break;
            case 97526364:
                if (type.equals("float")) {
                    var5 = 5;
                }
                break;
            case 109413500:
                if (type.equals("short")) {
                    var5 = 1;
                }
        }

        switch(var5) {
            case 0:
            case 1:
            case 2:
                return "" + slots.getInt(slotID);
            case 3:
                return "" + (char)slots.getInt(slotID);
            case 4:
                return slots.getInt(slotID) == 0 ? "false" : "true";
            case 5:
                return "" + slots.getFloat(slotID);
            case 6:
                return "" + slots.getLong(slotID);
            case 7:
                return "" + slots.getDouble(slotID);
            default:
                JObject obj = slots.getObjectRef(slotID);
                if (obj instanceof NullObject) {
                    return "null";
                } else if (obj instanceof NonArrayObject) {
                    return slots.getObjectRef(slotID).getId() + "@" + obj.getClazz().getName();
                } else if (obj instanceof ArrayObject) {
                    return this.getArrayContent((ArrayObject)obj);
                } else {
                    throw new RuntimeException("Invalid field type");
                }
        }
    }

    public String getArrayContent(ArrayObject obj) {
        String type = obj.getType();
        byte var4 = -1;
        switch(type.hashCode()) {
            case 2887:
                if (type.equals("[B")) {
                    var4 = 1;
                }
                break;
            case 2888:
                if (type.equals("[C")) {
                    var4 = 2;
                }
                break;
            case 2889:
                if (type.equals("[D")) {
                    var4 = 7;
                }
            case 2890:
            case 2892:
            case 2893:
            case 2896:
            case 2897:
            case 2898:
            case 2899:
            case 2900:
            case 2901:
            case 2902:
            case 2903:
            case 2905:
            case 2906:
            case 2907:
            case 2908:
            case 2909:
            case 2910:
            default:
                break;
            case 2891:
                if (type.equals("[F")) {
                    var4 = 6;
                }
                break;
            case 2894:
                if (type.equals("[I")) {
                    var4 = 4;
                }
                break;
            case 2895:
                if (type.equals("[J")) {
                    var4 = 5;
                }
                break;
            case 2904:
                if (type.equals("[S")) {
                    var4 = 3;
                }
                break;
            case 2911:
                if (type.equals("[Z")) {
                    var4 = 0;
                }
        }

        switch(var4) {
            case 0:
                return Arrays.toString(((BooleanArrayObject)obj).getArray());
            case 1:
                return Arrays.toString(((ByteArrayObject)obj).getArray());
            case 2:
                return Arrays.toString(((CharArrayObject)obj).getArray());
            case 3:
                return Arrays.toString(((ShortArrayObject)obj).getArray());
            case 4:
                return Arrays.toString(((IntArrayObject)obj).getArray());
            case 5:
                return Arrays.toString(((LongArrayObject)obj).getArray());
            case 6:
                return Arrays.toString(((FloatArrayObject)obj).getArray());
            case 7:
                return Arrays.toString(((DoubleArrayObject)obj).getArray());
            default:
                assert obj instanceof RefArrayObject;

                if (obj.getType().lastIndexOf(91) == 0) {
                    return Arrays.toString(((RefArrayObject)obj).getArray());
                } else {
                    StringBuilder ret = new StringBuilder();

                    for(int i = 0; i < obj.getLen(); ++i) {
                        ret.append(this.getArrayContent((ArrayObject)((RefArrayObject)obj).getArray()[i]));
                        ret.append(",");
                    }

                    return "[" + ret.substring(0, ret.length() - 1) + "]";
                }
        }
    }
}
