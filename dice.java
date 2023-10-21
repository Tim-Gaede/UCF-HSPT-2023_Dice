// dice.java by Jacob Steinebronn
import java.util.*;

public class dice {
	// Add a little on the end there just to be safe :) 
	static final int MAXT = 1_000_010; 

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int s = scan.nextInt();
		// We'll calculate the probability for every possible value of t, so
		// answering a query is as simple as an array lookup
		double probs[] = new double[MAXT];
		// You always win if you need to get 0 points, cuz you're already there!
		probs[0] = 1;
		// This is the key to the solution. We'll maintain a sum over the last s values, 
		// each multiplied by 1/s. This is like saying for some value x, you can win by 
		// rolling a 1 from x-1 with probability 1/s, or rolling a 2 from x-2 with probability
		// 1/s, ... etc, till rolling a s from x-s with probability 1/s. Thus, if we maintain
		// a sum of the last s values times 1/s, then we've done it!
		double windowSum = 1.0 / s;
		for(int i = 1; i < MAXT; i++) {
			// Set the probability of winning to this window sum
			probs[i] = windowSum;
			// Add this prob times 1/s to our sum
			windowSum += probs[i] * 1.0 / s;
			// If it won't go back off the end of the array, subtract off the left end of the window
			// This is no longer needed for our sum. 
			if(i >= s) windowSum -= probs[i-s] * 1.0 / s;
		}

		// Now we can handle queries by simply looking them up in our answer array!
		int v = scan.nextInt();
		while(v-- > 0) {
			int t = scan.nextInt();
			System.out.println(probs[t]*100);
		}

		// Be kind to your operating system, close the scanner <3
		scan.close();
	}
}