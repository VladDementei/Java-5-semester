import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


class Polsc {
    private static String operators = "+-*/";
    private static String delimiters = operators;
    private static boolean Flag = true;

    private static boolean isDelimiter(String token) {
        if (token.length() != 1) return false;
        for (int i = 0; i < delimiters.length(); i++) {
            if (token.charAt(0) == delimiters.charAt(i)) return true;
        }
        return false;
    }

    private static boolean isOperator(String token) {
        if (token.equals("oper")) return true;
        for (int i = 0; i < operators.length(); i++) {
            if (token.charAt(0) == operators.charAt(i)) return true;
        }
        return false;
    }

    private static int priority(String token) {
        if (token.equals("+") || token.equals("-")) return 2;
        if (token.equals("*")) return 3;
        return 4;
    }

    public static List<String> parse(String infix) {
        List<String> postfix = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        StringTokenizer tokenizer = new StringTokenizer(infix, delimiters, true);
        String prev = "";
        String curr = "";
        while (tokenizer.hasMoreTokens()) {
            curr = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens() && isOperator(curr)) {
                Flag = false;
                return postfix;
            }
            if (curr.equals(" ")) continue;
            else if (isDelimiter(curr)) {

                if (curr.equals("-") && (prev.equals("") || (isDelimiter(prev) && !prev.equals(")")))) {
                    curr = "oper";
                } else {
                    while (!stack.isEmpty() && (priority(curr) <= priority(stack.peek()))) {
                        postfix.add(stack.pop());
                    }

                }
                stack.push(curr);

            } else {
                postfix.add(curr);
            }
            prev = curr;
        }

        while (!stack.isEmpty()) {
            if (isOperator(stack.peek())) postfix.add(stack.pop());
            else {
                Flag = false;
                return postfix;
            }
        }
        return postfix;
    }

    public static Integer calc(List<String> postfix) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (String x : postfix) {

            if (x.equals("+")) stack.push(stack.pop() + stack.pop());
            else if (x.equals("-")) {
                Integer b = stack.pop(), a = stack.pop();
                stack.push(a - b);
            } else if (x.equals("*")) stack.push(stack.pop() * stack.pop());
            else if (x.equals("oper")) stack.push(-stack.pop());
            else stack.push(Integer.valueOf(x));
        }
        return stack.pop();
    }

    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String s = bf.readLine();
        s = s.replaceAll("---", "-");
        s = s.replaceAll("\\+\\+\\+", "+");
        s = s.replaceAll("\\*\\*\\*", "*");
        List<String> expression = parse(s);
        boolean flag = Flag;
        if (flag) {
            System.out.print(calc(expression));
        }
    }
}
