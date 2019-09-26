package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ParseUtils {

    public static String getClassName(String signature){
        StringTokenizer stringTokenizer = new StringTokenizer(signature, "(,) ");
        String fullFunctionName = stringTokenizer.nextToken();
        return fullFunctionName.substring(0, fullFunctionName.lastIndexOf("."));
    }

    public static String getFunctionName(String signature){
        StringTokenizer stringTokenizer = new StringTokenizer(signature, "(,) ");
        String fullFunctionName = stringTokenizer.nextToken();
        return fullFunctionName.substring(fullFunctionName.lastIndexOf(".")+1);
    }

    public static List<Class<?>> getParamsTypes(String signature){
        StringTokenizer stringTokenizer = new StringTokenizer(signature, "(,) ");
        stringTokenizer.nextToken();
        List<Class<?>> list = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()){
            String token = stringTokenizer.nextToken();
            if(PrimitiveTypeHelper.isPrimitiveType(token)) {
                list.add(PrimitiveTypeHelper.getPrimitiveClass(token));
            }else{
                try {
                    list.add(Class.forName(token));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}