package FEB15;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

public class CHEFEQ {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("in.txt")));

        int t = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < t; i++) {
            int n = Integer.parseInt(bufferedReader.readLine());

            StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            int[] piles = new int[n];
            for (int j = 0; j < n; j++) {
                piles[j] = Integer.parseInt(stringTokenizer.nextToken());
            }

            HashMap<Integer, Integer> pileCountMap = new HashMap<>();
            for (int pile : piles) {
                Integer pileCount = pileCountMap.get(pile);
                if (pileCount == null) {
                    pileCount = 0;
                }

                pileCountMap.put(pile, pileCount + 1);
            }

            int pileCount = 0;
            for (Map.Entry<Integer, Integer> entry : pileCountMap.entrySet()) {
                Integer value = entry.getValue();
                if (entry.getKey() > 0 && value > pileCount) {
                    pileCount = value;
                }
            }

            System.out.println(n - pileCount);
        }
    }

}
