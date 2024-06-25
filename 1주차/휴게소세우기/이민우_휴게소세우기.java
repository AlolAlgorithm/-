import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * [문제 설명]
 * 현재 휴게소의 개수는 N개
 * M개의 휴게소를 추가설치하려고 함
 * 휴게소의 거리 중 최대값이 최소가 되도록
 *
 * [조건] 시간 2초
 * 1. 이미 휴게소가 있는 곳에 휴게소 설치 불가
 * 2. 고속도로 끝에 설치 불가
 * 3. 휴게소는 정수의 위치에만 세울 수 있음
 *
 * [입력]
 * - 0 ≤ N ≤ 50
 * - 1 ≤ M ≤ 100
 * - 100 ≤ L ≤ 1,000
 * - 1 ≤ 휴게소의 위치 ≤ L-1
 *
 * [문제 해결 프로세스]
 * 1. 휴게소의 위치를 입력받고, 오름차순 정렬(간격을 계산해야하므로 0과 L의 크기를 배열 양 끝에 넣어줘야 함)
 * 2. 휴게소의 최대 간격 설정(tempInterval) -> 정확한 간격을 모르므로 L의 중간값으로 설정하고 추후 변경하자
 * 3. L에서 tempInterval 나눴을 때 -> 설치할 수 있는 휴게소 수(buildable)
 * 4. buildable > M 인 경우 -> 설치 가능한 휴게소 수가 이미 M을 넘어갔다. -> tempInterval 간격을 넓혀야 한다.
 * 5. buildable < M 인 경우 -> 설치 가능한 휴게소 수가 M보다 적다. -> tempInterval 간격을 줄여야 한다.
 * 6. buildable == M 인 경우 -> 개수가 일치한다. 하지만 tempInterval 간격을 더 줄일 수 있는지 체크해야함.
 */

public class Main {

    static int N, M, L;
    static int[] arr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        arr = new int[N + 2];
        arr[0] = 0;
        arr[N + 1] = L;

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(arr);
        int left = 1;
        int right = L - 1;
        int answer = 0;

        while (left <= right) {
            int tempInterval = (left + right) / 2;  // 휴게소 간 최대 길이
            int buildable = 0;

            // 설치 가능한 휴게소 수 계산
            for (int i = 1; i <= N + 1; i++) {
                int interval = arr[i] - arr[i - 1];
                buildable += interval / tempInterval;
                if (interval % tempInterval == 0) buildable--;  // 이미 해당 위치에 휴게소 설치되어 있으므로
            }

            if (buildable > M) left = tempInterval + 1; // 간격을 늘려야 함
            else {
                right = tempInterval - 1; // 간격을 줄여야 함
                answer = tempInterval;
            }
        }
        System.out.println(answer);
    }

}
