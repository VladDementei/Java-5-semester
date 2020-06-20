

//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.util.StringTokenizer;
//
//public class Erd {
//    public static void main(String[] args) throws Exception {
//        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
//        StringTokenizer line = new StringTokenizer(bf.readLine());
//        double x = Double.parseDouble(line.nextToken());
//        int k = Integer.parseInt(line.nextToken());
//        double rez = 1;
//        for (int i = 1; i < k + 1; i++) {
//            rez *= x/i;
//        }
//        System.out.print(rez);
//    }
//}
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
class Main2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int last = sc.nextInt();
        int n = ((last - 5) / 7) + 1;
        System.out.print(n * (5 + last) / 2);
    }
}

//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.Scanner;
//import java.util.StringTokenizer;
//
//class Main2 {
//
//    public static void main(String[] args) throws Exception{
//        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
//        StringTokenizer sk = new StringTokenizer(bf.readLine());
//        int n = Integer.parseInt(sk.nextToken());
//        int[] arr = new int[n];
//        for(int i  = 0; i < n; i++){
//            arr[i] = Integer.parseInt(sk.nextToken());
//        }
//        int x = Integer.parseInt(sk.nextToken());
//        int count = 0;
//        for(int i = 0; i < n; i++){
//            if(arr[i] == x){
//                System.out.print(i+1 + " ");
//                count++;
//            }
//        }
//        if(count == 0){
//            System.out.println("-1");
//        }
//    }
//}
