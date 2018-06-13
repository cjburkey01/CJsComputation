package com.cjburkey.cjscomputation.natives;

public class NativeTypes {
    
    public static final NativeType<Byte> INTEGER8 = new NativeType<Byte>(Byte.class) {
        public String serialize(Byte object) {
            return object.toString();
        }
        public Byte deserialize(String object) throws Exception {
            return Byte.parseByte(object);
        }
    };
    
    public static final NativeType<Short> INTEGER16 = new NativeType<Short>(Short.class) {
        public String serialize(Short object) {
            return object.toString();
        }
        public Short deserialize(String object) throws Exception {
            return Short.parseShort(object);
        }
    };
    
    public static final NativeType<Integer> INTEGER32 = new NativeType<Integer>(Integer.class) {
        public String serialize(Integer object) {
            return object.toString();
        }
        public Integer deserialize(String object) throws Exception {
            return Integer.parseInt(object);
        }
    };
    
    public static final NativeType<Long> INTEGER64 = new NativeType<Long>(Long.class) {
        public String serialize(Long object) {
            return object.toString();
        }
        public Long deserialize(String object) throws Exception {
            return Long.parseLong(object);
        }
    };
    
    public static final NativeType<Float> FLOAT32 = new NativeType<Float>(Float.class) {
        public String serialize(Float object) {
            return object.toString();
        }
        public Float deserialize(String object) throws Exception {
            return Float.parseFloat(object);
        }
    };
    
    public static final NativeType<Double> FLOAT64 = new NativeType<Double>(Double.class) {
        public String serialize(Double object) {
            return object.toString();
        }
        public Double deserialize(String object) throws Exception {
            return Double.parseDouble(object);
        }
    };
    
    public static final NativeType<Character> CHAR = new NativeType<Character>(Character.class) {
        public String serialize(Character object) {
            return object.toString();
        }
        public Character deserialize(String object) throws Exception {
            if (object.length() > 0) {
                return object.toCharArray()[0];
            }
            throw new Exception("Failed to parse character, input was empty");
        }
    };
    
    public static final NativeType<String> STR = new NativeType<String>(String.class) {
        public String serialize(String object) {
            return object;
        }
        public String deserialize(String object) throws Exception {
            return object;
        }
    };
    
    public static final NativeType<Boolean> BOOLEAN = new NativeType<Boolean>(Boolean.class) {
        public String serialize(Boolean object) {
            return object.toString();
        }
        public Boolean deserialize(String object) throws Exception {
            return Boolean.parseBoolean(object);
        }
    };
    
}