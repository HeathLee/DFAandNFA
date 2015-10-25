import java.util.*;
import java.io.*;

class NFA {
    private boolean[] alreadyOn; //记录已经添加到newsStateSet的状态
    private Stack<Integer> newStateSet = new Stack<>();  //下一个状态集合
    private Stack<Integer> oldStateSet = new Stack<>(); //当前状态集合

    /**
     *
     * @param move 转台转移表
     * @param accept_state 接受状态集合
     * @param word 待判断字符串
     * @return 是否能被NFA所识别
     */
    boolean recognizeString(int move[][][], int accept_state[], String word) {

        //初始化
        alreadyOn = new boolean[move.length];
        for (int i = 0; i < move.length; ++i) {
            alreadyOn[i] = false;
        }

        //构造初始状态集合s0
        oldStateSet.push(0);
        for (int i : move[0][0]) {
            oldStateSet.push(i);
        }

        for (int i = 0; i < word.length(); i++) {
            while (!oldStateSet.empty()) {
                int s = oldStateSet.peek();
                for (int j : move[s][word.charAt(i) - 'a' + 1]) {
                    if (!alreadyOn[j]) {
                        addState(j, move);
                    }
                }
                oldStateSet.pop();
            }
            while (!newStateSet.empty()) {
                int s= newStateSet.peek();
                oldStateSet.push(newStateSet.pop());
                alreadyOn[s] = false;
            }
        }

        while (!oldStateSet.empty()) {
            int s = oldStateSet.pop();
            for (int i = 0; i < accept_state.length; ++i) {
                if (s == accept_state[i]) {
                    return true;
                }
            }
        }
        return false;
    }

    private void addState(int i, int move[][][]) {
        if (!alreadyOn[i]) {
            newStateSet.push(i);
            alreadyOn[i] = true;
        }
        for (int j : move[i][0]) {
            if (!alreadyOn[j]) {
                addState(j, move);
            }
        }
    }

//    private Vector<Integer> closure(int[] init_state, int move[][][]) {
//        Vector<Integer> result = new Vector<>();
//        Stack<Integer> s = new Stack<>();
//        boolean[] added = new boolean[50];
//        for (int i = 0; i < 50; ++i) {
//            added[i] = false;
//        }
//        for (int i = 0; i < init_state.length; i++) {
//            s.push(init_state[i]);
//            result.add(init_state[i]);
//            added[init_state[i]] = true;
//        }
//        while (!s.empty()) {
//            int top = s.pop();
//            for (int i = 0; i < move[top][0].length; i++) {
//                if (!added[move[top][0][i]]) {
//                    s.push(top);
//                    added[move[top][0][i]] = true;
//                    result.add(top);
//                }
//            }
//        }
//        return result;
//    }



    public static void main(String args[]) throws IOException {
        int n, m;
        BufferedReader in = new BufferedReader(new FileReader("NFA.in"));
        StringTokenizer st = new StringTokenizer(in.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        while (n != 0) {
            int[][][] move = new int[n][m][];
            for (int i = 0; i < n; i++) {
                String line = in.readLine();
                int k = 0;
                for (int j = 0; j < m; j++) {
                    while (line.charAt(k) != '{') k++;
                    k++;
                    String states = "";
                    while (line.charAt(k) != '}') {
                        states = states + line.charAt(k);
                        k++;
                    }
                    states = states.trim();
                    if (states.length() > 0) {
                        String[] stateArray = states.split(",");
                        move[i][j] = new int[stateArray.length];
                        for (int l = 0; l < move[i][j].length; l++)
                            move[i][j][l] = Integer.parseInt(stateArray[l].trim());
                    } else move[i][j] = new int[0];
                }
            }
            String[] temp = in.readLine().split("\\s");
            int[] accept = new int[temp.length];
            for (int i = 0; i < accept.length; i++)
                accept[i] = Integer.parseInt(temp[i]);
            String word = in.readLine();
            while (word.compareTo("#") != 0) {
                NFA nfa = new NFA();
                if (nfa.recognizeString(move, accept, word))
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