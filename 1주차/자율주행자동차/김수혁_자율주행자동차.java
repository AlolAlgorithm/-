import java.util.*;
import java.io.*;


public class Main {
    
    static class Car{
        int r;
        int c;
        int d;
        public Car(int r, int c, int d){
            this.r = r;
            this.c = c;
            this.d = d;
        }
    }

    static int N,M;
    static int[][] map;
    static boolean[][] visited;

    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    static Car car;
    
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        
        // N,M 입력받기
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        visited = new boolean[N][M];

        st = new StringTokenizer(br.readLine());

        // 차의 시작 지점, 방향 입력받기
        int startX = Integer.parseInt(st.nextToken());
        int startY = Integer.parseInt(st.nextToken());
        int startD = Integer.parseInt(st.nextToken());
        car = new Car(startX, startY, startD);

        // 맵 입력받기
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 시작지점 방문처리
        visited[startX][startY] = true;

        // flag : 후진조차 안되어서 더 이상 움직일 수 없으면 false가 되게 하기
        boolean flag = true;
        
        // 시작지점이 주어지므로 최소 1
        int answer = 1;
        
        while(flag){
            
            // 현재 위치
            int cX = car.r;
            int cY = car.c;
            int cD = car.d;

            for(int i = 3; i >=0; i--){
                // 다음 방향과 좌표 계산
                int nextDir = (cD + i) % 4;
                int nX = cX + dx[nextDir];
                int nY = cY + dy[nextDir];

                // 이미 방문했거나 인도라면 continue
                if(visited[nX][nY] || map[nX][nY]==1){
                    continue;
                }
                
                if(!visited[nX][nY] && map[nX][nY]==0){
                    car.r = nX;
                    car.c = nY;
                    car.d = nextDir;
                    break;
                }
            }

            // 4방향 모두 돌았는지 체크하기 위해서는 기준의 cX, cY와 현재의 car의 r과 c를 비교
            // cX와 car.r이 같고 cY와 car.c가 같으면 위치가 변하지 않은 것이므로 후진해야하는 상황
            if(cX == car.r && cY == car.c){
                // 반대를 봐야 하니 그냥 기존 방향에 2를 더하고 4로 나눠주면 됨
                int nX = cX + dx[(cD+2)%4];
                int nY = cY + dx[(cD+2)%4];

                // 만약 후진하는데가 인도가 아니면
                if(map[nX][nY]==0){
                    car.r = nX;
                    car.c = nY;
                } else{
                    flag = false;
                }
            } else {
                if(!visited[car.r][car.c]){
                    answer++;
                }
                visited[car.r][car.c]=true;
            }

        }

        System.out.println(answer);

    }
}
