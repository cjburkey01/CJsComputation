package com.cjburkey.cjscomputation.commandline;

import java.util.Arrays;
import com.cjburkey.cjscomputation.Debug;
import com.cjburkey.cjscomputation.computer.ComputerCore;
import com.cjburkey.cjscomputation.natives.NativeCall;
import com.cjburkey.cjscomputation.natives.Natives;
import com.cjburkey.cjscomputation.process.ProcessNativeCall;

public class CommandLineNativeCall extends CommandLineCall {
    
    public CommandLineNativeCall() {
        super(1, false, new String[0]);
    }
    
    public String getName() {
        return "native";
    }
    
    public void onCall(ComputerCore computer, String[] parameters) {
        Debug.log("Native \"{}\" called", parameters[0]);
        
        String name = parameters[0];
        NativeCall nativeCall = Natives.getNative(name);
        if (nativeCall == null) {
            // TODO: NATIVE NOT FOUND
            Debug.log("Native call not found \"{}\"", name);
        }
        if ((parameters.length - 1) < nativeCall.minArguments || (parameters.length - 1) > nativeCall.arguments.length) {
            // TODO: INVALID ARGUMENTS
            Debug.log("Invalid native arguments: {}", Arrays.toString(parameters));
        }
        String[] args = (parameters.length > 1) ? Arrays.copyOfRange(parameters, 1, parameters.length) : new String[0];
        computer.appendProcess(new ProcessNativeCall(nativeCall, args));
    }
    
}