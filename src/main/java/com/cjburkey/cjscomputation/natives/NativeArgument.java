package com.cjburkey.cjscomputation.natives;

public final class NativeArgument {
    
    public final String name;
    public final NativeType<?> type;
    
    public NativeArgument(String name, NativeType<?> type) {
        this.name = name;
        this.type = type;
    }
    
}