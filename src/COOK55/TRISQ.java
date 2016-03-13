package COOK55;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Manoj Khanna
 */

public class TRISQ {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("in.txt")));

        int t = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < t; i++) {
            int b = Integer.parseInt(bufferedReader.readLine()),
                    n = b / 2 - 1;
            System.out.println(n * (n + 1) / 2);
        }
    }

}
