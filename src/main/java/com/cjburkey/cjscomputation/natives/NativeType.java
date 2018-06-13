package com.cjburkey.cjscomputation.natives;

public abstract class NativeType<T> {
    
    public final Class<T> type;
    
    public NativeType(Class<T> type) {
        this.type = type;
    }
    
    public abstract String serialize(T object);
    public abstract T deserialize(String object) throws Exception;
    
}