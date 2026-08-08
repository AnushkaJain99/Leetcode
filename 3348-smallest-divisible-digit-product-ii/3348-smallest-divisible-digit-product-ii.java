class Solution {
    private static final int[] E2 = {0,0,1,0,2,0,1,0,3,0}; 
    private static final int[] E3 = {0,0,0,1,0,0,1,0,0,2};

    public String smallestNumber(String num, long t) {
        long a0=0,b0=0,c0=0,d0=0;
        long tt = t;
        while (tt % 2 == 0) { a0++; tt/=2; }
        while (tt % 3 == 0) { b0++; tt/=3; }
        while (tt % 5 == 0) { c0++; tt/=5; }
        while (tt % 7 == 0) { d0++; tt/=7; }
        if (tt != 1) return "-1";

        int n = num.length();
        int[] digits = new int[n];
        for (int i = 0; i < n; i++) digits[i] = num.charAt(i) - '0';

    
        boolean hasZero = false;
        for (int dig : digits) if (dig == 0) { hasZero = true; break; }
        if (!hasZero) {
            long A=0,B=0,C=0,D=0;
            for (int dig : digits) {
                A += E2[dig]; B += E3[dig];
                if (dig==5) C++;
                if (dig==7) D++;
            }
            if (A>=a0 && B>=b0 && C>=c0 && D>=d0) return num;
        }

        int firstZero = n;
        for (int i = 0; i < n; i++) if (digits[i]==0) { firstZero = i; break; }

        long[] preA = new long[n+1], preB = new long[n+1];
        long[] preC = new long[n+1], preD = new long[n+1];
        for (int i = 0; i < n; i++) {
            int dig = digits[i];
            int e2 = dig==0 ? 0 : E2[dig];
            int e3 = dig==0 ? 0 : E3[dig];
            preA[i+1] = preA[i] + e2;
            preB[i+1] = preB[i] + e3;
            preC[i+1] = preC[i] + (dig==5 ? 1 : 0);
            preD[i+1] = preD[i] + (dig==7 ? 1 : 0);
        }

        int start = Math.min(n-1, firstZero);
        for (int p = start; p >= 0; p--) {
            long pa=preA[p], pb=preB[p], pc=preC[p], pd=preD[p];
            long ra=Math.max(0,a0-pa), rb=Math.max(0,b0-pb);
            long rc=Math.max(0,c0-pc), rd=Math.max(0,d0-pd);
            for (int dig = digits[p]+1; dig <= 9; dig++) {
                long na = Math.max(0, ra - E2[dig]);
                long nb = Math.max(0, rb - E3[dig]);
                long nc = (dig==5) ? Math.max(0, rc-1) : rc;
                long nd = (dig==7) ? Math.max(0, rd-1) : rd;
                int rem = n-1-p;
                if (feasible(rem, na, nb, nc, nd)) {
                    int[] suffix = build(rem, na, nb, nc, nd);
                    StringBuilder sb = new StringBuilder();
                    sb.append(num, 0, p);
                    sb.append(dig);
                    for (int x : suffix) sb.append(x);
                    return sb.toString();
                }
            }
        }

        long Lmin = c0 + d0 + minExtra(a0, b0);
        int L = (int) Math.max(n+1, Lmin);
        int[] result = build(L, a0, b0, c0, d0);
        StringBuilder sb = new StringBuilder();
        for (int x : result) sb.append(x);
        return sb.toString();
    }

    private long minExtra(long a, long b) {
        if (a <= 0 && b <= 0) return 0;
        long best = Long.MAX_VALUE;
        long maxZ = Math.max(a, b);
        for (long z = 0; z <= maxZ; z++) {
            long needX = (a - z <= 0) ? 0 : ((a - z + 2) / 3); 
            long needY = (b - z <= 0) ? 0 : ((b - z + 1) / 2); 
            long tot = z + needX + needY;
            if (tot < best) best = tot;
        }
        return best;
    }

    private boolean feasible(long r, long a, long b, long c, long d) {
        if (c + d > r) return false;
        long m = r - c - d;
        return minExtra(a, b) <= m;
    }

    private int[] build(long r, long a, long b, long c, long d) {
        int[] res = new int[(int) r];
        for (int i = 0; i < r; i++) {
            long rem = r - i - 1;
            for (int dig = 1; dig <= 9; dig++) {
                long na = Math.max(0, a - E2[dig]);
                long nb = Math.max(0, b - E3[dig]);
                long nc = (dig==5 && c>0) ? c-1 : c;
                long nd = (dig==7 && d>0) ? d-1 : d;
                if (feasible(rem, na, nb, nc, nd)) {
                    res[i] = dig;
                    a = na; b = nb; c = nc; d = nd;
                    break;
                }
            }
        }
        return res;
    }
}