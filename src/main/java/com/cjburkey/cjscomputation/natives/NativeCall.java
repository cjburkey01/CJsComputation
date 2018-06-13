package com.cjburkey.cjscomputation.natives;

import com.cjburkey.cjscomputation.computer.ComputerCore;

public abstract class NativeCall {
    
    public final String name;
    public final int minArguments;
    public final NativeArgument[] arguments;
    
    public NativeCall(String name, int minArguments, NativeArgument... arguments) {
        this.name = name;
        this.minArguments = minArguments;
        this.arguments = arguments;
    }
    
    // Returns whether or not the arguments were valid
    public abstract boolean onCall(ComputerCore computer, Object[] parameters);
    
}