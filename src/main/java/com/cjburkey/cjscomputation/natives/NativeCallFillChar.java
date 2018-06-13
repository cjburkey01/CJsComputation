package com.cjburkey.cjscomputation.natives;

import com.cjburkey.cjscomputation.computer.ComputerCore;

public class NativeCallFillChar extends NativeCall {
    
    public NativeCallFillChar() {
        super("fch", 2, new NativeArgument[] {
            new NativeArgument("char", NativeTypes.CHAR),
            new NativeArgument("move", NativeTypes.BOOLEAN),
        });
    }
    
    public boolean onCall(ComputerCore computer, Object[] parameters) {
        char character = (char) parameters[0];
        boolean move = (boolean) parameters[1];
        computer.fillCharacter(character, move);
        return true;
    }
    
}