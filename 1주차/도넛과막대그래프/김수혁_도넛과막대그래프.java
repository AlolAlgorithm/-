import java.util.*;

// 처음시도
// bfs와 queue를 이용하여 해결을 시도
// queue가 비면 막대그래프
// 들어오는 간선하고 나가는 간선 2개면 8자
// 간선 두개인 노드를 발견 못해서 자기자신으로 돌아온 경우 도넛으로 했는데
// 시간초과...

// 생성한 쟁점 구하기 : 들어오는 간선 x, 나가는 간선이 2개 이상일 경우
// 도넛 : 정점의 들어오는 간선과 나가는 간선의 개수가 같다.
// 막대 : 마지막 정점은 나가는 간선이 없다.
// 8자 : 정점 중 한개는 나가는 간선이 2개


class Solution {
    public int[] solution(int[][] edges) {
        int[] answer = new int[4];
        
        Map<Integer, Integer> in = new HashMap<>();
        Map<Integer, Integer> out = new HashMap<>();
        
        // 1->2로 되어있으면 in에는 1, out에는 2를 저장
        for(int[] edge: edges){
            in.put(edge[1], in.getOrDefault(edge[1], 0)+1);
            out.put(edge[0], out.getOrDefault(edge[0], 0)+1);
        }
        
        for(int node: out.keySet()){
            // 나가는 간선 개수 2개 이상
            if(out.get(node) > 1){
                // 들어오는 간선이 없으면 => 생성한 쟁점
                if(!in.containsKey(node)){
                    answer[0] = node;
                }
                // 들어오는 간선 있으면 => 8자 그래프
                else {
                    answer[3] += 1;
                }
            }
        }
        
        // 들어오는 간선 중에서
        for(int node: in.keySet()){
            // 나가는 간선이 없으면 막대 그래프
            if(!out.containsKey(node)){
                answer[2]+=1;
            }
        }
        
        // 도넛 그래프는 생성한 정점에서 막대그래프랑 8자 더한거 빼주면 됨 
        answer[1] = out.get(answer[0]) - (answer[2] + answer[3]);
        
        
        return answer;
    }
}
