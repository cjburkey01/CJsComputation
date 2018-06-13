package com.cjburkey.cjscomputation.natives;

import com.cjburkey.cjscomputation.computer.ComputerCore;

public class NativeCallSetFill extends NativeCall {
    
    public NativeCallSetFill() {
        super("sfl", 3, new NativeArgument[] {
            new NativeArgument("r", NativeTypes.INTEGER16),
            new NativeArgument("g", NativeTypes.INTEGER16),
            new NativeArgument("b", NativeTypes.INTEGER16),
        });
    }
    
    public boolean onCall(ComputerCore computer, Object[] parameters) {
        short r = (short) parameters[0];
        short g = (short) parameters[1];
        short b = (short) parameters[2];
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            return false;
        }
        computer.setFillColor((byte) (r - 128), (byte) (g - 128), (byte) (b - 128));    // -128 because bytes are -128 to 127, and 0 should be mapped to -128
        return true;
    }
    
}