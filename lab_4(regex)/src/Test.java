import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
//    private static String text;
//    public static void main(String[] args) throws IOException {
//        StringBuffer sb = new StringBuffer();
//        try(BufferedReader reader = new BufferedReader(new FileReader(new File("code1.pas")))){
//            String line;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//                sb.append('\n');
//            }
//        }
//        text = sb.toString();
//        text = text.replaceAll("//(([^'\n]*'){2})*[^'\n]*\n", "\n");//выкинуть все однострочные комменты: // (четное число ') \n
//
//        System.out.println("text without one line comments ");
//        System.out.println(text);
//
//        String[] arr = text.split("((?=\\{[^$])|(?<=\\}))");// разрез перед { и после} ( не режем до {$ т.к. это директива компилятора)
//        List<String> textParts = Arrays.stream(arr).filter(elem -> !elem.matches("\n")).collect(Collectors.toList());//удаляем пустые строки которые полуичились из-за разрезки
//        StringBuffer answer = new StringBuffer();
//        for (int i = 0; i < textParts.size(); i++) {
//            if (answer.toString().matches("(([^']*'){2})*[^']*")) {//если до этого было четные число ' (подозрительно на коммент)
//                if (textParts.get(i).matches("\\{[^\\}]*")) {// если начинается на { но заканчивается не }
//                    while (!textParts.get(i).matches("([^\\}]*\\})")) {// пропускаем пока не придет } случай когда в блочном комментарии много {
//                        i++;
//                    }
//                } else if (!textParts.get(i).matches("\\{[^\\}]*\\}")) {// не начинается на { и не заканчивается на} - точно не коммент
//                    answer.append(textParts.get(i));
//                }
//            } else {//было нечетное число ' значит точно не комментарий
//                answer.append(textParts.get(i));
//            }
//        }
//
//        System.out.println("text without {} comments");
//        System.out.println(answer);
//
//        arr = answer.toString().split("((?=(\\(\\*))|(?<=(\\*\\))))");//разрез перед (* и после *)
//        answer = new StringBuffer();
//
//        textParts = Arrays.stream(arr).filter(elem -> !elem.matches("\n")).collect(Collectors.toList());//удаляем пустые строки которые полуичились из-за разрезки
//        for (int i = 0; i < textParts.size(); i++) {
//            if (answer.toString().matches("(([^']*'){2})*[^']*")) {//если до этого было четные число ' (подозрительно на коммент)
//                //if (list.get(i).matches("\\(\\*[^(\\*\\))]*")) {
//                if (textParts.get(i).matches("\\(\\*(.*[\r\t\n]*)*") && !textParts.get(i).matches("(.*[\r\t\n]*)*\\*\\)")) {// если начинается на (* но заканчивается не *)
//                    //while (!list.get(i).matches("([^(\\*\\))]*(\\*\\)))")) {
//                    while (!textParts.get(i).matches("(.*[\r\t\n]*)*\\*\\)")){// пропускаем пока не придет *) случай когда в блочном комментарии много (*
//                        i++;
//                    }
//                //} else if (!list.get(i).matches("\\(\\*[^(\\*\\))]*(\\*\\))")) {
//                } else if (!textParts.get(i).matches("\\(\\*(.*[\r\t\n]*)*\\*\\)")) {// не начинается на (* и не заканчивается на *) - точно не коммент
//                    answer.append(textParts.get(i));
//                }
//            } else {//было нечетное число ' значит точно не комментарий
//                answer.append(textParts.get(i));
//            }
//        }
//
//        System.out.println("text without (**) comments");
//        System.out.println(answer);
//    }
}
