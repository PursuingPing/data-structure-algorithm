import java.util.Arrays;

public class Suger {
    public static void main(String[] args) {
        //Scanner in = new Scanner(System.in);
        //ArrayList<Integer> inList = new ArrayList<>();
        //while(in.hasNext()) {
        //    inList.add(in.nextInt());
        //}
        //int a = in.nextInt();
        //System.out.println(a);
        //System.out.println("Hello World!");

        //System.out.println(solution(inList.toArray()));
        int[] in = new int[]{1, 8, 3, 1, 5};
        System.out.println(solution(in));
        int[] in2 = new int[]{2, 3, 2};
        int max = Math.max(solution2(Arrays.copyOfRange(in2, 0, in2.length - 1)),
                solution2(Arrays.copyOfRange(in2, 1, in2.length)));
        System.out.println(max);
    }

    public static int solution(int[] in) {
        if (in == null || in.length == 0) {
            return 0;
        }
        int n = in.length;
        if (n == 1) return in[0];
        int[] dp = new int[n];
        dp[0] = in[0];
        dp[1] = Math.max(dp[0], in[1]);
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + in[i]);
        }
        return dp[n - 1];
    }

    public static int solution2(int[] in) {
        Arrays.stream(in).forEach(System.out::println);
        if (in == null || in.length == 0) {
            return 0;
        }
        int n = in.length;
        if (n == 1) return in[0];
        int pre = in[0];
        int cur = Math.max(in[0], in[1]);
        for (int i = 2; i < n; i++) {
            int temp = cur;
            cur = Math.max(cur, pre + in[i]);
            pre = temp;
        }
        return cur;
    }
}

