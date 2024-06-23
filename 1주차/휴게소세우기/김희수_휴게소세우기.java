import java.util.*;
import java.io.*;
public class 김희수_휴게소세우기 {
	static int n,m,l;
	static StringTokenizer st;
	static int arr[];
	static boolean decide(int target) {
		int cnt = 0;
		
		int start = 0;
		int end = l;
		
		
		for(int i = 0; i < n; i++) {
			int cur = arr[i];
			
			int len = cur - start;
			cnt += len / target;
			if(len % target == 0)
				cnt--;
			start = cur;
		}
		
		int len = end - start;
		cnt += len / target;
		if(len % target == 0)
			cnt--;
		return cnt <= m;
		
	}
	public static void main(String[] args) throws IOException{
//		System.setIn(new FileInputStream("./input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		l = Integer.parseInt(st.nextToken());
		
		arr = new int[n];
		
		if(n != 0) {
			st = new StringTokenizer(br.readLine());
			for(int i = 0; i < n;i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			Arrays.sort(arr);
		
		}
		
		
		
		int left = 1;
		int right = l-1;
		
		
		int ans = Integer.MAX_VALUE;
		while(left <= right) {
			int mid = (left + right) / 2;
			
			if(decide(mid)) {
				right = mid - 1;
				ans = mid;
			}else {
				left = mid + 1;
			}
			
		}
		
		System.out.println(ans);
	}
}