package LTIME20;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Manoj Khanna
 */

public class LCH15JAB {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("in.txt")));

        int t = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < t; i++) {
            char[] chars = bufferedReader.readLine().toCharArray();
            int[] a = new int[26];
            for (char c : chars) {
                ++a[c - 'a'];
            }

            boolean flag = false;
            for (int j = 0; j < 26; j++) {
                int sum = 0;
                for (int k = 0; k < 26; k++) {
                    if (k == j) {
                        continue;
                    }

                    sum += a[k];
                }

                if (a[j] == sum) {
                    flag = true;
                    break;
                }
            }

            System.out.println(flag ? "YES" : "NO");
        }
    }

}
