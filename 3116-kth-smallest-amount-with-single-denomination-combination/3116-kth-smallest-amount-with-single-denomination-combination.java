class Solution {
    public long findKthSmallest(int[] coins, int k) {
        int n = coins.length;
        int minCoin = coins[0];
        for (int c : coins) minCoin = Math.min(minCoin, c);
        
        long high = (long) minCoin * k;
        
        
        long[] lcmSub = new long[1 << n];
        lcmSub[0] = 1;
        for (int mask = 1; mask < (1 << n); mask++) {
            int lsb = Integer.numberOfTrailingZeros(mask);
            long prevLcm = lcmSub[mask & (mask - 1)];
            lcmSub[mask] = lcmCapped(prevLcm, coins[lsb], high);
        }
        
        long lo = 1, hi = high;
        while (lo < hi) {
            long mid = lo + (hi - lo) / 2;
            if (countUpTo(mid, lcmSub, n) >= k) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }
    
   
    private long countUpTo(long x, long[] lcmSub, int n) {
        long total = 0;
        int full = 1 << n;
        for (int mask = 1; mask < full; mask++) {
            long l = lcmSub[mask];
            if (l > x) continue;
            long term = x / l;
            if (Integer.bitCount(mask) % 2 == 1) {
                total += term;
            } else {
                total -= term;
            }
        }
        return total;
    }
  
    private long lcmCapped(long a, long b, long cap) {
        if (a > cap) return cap + 1;
        long g = gcd(a, b);
        long l = a / g;
        if (l > cap / b) return cap + 1; 
        return l * b;
    }
    
    private long gcd(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }
}