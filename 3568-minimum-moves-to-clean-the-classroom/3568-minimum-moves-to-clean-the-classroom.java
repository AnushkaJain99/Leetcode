import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length, n = classroom[0].length();
        int sr = 0, sc = 0, k = 0;

        int[][] id = new int[m][n];
        for (int[] row : id) Arrays.fill(row, -1);

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                char ch = classroom[r].charAt(c);

                if (ch == 'S') {
                    sr = r;
                    sc = c;
                } else if (ch == 'L') {
                    id[r][c] = k++;
                }
            }
        }

        int fullMask = (1 << k) - 1;
        int[][] best = new int[m * n][1 << k];

        for (int[] row : best) Arrays.fill(row, -1);

        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{sr, sc, 0, energy});
        best[sr * n + sc][0] = energy;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        int moves = 0;

        while (!q.isEmpty()) {
            int size = q.size();

            while (size-- > 0) {
                int[] cur = q.poll();

                int r = cur[0];
                int c = cur[1];
                int mask = cur[2];
                int e = cur[3];

                if (mask == fullMask) return moves;
                if (e == 0) continue;

                for (int d = 0; d < 4; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    if (nr < 0 || nr >= m || nc < 0 || nc >= n)
                        continue;

                    char ch = classroom[nr].charAt(nc);

                    if (ch == 'X') continue;

                    int ne = e - 1;
                    int nmask = mask;

                    if (ch == 'L')
                        nmask |= 1 << id[nr][nc];

                    if (ch == 'R')
                        ne = energy;

                    int pos = nr * n + nc;

                    if (best[pos][nmask] >= ne)
                        continue;

                    best[pos][nmask] = ne;
                    q.offer(new int[]{nr, nc, nmask, ne});
                }
            }

            moves++;
        }

        return -1;
    }
}