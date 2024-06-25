import java.util.*;
import java.io.*;

public class Main {

    static int N,M,L;
    static int[] arr;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());

        arr = new int[N+2];

        // 출발 위치
        arr[0] = 0;

        // 끝 위치
        arr[N+1] = L;

        st = new StringTokenizer(br.readLine());
        for(int i = 1; i < N+1; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);

        System.out.println(findLMaxLength(1,L-1));

    }

    private static int findLMaxLength(int left, int right){
        while(left <= right){
            int mid = (left + right) / 2;
            int sum = 0;

            for(int i = 1; i < arr.length; i++){
                sum += (arr[i] - arr[i-1] -1) / mid;
            }

            if(sum > M){
                left = mid+1;
            }
            else {
                right = mid-1;
            }
        }

        return left;
    }
}
