import java.util.*;
/**
* [문제 설명]
* 도넛 모양 : n개의 정점, n개의 간선 / 간선을 따라가면 처음으로 되돌아옴
* 막대 모양 : n개의 정점, n-1개의 간선
* 8자 모양 : 2n+1개의 정점, 2n+2개의 간선 / 하나의 정점은 2개의 외부차수 ,내부 차수를 가지고 있음
* -> 간선의 정보가 주어질 때, 생성한 정점의 번호, 각각 그래프 개수를 구하라
* -> 생성한 정점의 번호 : 그래프를 연결하는 중간 정점
* [제한 사항]
* 도넛모양, 막대모양, 8자모양 그래프 수의 합은 2이상 -> 즉 사이에 무조건 생성한 정점이 존재
* [문제 해결 프로세스]
* 1. 생성된 정점 : outDegree만 있는 것
* 2. 도넛 : 모든 정점 in 1 out 1 -> 생성된 정점의 out은 그래프의 개수이므로 도넛을 제외한 나머지 그래프를 빼주면 도넛의 개수가 됨
* 3. 막대 : in 1 out 0 -> 들어오는 것은 있는데 나가는 것은 없음
* 4. 8자 : 정점 중 하나가 in 2개 out 2개이다. -> 나가는 것이 2개 이상이면서 들어오는 것이 있으면 됨
*/
class Solution {
    public static HashMap<Integer, Integer> in = new HashMap<>();
    public static HashMap<Integer, Integer> out = new HashMap<>();
    public int[] solution(int[][] edges) {
        int[] answer = new int[4];
        
        for(int[] edge : edges) {
            out.put(edge[0], out.getOrDefault(edge[0], 0) + 1);
            in.put(edge[1], in.getOrDefault(edge[1], 0) + 1);
        }
        
        // 생성된 정점 찾기 -> in은 0, out은 2이상
        for(int vertex : out.keySet()) {
            if(out.get(vertex) >= 2) {
                if(!in.containsKey(vertex)) answer[0] = vertex;
                else answer[3]++; //8자 찾기
            }
        }
        
        // 막대 찾기
        for(int vertex : in.keySet()) {
            if(in.get(vertex) > 0) {
                if(!out.containsKey(vertex)) answer[2]++;
            }
        }

        // 도넛 찾기
        answer[1] = out.get(answer[0]) - answer[2] - answer[3]   ;           
        return answer;
    }
    
    
}
