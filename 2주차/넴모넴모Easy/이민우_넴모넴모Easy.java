import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * [문제 설명]
 * 2 x 2의 배치가 생기기 전의 경우의 수 구하기
 * 최대 5 x 5의 크기
 * [문제 해결 프로세스]
 * 1. DFS로 네모를 하나씩 놓고 네모가 생성되었는지 체크
 * 2. 네모가 생성되었다면 종료
 * 3. 그렇지 않다면 정답 + 1
 */
public class Main {
    static int N, M;
    static boolean[][] visit;
    static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        visit = new boolean[N][M];
        DFS(0);
        System.out.println(answer);

    }
    public static void DFS(int depth) {
        if(depth == N * M) {
            if(check()) answer++;
            return;
        }
        int x = depth / M;
        int y = depth % M;


        DFS(depth + 1);
        visit[x][y] = true;
        DFS(depth + 1);
        visit[x][y] = false;

    }

    public static boolean check() {
        for(int i = 0; i< N - 1; i++) {
            for(int j = 0; j< M - 1; j++) {
                if(visit[i][j] && visit[i][j + 1] && visit[i + 1][j] && visit[i + 1][j + 1]) return false;
            }
        }
        return true;
    }

}
