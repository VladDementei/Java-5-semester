import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Snake {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bf.readLine());
        int index = 1;
        for (int i = 1 ;i < n+1; ++i){
            StringBuffer sb = new StringBuffer();
            if(i%2 == 1){
                for (int j = 0; j < n; j++){
                    sb.append(index);
                    if(j != n-1) {
                        sb.append(" ");
                    }
                    index++;
                }
            }else {
                index += n;
                for(int j = 0; j < n; j++){
                    index--;
                    sb.append(index);
                    if(j != n-1) {
                        sb.append(" ");
                    }
                }
                index += n;
            }
            if(i != n) {
                System.out.println(sb);
            }else {
                System.out.print(sb);
            }
        }
    }
}
