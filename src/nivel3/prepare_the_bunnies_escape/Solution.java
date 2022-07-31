package nivel3.prepare_the_bunnies_escape;

import java.util.PriorityQueue;

public class Solution {

    private static final int[] dx = { 0, 0, -1, 1 };
    private static final int[] dy = { -1, 1, 0, 0 };

    public static int solution(int[][] map) {
        int ans = Integer.MAX_VALUE;

        int rows = map.length;
        int cols = map[0].length;

        final PriorityQueue<Node> queue = new PriorityQueue<>();

        final boolean[][][] visited = new boolean[rows][cols][2];

        queue.add(new Node(0, 0, 1, false));

        while (!queue.isEmpty()) {
            Node top = queue.poll();
            if ((top.x == rows - 1) && (top.y == cols - 1)) {
                ans = Math.min(ans, top.moves);
                continue;
            }

            if (visited[top.x][top.y][top.remodeled ? 0 : 1]) continue;

            visited[top.x][top.y][top.remodeled ? 0 : 1] = true;

            for (int i = 0; i < dx.length; i++) {
                int nx = top.x + dx[i];
                int ny = top.y + dy[i];
                if (nx < 0 || nx >= rows || ny < 0 || ny >= cols || (map[nx][ny] == 1 && top.remodeled)) {
                    continue;
                }
                // if (map[nx][ny] == 1) {
                //     queue.add(new Node(nx, ny, top.moves + 1, true));
                // } else {
                //     queue.add(new Node(nx, ny, top.moves + 1, top.throughWall));
                // }
                queue.add(new Node(nx, ny, top.moves + 1, (map[nx][ny] == 1 || top.remodeled)));
            }
        }

        return ans;
    }

    private static class Node implements Comparable<Node> {

        protected int x;
        protected int y;
        protected int moves;
        protected boolean remodeled;

        public Node(final int x, final int y, final int moves, final boolean remodeled) {
            this.x = x;
            this.y = y;
            this.moves = moves;
            this.remodeled = remodeled;
        }

        @Override
        public int compareTo(final Node o) {
            return moves - o.moves;
        }
    }

    public static void main(String[] args) {
        {
            int[][] map = {
                { 0, 1, 1, 0 },
                { 0, 0, 0, 1 },
                { 1, 1, 0, 0 },
                { 1, 1, 1, 0 }
            };

            int result = solution(map);

            assert (result == 7);

            System.out.printf("RESULT: %d\n", result);
        }
        {
            int[][] map = {
                { 0, 0, 0, 0, 0, 0 },
                { 1, 1, 1, 1, 1, 0 },
                { 0, 0, 0, 0, 0, 0 },
                { 0, 1, 1, 1, 1, 1 },
                { 0, 1, 1, 1, 1, 1 },
                { 0, 0, 0, 0, 0, 0 }
            };

            int result = solution(map);

            assert (result == 11);

            System.out.printf("RESULT: %d\n", result);
        }
        {
            int[][] map = {
                { 0, 0, 0, 0, 0, 0 },
                { 1, 1, 1, 1, 1, 0 },
                { 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 1, 1 },
                { 0, 1, 1, 1, 1, 1 },
                { 0, 0, 0, 0, 0, 0 }
            };

            int result = solution(map);

            assert (result == 11);

            System.out.printf("RESULT: %d\n", result);
        }
    }

}
