package com.cjburkey.cjscomputation.process;

import com.cjburkey.cjscomputation.Debug;
import com.cjburkey.cjscomputation.commandline.CommandLineCall;
import com.cjburkey.cjscomputation.computer.ComputerCore;

public class ProcessCommandLineCall extends Process {
    
    public final CommandLineCall call;
    public final String[] arguments;
    
    public ProcessCommandLineCall(CommandLineCall call, String[] arguments) {
        this.call = call;
        this.arguments = arguments;
    }
    
    public boolean onCall(ComputerCore host) {
        call.onCall(host, arguments);
        return true;
    }
    
}