# Author : Juan Moscoso

'''

let prob(i, s) be the probability that that we land on a target value i with a fair s-sided die

we can jump to target value i from values i - s to i - 1 all with probability 1 / s because for 
each value from i - s to i - 1, we only have one side of our die that gets us to i and we have s sides
so we have i / s probablity of landing on that side. 

prob(i, s) = (prob(i - s, s) / s) + (prob(i - s + 1, s) / s ) + ... + (prob(i - 1, s) / s)
simplifying we get
prob(i, s) = prob(i - s, s) + prob(i - s + 1, s) + ... + prob(i - 1, s)
	      ---------------------------------------------------------
			 		s
									
However, looping through all from i - s to i - 1 for all i from 1 to our max target value
is too slow. This is O(s * max_value_of_t + v). This is about 10^12 operations which is way too slow.
However, we can speed this up with a prefix sum.

let dp(i, s) be the sum(prob(i, s)) for non-negative values <= i

We can calculate dp(i, s) using the same formula for prob(i, s)

dp(i, s) = dp(i - 1, s) + prob(i, s)
dp(i, s) = dp(i - 1, s) + (prob(i - s, s) + prob(i - s + 1, s) + ... + prob(i - 1, s)) / s

we know that 
prob(i - s, s) + prob(i - s + 1, s) + ... + prob(i - 1, s)) = (dp(i - 1, s) - dp(i - s - 1, s)) / s

dp(i, s) = dp(i - 1, s) + (dp(i - 1, s) - dp(i - s - 1, s)) / s

if we calculate dp(i, s) in increasing i, we can calculate each dp(i, s) for all i in O(1)

Now for every query now, prob(i, s) is equal to dp(i, s) - dp(i - 1, s)
by what we defined dp(i, s) to be earlier

dp(i, s) = prob(0, s) + prob(1, s) + ... + prob(i - 1, s) + prob(i, s)
dp(i - 1, s) = prob(0, s) + prob(1, s) + ... + prob(i - 1, s)

dp(i, s) - dp(i - 1, s) = (prob(0, s) + prob(1, s) + ... + prob(i - 1, s) + prob(i, s)) - (prob(0, s) + prob(1, s) + ... + prob(i - 1, s))
						= prob(0, s) - prob(0, s) + prob(1, s) - prob(1, s) + ... + prob(i - 1, s) - prob(i - 1, s) + prob(i, s)
cancel out like terms   = prob(i, s)

So now, we can solve our queries in O(1), this is assuming we calculate all values of dp(i, s)

'''
s, v = map(int, input().split())
MAX_VALUE = 10**6

# calculate dp for all possible target values
dp = [-1] * (MAX_VALUE + 1)
# the probability that we land on 0 is always 100%
dp[0] = 100
for i in range(1, MAX_VALUE + 1):
	dp[i] = dp[i - 1] + (dp[i - 1] / s)
	# edge case if i - s - 1 < 0 then we shouldnt subtract off dp(i - s - 1, s)
	# because we can reach i from all previous values
	if i - s - 1 >= 0:
		dp[i] -= dp[i - s - 1] / s

for i in range(v):
	target_value = int(input())
	# output prob(i, s) which we know is equal to 
	# dp(i, s) - dp(i - 1, s)
	print(dp[target_value] - dp[target_value - 1])
