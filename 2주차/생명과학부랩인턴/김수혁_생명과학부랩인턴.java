import java.util.*;
import java.io.*;

/*
* 빨간색 숫자 : 곰팡이의 크기
* 파란색의 숫자 : 속력
* 
* 곰팡이 채취 규칙
* 1. 첫번째 열부터 탐색
* 2. 위에서 아래로 내려가면서 제일 빨리 발견한 곰팡이 채취 -> 곰팡이 채취한 칸은 빈칸
* 3. 열 채취 시도 끝나면 곰팡이가 이동
* 4. 격자판의 벽에 도달하면 반대로 방향을 바꾸고 속력 유지한채로 이동, 방향 바꾸는 건 시간 안걸림
* 5. 모든 곰팡이가 이동 끝내고 한 칸에 곰팡이가 두마리 이상이면 크기가 큰 곰팡이가 다른 곰팡이를 잡아먹음
* 6. 이 모든 과정이 1초 이후 승용이는 오른쪽 열로 이동 후 반복
*/

public class Main {
    static class Mold{
        int r;
        int c;
        int s;
        int d;
        int b;
        public Mold(int r, int c, int s, int d, int b){
            this.r = r;
            this.c = c;
            this.s = s;
            this.d = d;
            this.b = b;
        }
    }
    static int N,M,K,answer;
    static Mold[] molds;
    static int[][] map;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,1,-1};

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new int[N+1][M+1];
        molds = new Mold[K];

        for(int i = 0; i < K; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken())-1;
            int y = Integer.parseInt(st.nextToken())-1;
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken())-1;
            int b = Integer.parseInt(st.nextToken());
            molds[i] = new Mold(x,y,s,d,b);
            map[x][y] = i+1;
        }

        simulate();
        System.out.println(answer);

    }

    // 전체 프로그램 실행 함수
    private static void simulate() {
        for (int col = 0; col < M; col++) {
            int moldInfo = searchMold(col);
            if (moldInfo != 0) {
                answer += molds[moldInfo - 1].b;
                map[molds[moldInfo - 1].r][molds[moldInfo - 1].c] = 0;
                molds[moldInfo - 1] = null;
            }
            moveMold();
        }
    }

    private static void moveMold() {
        int[][] newMap = new int[N][M];
        for (int i = 0; i < molds.length; i++) {
            if (molds[i] == null) continue;

            Mold nowMold = molds[i];
            int nr = nowMold.r;
            int nc = nowMold.c;

            for (int j = 0; j < nowMold.s; j++) {
                nr += dx[nowMold.d];
                nc += dy[nowMold.d];

                if (nr < 0 || nr >= N || nc < 0 || nc >= M) {
                    nowMold.d = changeDir(nowMold.d);
                    nr += dx[nowMold.d] * 2;
                    nc += dy[nowMold.d] * 2;
                }
            }

            nowMold.r = nr;
            nowMold.c = nc;

            if (newMap[nr][nc] == 0 || molds[newMap[nr][nc] - 1].b < nowMold.b) {
                if (newMap[nr][nc] != 0) {
                    molds[newMap[nr][nc] - 1] = null;
                }
                newMap[nr][nc] = i + 1;
            } else {
                molds[i] = null;
            }
        }
        map = newMap;
    }

    private static int searchMold(int col) {
        for (int i = 0; i < N; i++) {
            if (map[i][col] != 0) {
                return map[i][col];
            }
        }
        return 0;
    }

    private static int changeDir(int dir) {
        if (dir % 2 == 0) {
            return dir + 1;
        } else {
            return dir - 1;
        }
    }
}
