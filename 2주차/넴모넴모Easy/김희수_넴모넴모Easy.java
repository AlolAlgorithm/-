import java.util.*;
import java.io.*;

public class Main {
	static int n,m;
	static StringTokenizer st;
	static boolean visited[][];
	static int cnt;
	
	
	static boolean check(int sy, int sx) {
		int ey = sy + 1;
		int ex = sx + 1;
		for(int y = sy; y<=ey; y++) {
			for(int x=sx;x<=ex;x++) {
				if(!visited[y][x])
					return false;
			}
		}
		return true;
	}
	
	static boolean possible() {
		for(int sy= 1; sy<=n-1; sy++) {
			for(int sx=1; sx<=m-1;sx++) {
				if(check(sy,sx))
					return false;
			}
		}
		return true;
	}
	
	
	static void solve(int cur) {

		if(cur == n*m) {
			
			if(possible()) {
				cnt++;
			}
			
			return;
		}
		
		int y = cur / m + 1;
		int x = cur % m + 1;
		
		visited[y][x] = true;
		solve(cur + 1);
		visited[y][x] = false;
		
		solve(cur + 1);
	}
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	st = new StringTokenizer(br.readLine());
    	n = Integer.parseInt(st.nextToken());
    	m = Integer.parseInt(st.nextToken());
    	visited = new boolean[n+1][m+1];
    	solve(0);
    	System.out.println(cnt);
    }
}