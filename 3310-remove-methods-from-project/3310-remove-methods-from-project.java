class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {

        List<Integer>[]graph = new ArrayList[n];

        for(int i=0;i<n;i++)
            graph[i]=new ArrayList<>();

            for(int[]edge:invocations)
            graph[edge[0]].add(edge[1]);

            boolean[] suspi=new boolean[n];

            dfs(k,graph,suspi);

            for(int[] edge:invocations){
                int u=edge[0];
                int v= edge[1];
            
            if(!suspi[u] && suspi[v]){
                List<Integer> ans=new ArrayList<>();
                for(int i=0;i<n;i++)
                ans.add(i);
                return ans;
            }
         }
        List<Integer> ans=new ArrayList<>();

        for(int i=0;i<n;i++){
            if(!suspi[i])
            ans.add(i);
        } 
return ans;
                    
    }
    private void dfs(int node,List<Integer>[] graph,boolean[]suspi){
        if(suspi[node])
        return;

        suspi[node]=true;

        for(int next:graph[node])
         dfs(next,graph,suspi);
    }
}