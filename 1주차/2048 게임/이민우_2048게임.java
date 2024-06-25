import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 최대 5번 움직여서 만들 수 있는 가장 큰 블록의 값 구하기
 * [입력]
 * N (1 ≤ N ≤ 20)
 * 들어올 수 있는 최대 값 2 ~ 1024로 2의 제곱근 형태
 * [문제 해결 프로세스]
 * 1. 5번을 움직여 최대 값을 찾기 위해서는 순열이 필요함 (상하좌우)
 * 2. 순열이 만들어졌을 때 해당 방향으로 게임 시작
 * 3. 원본배열 남기고 복사배열 사용
 * 4. 벽으로 밀기, 합치기 2개 메서드 필요
 */

public class Main {
    static int N, answer;
    static int[][] original;
    static int[] permutation;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        original = new int[N][N];
        permutation = new int[5];

        StringTokenizer st = null;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                original[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        setPermutation(0);
        System.out.println(answer);
    }

    public static int game(int[][] copy) {
        for (int dir : permutation) {
            push(copy, dir);
            addUp(copy, dir);
            push(copy, dir);
        }
        return findMax(copy);
    }

    // 벽으로 밀치기
    public static int[][] push(int[][] copy, int dir) {
        List<Integer> temp = null;
        if (dir == 0) {  // 상
            for (int i = 0; i < N; i++) {
                temp = new ArrayList<>();
                for (int j = 0; j < N; j++) {
                    if (copy[j][i] == 0) continue;
                    temp.add(copy[j][i]);
                    copy[j][i] = 0;
                }
                for (int j = 0; j < temp.size(); j++) {
                    copy[j][i] = temp.get(j);
                }
            }
        } else if (dir == 1) {  // 하
            for (int i = 0; i < N; i++) {
                temp = new ArrayList<>();
                for (int j = N - 1; j >= 0; j--) {
                    if (copy[j][i] == 0) continue;
                    temp.add(copy[j][i]);
                    copy[j][i] = 0;
                }
                for (int j = 0; j < temp.size(); j++) {
                    copy[N - 1 - j][i] = temp.get(j);
                }
            }
        } else if (dir == 2) {  // 좌
            for (int i = 0; i < N; i++) {
                temp = new ArrayList<>();
                for (int j = 0; j < N; j++) {
                    if (copy[i][j] == 0) continue;
                    temp.add(copy[i][j]);
                    copy[i][j] = 0;
                }
                for (int j = 0; j < temp.size(); j++) {
                    copy[i][j] = temp.get(j);
                }
            }
        } else if (dir == 3) {  // 우
            for (int i = 0; i < N; i++) {
                temp = new ArrayList<>();
                for (int j = N - 1; j >= 0; j--) {
                    if (copy[i][j] == 0) continue;
                    temp.add(copy[i][j]);
                    copy[i][j] = 0;
                }
                for (int j = 0; j < temp.size(); j++) {
                    copy[i][N - 1 - j] = temp.get(j);
                }
            }
        }
        return copy;
    }

    //합치기
    public static int[][] addUp(int[][] copy, int dir) {
        if (dir == 0) {  // 상
            for (int i = 0; i < N - 1; i++) {
                for (int j = 0; j < N; j++) {
                    if (copy[i][j] == copy[i + 1][j]) {
                        copy[i][j] *= 2;
                        copy[i + 1][j] = 0;
                    }
                }
            }
        } else if (dir == 1) {  // 하
            for (int i = N - 1; i > 0; i--) {
                for (int j = 0; j < N; j++) {
                    if (copy[i][j] == copy[i - 1][j]) {
                        copy[i][j] *= 2;
                        copy[i - 1][j] = 0;
                    }
                }
            }
        } else if (dir == 2) {  // 좌
            for (int i = 0; i < N - 1; i++) {
                for (int j = 0; j < N; j++) {
                    if (copy[j][i] == copy[j][i + 1]) {
                        copy[j][i] *= 2;
                        copy[j][i + 1] = 0;
                    }
                }
            }
        } else if (dir == 3) {  // 우
            for (int i = N - 1; i > 0; i--) {
                for (int j = 0; j < N; j++) {
                    if (copy[j][i] == copy[j][i - 1]) {
                        copy[j][i] *= 2;
                        copy[j][i - 1] = 0;
                    }
                }
            }
        }
        return copy;
    }

    public static int findMax(int[][] copy) {
        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (result < copy[i][j]) result = copy[i][j];
            }
        }
        return result;
    }

    public static int[][] copyOriginal() {
        int[][] copy = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }

    public static void setPermutation(int depth) {
        if (depth == 5) {
            int result = game(copyOriginal());
            answer = Math.max(result, answer);
            return;
        }
        for (int i = 0; i < 4; i++) {
            permutation[depth] = i;
            setPermutation(depth + 1);
        }
    }

}
