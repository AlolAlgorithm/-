import java.util.*;
import java.io.*;
public class 김희수_구슬탈출2 {
	static StringTokenizer st;
	static char board[][];
	static int n,m;
	static class Pair{
		int y,x;
		public Pair(int y,int x) {
			this.y = y;
			this.x = x;
		}
		
		public String toString() {
			return y+" "+x;
		}
		
		public boolean isSame(int y,int x) {
			return this.y == y && this.x == x;
		}
	}
	
	static Pair red;
	static Pair blue;
	static Pair exit;
	
	static int INT_MAX = Integer.MAX_VALUE;
	static int ans = INT_MAX;
	static Pair NO_POS = new Pair(-1,-1);
	
	
    // red가 먼저 이동할 수 있는지 여부 
	static boolean redFirst(int dir) {
		int ry = red.y;
		int rx = red.x;
		
		int by = blue.y;
		int bx = blue.x;
		
		if(dir == 0) {
			return rx == bx && ry < by;
		}else if(dir == 1) {
			return rx == bx && ry > by;
		}else if(dir == 2) {
			return rx < bx && ry == by;
		}
		
		return rx > bx && ry == by;
	}
	
	static int dy[] = {-1,1,0,0};
	static int dx[] = {0,0,-1,1};
	static boolean isWall(int y,int x) {
		return board[y][x] == '#';
	}

    /*
     * 이동을 나타내는 함수
     * target: 이동하려는 사탕
     * other : 이동하지 않는 사탕
     * dir : 이동방향 
     */
	static Pair move(Pair target, Pair other, int dir) {
		int y = target.y;
		int x = target.x;
		
		int ny = y + dy[dir];
		int nx = x + dx[dir];
		
		if(exit.isSame(ny,nx)) // 다음 위치가 출구면 탈출 
			return NO_POS;
		
		if(isWall(ny,nx) || other.isSame(ny, nx)) // 다음 위치가 벽이거나 다른 사탕이면 이동을 멈춘다. 
			return target;
		
		return move(new Pair(ny,nx), other, dir); // 다음 위치에 대해 동일한 행위를 반복한다.
        
        // 재귀를 사용하지 않고 Queue를 사용해서도 풀 수 있다.
        // Queue<Pair> q = new LinkedList<>();
		// q.add(target);
		
		// while(!q.isEmpty()) {
		// 	Pair cur = q.poll();
			
		// 	int ny = cur.y + dy[dir];
		// 	int nx = cur.x + dx[dir];
			
		// 	if(exit.isSame(ny, nx))
		// 		break;
		// 	if(isWall(ny,nx) || other.isSame(ny, nx))
		// 		return cur;
			
		// 	q.add(new Pair(ny,nx));
		// }
		
		// return NO_POS;
		
	}
	
	static void tilt(int dir) {
		boolean flag = redFirst(dir);
		
		if(flag) { // red가 먼저 이동할 수 있으면 
			red = move(red, blue, dir); // red가 먼저 이동한다. 이때 blue의 위치는 고정 
			blue = move(blue, red, dir); // blue가 이동한다. 이 때 red의 위치는 고정 
		}else { // red가 먼저 이동할 수 없으면 
			blue = move(blue, red, dir); // blue가 먼저 이동한다. 이 때 red의 위치는 고정
			red = move(red, blue, dir); // red가 이동한다. 이 때 blue의 위치는 고정
		}
	}
	
	static boolean blueEscape() {
		return blue == NO_POS;
	}
	
	static boolean redEscape() {
		return red == NO_POS;
	}
	
	static void solve(int cnt) {
		if(blueEscape()) // blue가 탈출한 모든 경우는 답이 될 수 없다. 
			return;
		
		if(redEscape()) { // blue가 탈출하지 않고 red만 탈출한 경우만 답이 될 수 있다. 
			ans = Math.min(ans, cnt);
			return;
		}
		
		if(cnt >= 10) // 기울이기가 10번이상이면 안된다. 
			return;
		
		for(int dir = 0; dir<4;dir++) {

            // 기울이기 전에 이전 위치 저장 
			Pair tempRed = red;
			Pair tempBlue = blue;

            // 기울임 
			tilt(dir);

            // 다음 기울이기 수행 
			solve(cnt+1);
			

            // 이전 위치 복원 
			red = tempRed;
			blue = tempBlue;
		}
	}
	
	public static void main(String[] args) throws IOException{
//		System.setIn(new FileInputStream("./input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		board = new char[n+1][m+1];
		for(int y=1; y<=n; y++) {
			String str = br.readLine();
			for(int x = 1; x<=m; x++) {
				board[y][x] = str.charAt(x-1);
				
				
				if(board[y][x] == 'R') {
					red = new Pair(y,x);
				}else if(board[y][x] == 'B') {
					blue = new Pair(y,x);
				}else if(board[y][x] == 'O') {
					exit = new Pair(y,x);
				}
				
			}
		}
		
		
		solve(0);
		System.out.println(ans >=11 ? -1 : ans);
		
		
	}
}