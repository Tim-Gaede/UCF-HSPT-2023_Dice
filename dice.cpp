// UCF HSPT 2023 - Dencker's Dice?
// Author - Tyler Marks

// Includes all important libraries
#include <bits/stdc++.h>
// Makes it so we don't have to keep calling "std::"
using namespace std;

// Max Value of the problem we can be asked to solve for
const int MAXVAL = 1'000'000;

// General Solution Idea:
//      For the target t and number of sides d, the probability of reaching that
//      target is p_t = r_1 * p_(t-1) + r_2 * p_(t-2) + ... + r_d * p_(t-min(d, t))
//      where r_i is the probability of rolling i and p_t is the probanility
//      of reaching value t. 

//      Idea 1: Solve for t = 0 to MAXVAL. Since the current t value only depends
//      on t values less than it, we can use dynamic programming. Loop through
//      each possible t value. For the current t value, loop from the min of 0 and t-d
//      up to t-1 and take that p value, multiply by the probability of rolling i and add to p_t.
//      This is however too slow since t can be up to 10^6 and d can be up to 10^6.

//      Since we are working with a fair die, p_1 == p_2 == ... == p_d.
//      We will represent this value as p_roll which equals 1 / d.
//      So p_t = the sum of p_roll * p_(t-i) where i is in the range 1 to min(d, t).
//      We use min(d, t) to make sure (t-i) does not go negative.
//      Since we have the same constant multiplied by a series of terms being added 
//      together, we can factor out this constant.
//      This gives us p_t = p_roll * (p_(t-1) + p_(t-2) + ... + p_(t-min(d, t)))
//      So we have a constant times a series of contiguous terms being added together.

//      Idea 2: We now have a contiguous sum of elements. We can use a prefix sum to
//      spped up our dp. As we loop through t, also expand the prefix sum. Now instead
//      of looping through each possible dice roll, we can take the sum from the prefix
//      sum and use that instead.

int main() {

    // Scan in the number of sides and queries.
    int sides, queries; 
    cin >> sides >> queries;

    // Initialize DP and Prefix Sum arrays
    vector<double> dp(MAXVAL + 1, 0);
    vector<double> prefixSums(MAXVAL+1, 0);

    // The probability of rolling a target of 0 is 100%
    dp[0] = prefixSums[0] = 1;

    // Loop through each target
    for(int cur = 1; cur <= MAXVAL; cur++) {

        // Determine how far back into our prefix sum we must go
        double minState = max(0, cur - sides);

        // Get the sum from 0 to the current target minus 1
        double prevStates = prefixSums[cur-1];

        // If our furthest back is greater than 0, subtract out 0 to minstate - 1
        if(minState > 0) prevStates -= prefixSums[minState - 1];

        // Update the current probability
        dp[cur] = 1.0 / sides * prevStates;

        // Update the prefix sum.
        prefixSums[cur] = prefixSums[cur-1] + dp[cur];
    }

    // Go through each query target and print out its probability
    for(int curQuery = 0; curQuery < queries; curQuery++) {
        int target; cin >> target;
        cout << setprecision(10) << fixed << 100.0 * dp[target] << endl;
    }
}
