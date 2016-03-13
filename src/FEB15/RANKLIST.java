package FEB15;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

public class RANKLIST {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("in.txt")));

        int t = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < t; i++) {
            StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            int n = Integer.parseInt(stringTokenizer.nextToken());
            long s = Long.parseLong(stringTokenizer.nextToken());
            System.out.println(n - Math.round(Math.sqrt(2 * (s - n + 1))));
        }
    }

}
