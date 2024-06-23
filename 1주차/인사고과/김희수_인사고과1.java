import java.util.*;

class 김희수_인사고과1 {
    public int solution(int[][] scores) {
        int ans = 1;
        
        int atti = scores[0][0];
        int value = scores[0][1];
        
        List<int[]> list = new ArrayList<>();
        for(int i = 1; i< scores.length;i++){
            if(atti< scores[i][0] && value < scores[i][1])
                return -1;
            
            if((atti + value) < scores[i][0] + scores[i][1]){
                list.add(new int[]{scores[i][0] ,scores[i][1]});
                ans++;
            }
                
        }
        
        Collections.sort(list, (int[] s1, int[] s2)->{
           return  (s2[0] + s2[1]) - (s1[0] + s1[1]); 
        });
        
        for(int i = list.size() -1; i>=1; i--){
            int a1 = list.get(i)[0];
            int v1 = list.get(i)[1];
            
            for(int j = i - 1; j>=0; j--){
                int a2 = list.get(j)[0];
                int v2 = list.get(j)[1];
                
                if(a1 < a2 && v1< v2){
                    ans--;
                    break;
                }
            }
        }
        
       
        
        return ans;
    }
}