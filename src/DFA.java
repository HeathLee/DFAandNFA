import java.util.*;
import java.io.*;

class DFA {
    /**
     *
     * @param move 状态转移表
     * @param accept_state 接受状态集合
     * @param word 要匹配的字符串
     * @return 匹配结果
     */
    boolean recognizeString(int move[][], int accept_state[], String word) {
        //初始状态
        int nextState = 0;
        //更新状态
        for (int i = 0; i < word.length(); i++) {
            nextState = move[nextState][word.charAt(i) - 'a'];
        }
        for (int i = 0; i < accept_state.length; i++) {
            if (nextState == accept_state[i]) {
                return true;
            }
        }
        return false;
    }

    public static void main(String args[]) throws IOException {
        int n, m;
        BufferedReader in = new BufferedReader(new FileReader("DFA.in"));
        StringTokenizer st = new StringTokenizer(in.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        while (n != 0) {
            int[][] move = new int[n][m];
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(in.readLine());
                for (int j = 0; j < m; j++)
                    move[i][j] = Integer.parseInt(st.nextToken());
            }
            String[] temp = in.readLine().split("\\s");
            int[] accept = new int[temp.length];
            for (int i = 0; i < accept.length; i++)
                accept[i] = Integer.parseInt(temp[i]);
            String word = in.readLine();
            while (word.compareTo("#") != 0) {
                DFA dfa = new DFA();
                if (dfa.recognizeString(move, accept, word))
                    System.out.println("YES");
                else System.out.println("NO");
                word = in.readLine();
            }
            st = new StringTokenizer(in.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
        }
    }
}