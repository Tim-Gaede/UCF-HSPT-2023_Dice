import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DiceData {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);
        String path = "C:/Users/lioba/IdeaProjects/WorldFinals/hspt/dice/dice";
        int counter = 7;
        for(int i = 0;i<12;i++){
            PrintWriter out = new PrintWriter(new File(path+threeDigit(counter)+".in"));
            int n = (int)(Math.random()*6)+999_995;
            int m = (int)(Math.random()*6)+9995;
            if(i<2){
                n = 1_000_000;
                m = 10_000;
            }
            out.print(n+" ");
            out.println(m);
            if(i<2){
                out.println("1");
                out.println("1000000");
            }
            for(int j = 0;j<m;j++){
                int rand = (int)(Math.random()*1000000)+1;
                out.println(rand);
            }
            out.flush();
            out.close();
            counter++;
        }
    }
    public static String threeDigit(int n){
        String s = Integer.toString(n);
        while(s.length()<3){
            s = "0"+s;
        }
        return s;
    }
}
