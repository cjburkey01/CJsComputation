package com.cjburkey.cjscomputation.natives;

import com.cjburkey.cjscomputation.computer.ComputerCore;

public class NativeCallSetCursor extends NativeCall {
    
    public NativeCallSetCursor() {
        super("cur", 2, new NativeArgument[] {
            new NativeArgument("x", NativeTypes.INTEGER16),
            new NativeArgument("y", NativeTypes.INTEGER16),
        });
    }
    
    public boolean onCall(ComputerCore computer, Object[] parameters) {
        short x = (short) parameters[0];
        short y = (short) parameters[1];
        computer.setCursor(x, y);
        return true;
    }
    
}