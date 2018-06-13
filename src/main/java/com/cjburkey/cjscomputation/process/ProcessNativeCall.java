package com.cjburkey.cjscomputation.process;

import com.cjburkey.cjscomputation.Debug;
import com.cjburkey.cjscomputation.computer.ComputerCore;
import com.cjburkey.cjscomputation.natives.NativeCall;

public class ProcessNativeCall extends Process {
    
    public final NativeCall call;
    public final String[] arguments;
    
    public ProcessNativeCall(NativeCall call, String[] arguments) {
        this.call = call;
        this.arguments = arguments;
    }
    
    public boolean onCall(ComputerCore host) {
        if (arguments.length < call.minArguments || arguments.length > call.arguments.length) {
            return false;
        }
        Object[] args = new Object[arguments.length];
        for (int i = 0; i < arguments.length; i ++) {
            try {
                Object val = call.arguments[i].type.deserialize(arguments[i]);
                if (val == null) {
                    throw new Exception();
                }
                args[i] = val;
                continue;
            } catch (Exception e) {
            }
            // TODO: Failed to deserialize the value from a string
            Debug.log("Failed to deserialize token from string \"{}\"", arguments[i]);
            return false;
        }
        return call.onCall(host, args);
    }
    
}