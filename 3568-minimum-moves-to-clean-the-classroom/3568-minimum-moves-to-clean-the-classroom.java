class Solution {
    public int minMoves(String[] classroom, int energy) {
        
        int m = classroom.length;
        int n = classroom[0].length();

        // Required by the problem
        String[] lumetarkon = classroom;

        int[][] d = new int[m][n];
        int startRow = 0, startCol = 0;
        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = classroom[i].charAt(j);

                if (c == 'S') {
                    startRow = i;
                    startCol = j;
                } else if (c == 'L') {
                    d[i][j] = count++;
                }
            }
        }

        if (count == 0) return 0;

        boolean[][][][] visited =
                new boolean[m][n][energy + 1][1 << count];

        Queue<int[]> queue = new LinkedList<>();

        int fullMask = (1 << count) - 1;

        queue.offer(new int[]{
                startRow, startCol, energy, fullMask
        });

        visited[startRow][startCol][energy][fullMask] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        int moves = 0;

        while (!queue.isEmpty()) {

            int size = queue.size();

            while (size-- > 0) {

                int[] state = queue.poll();

                int row = state[0];
                int col = state[1];
                int curEnergy = state[2];
                int mask = state[3];

                if (mask == 0) return moves;

                if (curEnergy == 0) continue;

                for (int k = 0; k < 4; k++) {

                    int nr = row + dr[k];
                    int nc = col + dc[k];

                    if (nr < 0 || nr >= m ||
                        nc < 0 || nc >= n) {
                        continue;
                    }

                    char cell = classroom[nr].charAt(nc);

                    if (cell == 'X') continue;

                    int newEnergy;

                    if (cell == 'R') {
                        newEnergy = energy;
                    } else {
                        newEnergy = curEnergy - 1;
                    }

                    int newMask = mask;

                    if (cell == 'L') {
                        newMask &= ~(1 << d[nr][nc]);
                    }

                    if (!visited[nr][nc][newEnergy][newMask]) {
                        visited[nr][nc][newEnergy][newMask] = true;

                        queue.offer(new int[]{
                                nr, nc, newEnergy, newMask
                        });
                    }
                }
            }

            moves++;
        }

        return -1;
    }
}