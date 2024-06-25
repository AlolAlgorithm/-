import java.util.*;
import java.io.*;

public class Main {

    static int N;
    static int[][] map;
    static int answer;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        answer = 0;

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0);
        System.out.println(answer);

    }

    private static void copyMap(int[][] originalMap, int[][] copyMap){
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                copyMap[i][j] = originalMap[i][j];
            }
        }
    }

    private static void addNumber(int dir){
        Queue<Integer> q = new LinkedList<>();

        // 위로
        if(dir == 1){
            for(int i = 0; i < N; i++){
                for(int j = 0; j < N; j++){
                    // 0이 아니면 q에 넣고
                    if(map[j][i] != 0){
                        q.offer(map[j][i]);
                    }
                    // 해당 부분 0처리
                    map[j][i] = 0;
                }

                // queue에 세로줄의 값이 다 들어가있기 때문에 꺼내면서 합칠 수 있으면 합치고 map에 저장
                int idx = 0;
                while(!q.isEmpty()){
                    int num = q.poll();

                    // 아무것도 안들어가있을 떄
                    if(map[idx][i] == 0){
                        map[idx][i] = num; 
                    }
                    // 숫자가 같으면 더하기
                    else if( map[idx][i] == num){
                        map[idx][i] = num*2;
                        idx++;
                    }
                    // 들어가있을 때
                    else {
                        idx++;
                        map[idx][i] = num;
                    }
                }
            }
        }
        // 아래로
        else if (dir == 2){
            for(int i = 0; i < N; i++){
                for(int j = N-1; j>=0; j--){
                    if(map[j][i] != 0){
                        q.offer(map[j][i]);
                    }
                    map[j][i] = 0;
                }

                int idx = N-1;
                while(!q.isEmpty()){
                    int num = q.poll();

                    // 아무것도 안들어가있을 때
                    if(map[idx][i] == 0){
                        map[idx][i] = num;
                    }
                    else if(map[idx][i] == num){
                        map[idx][i] = num*2;
                        idx--;
                    }
                    else{
                        idx--;
                        map[idx][i] = num;
                    }

                }
            }
        }
        // 왼쪽으로
        else if (dir == 3){
            for(int i = 0; i < N; i++){
                for(int j = N-1; j >=0; j--){
                    if(map[i][j] != 0){
                        q.offer(map[i][j]);
                    }
                    map[i][j] = 0;
                }

                int idx = N-1;
                while(!q.isEmpty()){
                    int num = q.poll();

                    if(map[i][idx] == 0) {
                        map[i][idx] = num;
                    }
                    else if(map[i][idx] == num){
                        map[i][idx] = num*2;
                        idx--;
                    }
                    else{
                        idx--;
                        map[i][idx] = num;
                    }
                }
            }
        }
        // 오른쪽으로
        else if (dir == 4){
            for(int i = 0; i < N; i++){
                for(int j = 0; j < N; j++){
                    if(map[i][j] != 0){
                        q.offer(map[i][j]);
                    }
                    map[i][j] = 0;
                }

                int idx = 0;
                while(!q.isEmpty()){
                    int num = q.poll();

                    if(map[i][idx] == 0){
                        map[i][idx] = num;
                    }
                    else if(map[i][idx] == num){
                        map[i][idx] = num*2;
                        idx++;
                    }
                    else{
                        idx++;
                        map[i][idx] = num;
                    }
                }
            }
        }

    }

    // 제일 큰 수를 구하는 함수
    private static int getMaxNumber(){
        int max = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                max = Math.max(max, map[i][j]);
            }
        }
        return max;
    }

    private static void dfs(int depth){
        if(depth >= 5){
            int max = getMaxNumber();
            answer = Math.max(answer, max);
            return;
        }
        else{
            int[][] copyMap = new int[N][N];
            copyMap(map, copyMap);

            for(int i = 1; i <= 4; i++){
                addNumber(i);
                dfs(depth+1);
                copyMap(copyMap, map);
            }
        }
    }

    private static void printMap(){
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                System.out.print(map[i][j]+",");
            }
            System.out.println();
        }
    }
}
