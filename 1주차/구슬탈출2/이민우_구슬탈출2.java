package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * [문제 설명]
 * 구슬탈출 : 직사각형 보드에 빨간 구슬과 파란구슬을 하나씩 넣은 다음, 빨간 구슬을 구멍을 통해 빼내는 게임
 * 가장 바깥 쪽 행 열을 막혀져 있음
 * 보드에는 구멍이 하나 있다.
 * 파란구슬은 구멍에 들어가면 안된다.
 * 빨간 구슬, 파란구슬 동시에 들어가도 안된다.
 * 빨간 구슬과 파란구슬은 동시에 같은 칸에 있을 수 없다.
 * 기울이는 동작은 더 이상 구슬이 움직이지 않을 때까지 진행
 * 최소 몇번만에 빨간 구슬을 구멍을 통해 빼낼 수 있는지 구하라
 * <p>
 * [입력]
 * 두 정수 N, M (3 ≤ N, M ≤ 10)
 * '.' 빈칸
 * '#' 벽
 * 'O' 구멍
 * 'R' 빨간 구슬
 * 'B' 파란구슬
 * <p>
 * [문제 해결 프로세스]
 * 1. 기울이기를 어디로 할지 정하기 -> BFS
 * 2. 기울일 때 구슬이 같은 라인에 있다면
 * - 가로 : 좌 우로 기울일 때 -> 좌 : 왼 -> 오 / 우 : 오 -> 왼
 * - 세로 : 상 하로 기울일 때 -> 상 : 위 -> 아래 / 하 : 아래 -> 위
 * 3. 큐에 넣는 조건
 * - 구슬 중 하나라도 움직인다면
 * 4. 큐에 넣지 않는 조건
 * - B가 먼저 구멍에 들어갈 때
 */

public class Main {
    static int N, M;
    static char[][] arr;
    static boolean[][][][] visit;
    static int[][] answer;
    static Point RED, BLUE, HOLE; // 구슬의 현재 위치 저장
    static int INF = 987654321;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        M = Integer.parseInt(str[1]);
        arr = new char[N][M];
        answer = new int[N][M];
        visit = new boolean[N][M][N][M];

