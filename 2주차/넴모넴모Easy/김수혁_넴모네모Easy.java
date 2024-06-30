import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 격자판의 비어 있는 칸을 임의로 골라 넴모를 하나 올려놓거나 올라간  칸 4개가 2x2 사각형을 이루는 부분을 찾아 그 위에 있는 넴모들을 모두 없애는 것을 질릴때까지 하는 것
 * 네모가 게임을 그만 두었을 때 나올 수 있는 넴모의 배치의 가짓수 구하기
 * 게임을 그만 두는것은 격자판 위에 없앨 수 있는 넴모가 없을 때
 *
 * 그럼 지금 위치에서 위, 왼쪽대각선, 왼쪽 이렇게 모두 1이면 없애기
 * 
 */

public class BOJ_14712_넴모넴모Easy {

    static int N,M;
    static int[][] map;
    static int answer;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N+1][M+1];
        answer = 0;

        dfs(0);
        System.out.println(answer);

    }

    private static void dfs(int depth){
        if(depth == N*M){
            answer++;
            return;
        }

        // 현재 위치 구하기
        // N과 M이 2, 2라고 했을 때
        // depth = 0이면 (1,1), depth = 1이면 (1,2), depth = 2이면 (2,1), depth = 3이면 (2,2)
        // 그럼 만약 N이 2, M이 3이면
        // depth = 0이면 (1,1), depth = 1이면 (1,2), depth = 2이면 (1,3), depth = 3이면 (2,1), depth = 4이면 (2,2), depth = 5이면 (2,3)
        int r = depth / M + 1;
        int c = depth % M + 1;

        if(map[r-1][c] == 1 && map[r][c-1] == 1 && map[r-1][c-1] == 1){
            // 놓을 수 없을 때
            dfs(depth+1);
        } else {
            dfs(depth+1);   // 선택했을 때
            map[r][c] = 1;
            dfs(depth+1);   // 선택안했을 때
            map[r][c] = 0;
        }

    }
}
