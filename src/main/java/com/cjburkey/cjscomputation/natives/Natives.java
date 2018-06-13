package com.cjburkey.cjscomputation.natives;

import java.util.HashMap;
import java.util.Map;

public class Natives {
    
    private static final Map<String, NativeCall> natives = new HashMap<>();
    
    public static boolean addNative(NativeCall nativeCall) {
        if (natives.containsKey(nativeCall.name)) {
            return false;
        }
        natives.put(nativeCall.name, nativeCall);
        return true;
    }
    
    public static NativeCall getNative(String name) {
        return natives.getOrDefault(name, null);
    }
    
}