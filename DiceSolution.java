import java.util.*;
import java.io.*;
public class DiceSolution {
    public static void main(String[] args) throws FileNotFoundException {
        File dir = new File("./");
        File[] directoryListing = dir.listFiles();
        String path = "./dice";
        int counter = 1;
        if(directoryListing!=null){
            for (File child:directoryListing){
                if(!child.getName().endsWith("in"))continue;
                PrintWriter out = new PrintWriter(new File(path+threeDigit(counter)+".out"));
                Scanner scan = new Scanner(child);
                int n = scan.nextInt();
                int m = scan.nextInt();
                int[] targets = new int[m];
                for(int i = 0;i<m;i++){
                    targets[i] = scan.nextInt();
                }
                double[] ans = solve(n,m,targets);
                for(int i = 0;i<m;i++){
                    out.printf("%.15f\n", ans[targets[i]]*100);
                }
                counter++;
                out.flush();
                out.close();
            }
        }else{
            System.out.println("it's alright try again!");
        }
    }
    public static double[] solve(int n, int m, int[] targets) {
        int lim = 1_000_001;
        double[] dp = new double[lim];
        Arrays.fill(dp,-1);
        dp[0] = 1;
        double sum = dp[0];
        for(int i = 1;i<lim;i++){
            if(i>n){
                sum-=dp[i-n];
            }
            dp[i] = sum/n;
            sum+=dp[i];
        }
        return dp;
    }
    public static String threeDigit(int n){
        String s = Integer.toString(n);
        while(s.length()<3){
            s = "0"+s;
        }
        return s;
    }

}
