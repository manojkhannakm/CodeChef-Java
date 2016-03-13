package FEB15;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

public class STFM {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("in.txt")));

        StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
        int n = Integer.parseInt(stringTokenizer.nextToken()),
                m = Integer.parseInt(stringTokenizer.nextToken());

        long[] p = new long[n];
        stringTokenizer = new StringTokenizer(bufferedReader.readLine());
        for (int i = 0; i < n; i++) {
            p[i] = Long.parseLong(stringTokenizer.nextToken());
        }

        Arrays.sort(p);

        long i = 1, fi = 1 % m, si = 1 % m, sum = 0;
        for (int j = 0; j < n; j++) {
            long pj = p[j];
            for (long k = i + 1; k <= pj && fi > 0; k++) {
                fi = fi * k % m;
                si = (si + k * fi % m) % m;
            }

            i = pj;

            BigInteger pjBI = BigInteger.valueOf(pj);
            sum = (sum + si +
                    pjBI.add(BigInteger.ONE).multiply(pjBI).divide(BigInteger.valueOf(2)).multiply(pjBI).mod(BigInteger.valueOf(m)).longValue()) % m;
        }

        System.out.println(sum % m);
    }

}
