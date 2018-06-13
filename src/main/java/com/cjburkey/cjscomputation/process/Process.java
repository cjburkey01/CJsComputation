package com.cjburkey.cjscomputation.process;

import com.cjburkey.cjscomputation.computer.ComputerCore;

public abstract class Process {
    
    public abstract boolean onCall(ComputerCore host);
    
}