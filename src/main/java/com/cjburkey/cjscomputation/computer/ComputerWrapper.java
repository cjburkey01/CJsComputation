package com.cjburkey.cjscomputation.computer;

public class ComputerWrapper<T extends ComputerCore> {
    
    public final Class<? extends ComputerCore> type;
    public final T computer;
    
    public ComputerWrapper(T computer) {
        type = computer.getClass();
        this.computer = computer;
    }
    
}