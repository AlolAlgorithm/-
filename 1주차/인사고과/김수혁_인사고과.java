import java.util.Arrays;

class Solution {
    public int solution(int[][] scores) {
        int answer = 1;
        
        int size = scores.length;
        int[] wh = {scores[0][0], scores[0][1]};
        
       Arrays.sort(scores, (s1, s2) -> {
            if(s1[0] == s2[0])
                return s1[1] - s2[1];
            else
                return s2[0] - s1[0];
        });
        
        int maxScore = scores[0][1];

        for(int i = 0; i < scores.length; i++){
            if(scores[i][1] < maxScore){
                if(scores[i][0] == wh[0] && scores[i][1] == wh[1])
                    return -1;
                else{
                    scores[i][0] = -1;
                    scores[i][1] = -1;
                }
            }
            else
                maxScore = scores[i][1];
        }
        
       Arrays.sort(scores, (s1, s2) -> (s2[0] + s2[1]) - (s1[0] + s1[1]));

        for(int i = 0; i < scores.length; i++){
            if(scores[i][0] + scores[i][1] > wh[0] + wh[1])
                answer++;
            else
                break;
        }
        return answer;
    }
}
