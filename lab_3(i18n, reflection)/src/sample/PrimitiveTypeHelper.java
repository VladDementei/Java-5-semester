package sample;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class PrimitiveTypeHelper {

    private static Map<String, Pair<Class,Class>> builtInMap;
    static {
        builtInMap = new HashMap<>(); {
        builtInMap.put("int",new Pair<>(Integer.TYPE, Integer.class));
        builtInMap.put("long",new Pair<>(Long.TYPE, Long.class));
        builtInMap.put("double",new Pair<>(Double.TYPE, Double.class));
        builtInMap.put("float",new Pair<>(Float.TYPE, Float.class));
        builtInMap.put("bool",new Pair<>(Boolean.TYPE, Boolean.class));
        builtInMap.put("char",new Pair<>(Character.TYPE, Character.class));
        builtInMap.put("byte",new Pair<>(Byte.TYPE, Byte.class));
        builtInMap.put("void",new Pair<>(Void.TYPE, Void.class));
        builtInMap.put("short",new Pair<>(Short.TYPE, Short.class));
    }
    }

    public static boolean isPrimitiveType(String type){
        return builtInMap.containsKey(type);
    }

    public static Class getPrimitiveClass(String type){
        return builtInMap.get(type).getKey();
    }

    public static Class<?> getWrapperClass(String type){
        return builtInMap.get(type).getValue();
    }
}
