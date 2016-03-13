package LTIME20;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * @author Manoj Khanna
 */

public class LCH15JEF {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("in.txt")));

        int t = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < t; i++) {
            String line = bufferedReader.readLine();
            String[] strings = line.split(" ", 2);
            BigInteger m = new BigInteger(strings[0]);

            BigInteger res = BigInteger.ONE;
            String[] a = strings[1].split("(?<=\\d)\\*(?=\\d)");
            for (String ai : a) {
                String[] xy = ai.split("\\*\\*");
                res = res.multiply(new BigInteger(xy[0]).modPow(new BigInteger(xy[1]), m))
                        .mod(m);
            }

            System.out.println(res);
        }
    }

}
