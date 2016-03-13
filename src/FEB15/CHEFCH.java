package FEB15;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Manoj Khanna
 */

public class CHEFCH {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("in.txt")));

        int t = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < t; i++) {
            String line = bufferedReader.readLine();
            int a = 0, b = 0;
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                if (j % 2 == 0) {
                    if (c != '-') {
                        a++;
                    } else {
                        b++;
                    }
                } else {
                    if (c != '+') {
                        a++;
                    } else {
                        b++;
                    }
                }
            }

            System.out.println(Math.min(a, b));
        }
    }

}