        for (int i = 0; i < N; i++) Arrays.fill(answer[i], INF);

        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                char c = input.charAt(j);
                arr[i][j] = c;
                if (c == 'R') {
                    RED = new Point(i, j);
                    arr[i][j] = '.';
                } else if (c == 'B') {
                    BLUE = new Point(i, j);
                    arr[i][j] = '.';
                } else if (c == 'O') {
                    HOLE = new Point(i, j);
                }
            }
        }
        BFS();
        if(answer[HOLE.x][HOLE.y] == INF) System.out.println(-1);
        else System.out.println(answer[HOLE.x][HOLE.y]);

    }

    public static void BFS() {
        Queue<Bead> queue = new ArrayDeque<>();
        queue.add(new Bead(RED, BLUE, 0));

        while (!queue.isEmpty()) {
            Bead cur = queue.poll();
            if (cur.cnt > 10) {
                continue;
            }
            if (cur.red.x == HOLE.x && cur.red.y == HOLE.y && cur.blue.x == HOLE.x && cur.blue.y == HOLE.y) {
                if (answer[HOLE.x][HOLE.y] == INF) answer[HOLE.x][HOLE.y] = -1;
            } else if (cur.red.x == HOLE.x && cur.red.y == HOLE.y) {
                if (answer[HOLE.x][HOLE.y] == -1) answer[HOLE.x][HOLE.y] = cur.cnt;
                else if (answer[HOLE.x][HOLE.y] > cur.cnt) answer[HOLE.x][HOLE.y] = cur.cnt;
            }

            int cnt = 0;
            for (int i = 0; i < 4; i++) {
                Bead next = nextPos(cur, i);
                if (next.red.x == cur.red.x && next.red.y == cur.red.y && next.blue.x == cur.blue.x && next.blue.y == cur.blue.y) { // 두 구슬 모두 이동하지 않았으면
                    cnt++;
                    continue;
                }
                if (visit[next.red.x][next.red.y][next.blue.x][next.blue.y]) continue;
                if (next.blue.x == HOLE.x && next.blue.y == HOLE.y) continue;
                visit[next.red.x][next.red.y][next.blue.x][next.blue.y] = true;
                queue.add(new Bead(next));
            }
            if (cnt == 4) {
                answer[HOLE.x][HOLE.y] = -1;
                return;
            }
        }
    }

    // 기울이기
    // 멈춰야 할 때 : 벽, 구슬 뒤, 구멍
    public static Point tilt(Point p, Point previous, int dir) {
        if (dir == 0) { // 상
            int nx = p.x;
            while (true) {
                if (arr[nx - 1][p.y] == '#') return new Point(nx, p.y);
                if (arr[nx - 1][p.y] == 'O') return new Point(nx - 1, p.y);
                if (previous != null) { // 내 앞에 구슬이 있는 경우
                    if (nx - 1 == previous.x && p.y == previous.y) return new Point(nx, p.y);
                }
                if (arr[nx - 1][p.y] == '.') {
                    nx -= 1;
                }
            }
        } else if (dir == 1) {  // 하
            int nx = p.x;
            while (true) {
                if (arr[nx + 1][p.y] == '#') return new Point(nx, p.y);
                if (arr[nx + 1][p.y] == 'O') return new Point(nx + 1, p.y);
                if (previous != null) { // 내 앞에 구슬이 있는 경우
                    if (nx + 1 == previous.x && p.y == previous.y) return new Point(nx, p.y);
                }
                if (arr[nx + 1][p.y] == '.') {
                    nx += 1;
                }
            }
        } else if (dir == 2) {  // 좌
            int ny = p.y;
            while (true) {
                if (arr[p.x][ny - 1] == '#') return new Point(p.x, ny);
                if (arr[p.x][ny - 1] == 'O') return new Point(p.x, ny - 1);
                if (previous != null) { // 내 앞에 구슬이 있는 경우
                    if (p.x == previous.x && ny - 1 == previous.y) return new Point(p.x, ny);
                }
                if (arr[p.x][ny - 1] == '.') {
                    ny -= 1;
                }
            }
        } else {  // 우
            int ny = p.y;
            while (true) {
                if (arr[p.x][ny + 1] == '#') return new Point(p.x, ny);
                if (arr[p.x][ny + 1] == 'O') return new Point(p.x, ny + 1);
                if (previous != null) { // 내 앞에 구슬이 있는 경우
                    if (p.x == previous.x && ny + 1 == previous.y) return new Point(p.x, ny);
                }
                if (arr[p.x][ny + 1] == '.') {
                    ny += 1;
                }
            }
        }
    }

    // 앞에 있는 구슬 이동 후 뒤에 있는 구슬 이동(좌표만 전송) -> arr 에서 이전 위치를 '.'으로 바꾸고, 현재위치 최신화하는 작업은 다른 곳에서
    public static Bead nextPos(Bead bead, int dir) {
        Point red = bead.red;
        Point blue = bead.blue;
        if (dir == 0) {  // 상 -> x축을 비교해서 더 작은 값이 먼저 이동(y는 상관 없음)
            if (red.x < blue.x) { // red가 상위에 존재하는 경우
                red = tilt(red, null, dir);
                blue = tilt(blue, red, dir);
            } else if (red.x > blue.x) { // blue가 상위에 존재하는 경우
                blue = tilt(blue, null, dir);
                red = tilt(red, blue, dir);
            } else { // 같은위치(y 만 다른 경우) -> x,y 모두 같을 수는 없음
                red = tilt(red, null, dir);
                blue = tilt(blue, null, dir);
            }
        } else if (dir == 1) { // 하 -> x축을 비교해서 더 큰 값이 먼저 이동(y는 상관 없음)
            if (red.x > blue.x) { // red가 상위에 존재하는 경우
                red = tilt(red, null, dir);
                blue = tilt(blue, red, dir);
            } else if (red.x < blue.x) { // blue가 상위에 존재하는 경우
                blue = tilt(blue, null, dir);
                red = tilt(red, blue, dir);
            } else { // 같은위치(y 만 다른 경우) -> x,y 모두 같을 수는 없음
                red = tilt(red, null, dir);
                blue = tilt(blue, null, dir);
            }
        } else if (dir == 2) { // 좌 -> y축을 비교해서 더 작은 값이 먼저 이동(x는 상관 없음)
            if (red.y < blue.y) { // red가 상위에 존재하는 경우
                red = tilt(red, null, dir);
                blue = tilt(blue, red, dir);
            } else if (red.y > blue.y) { // blue가 상위에 존재하는 경우
                blue = tilt(blue, null, dir);
                red = tilt(red, blue, dir);
            } else { // 같은위치(y 만 다른 경우) -> x,y 모두 같을 수는 없음
                red = tilt(red, null, dir);
                blue = tilt(blue, null, dir);
            }
        } else { // 우 -> y축을 비교해서 더 큰 값이 먼저 이동(y는 상관없음)
            if (red.y > blue.y) { // red가 상위에 존재하는 경우
                red = tilt(red, null, dir);
                blue = tilt(blue, red, dir);
            } else if (red.y < blue.y) { // blue가 상위에 존재하는 경우
                blue = tilt(blue, null, dir);
                red = tilt(red, blue, dir);
            } else { // 같은위치(y 만 다른 경우) -> x,y 모두 같을 수는 없음
                red = tilt(red, null, dir);
                blue = tilt(blue, null, dir);
            }
        }
        return new Bead(red, blue, bead.cnt + 1);
    }

    static class Bead {
        Point red;
        Point blue;
        int cnt;

        public Bead(Point red, Point blue, int cnt) {
            this.red = red;
            this.blue = blue;
            this.cnt = cnt;
        }

        public Bead(Bead bead) {
            this.red = bead.red;
            this.blue = bead.blue;
            this.cnt = bead.cnt;
        }
    }

    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
