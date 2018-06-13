package com.cjburkey.cjscomputation.commandline;

import com.cjburkey.cjscomputation.computer.ComputerCore;

public abstract class CommandLineCall {
    
    public final int minArguments;
    public final boolean limitArgs;
    public final String[] arguments;
    
    public CommandLineCall(int minArguments, boolean limitArgs, String... arguments) {
        this.minArguments = minArguments;
        this.limitArgs = limitArgs;
        this.arguments = arguments;
    }
    
    public abstract String getName();
    public abstract void onCall(ComputerCore computer, String[] parameters);
    
}