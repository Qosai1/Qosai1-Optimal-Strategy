package com.example.project1;

import java.util.ArrayList;


/*
     * Create the Optimal Game strategy,
     * Get the maximum profit,
     * fill the Dynamic table,
     * Saves the moves that gives an optimal solution
     */
    public class OptimalGameStrategy {
        //Attributes
        private static int size;
        static int[][] dpTable;
        private static int max;
        private static ArrayList<Integer> moves;


        public OptimalGameStrategy(int[] coins) {
            size = coins.length;
            dpTable = new int[size][size];
            moves = new ArrayList<>();
            printCoins(1, coins.length - 1, coins);
            optimalGame(coins);
        }

    private void optimalGame(int[] coins) {
        // Initialize the dpTable
        for (int gap = 0; gap < size; gap++) {
            for (int i = 0, j = gap; j < size; i++, j++) {
                // Handle boundary cases where indices are out of bounds
                int x = (i + 2 <= j) ? dpTable[i + 2][j] : 0;
                int y = (i + 1 <= j - 1) ? dpTable[i + 1][j - 1] : 0;
                int z = (i <= j - 2) ? dpTable[i][j - 2] : 0;

                dpTable[i][j] = Math.max(coins[i] + Math.min(x, y),
                        coins[j] + Math.min(y, z));
            }
        }
        max = dpTable[0][size - 1];
    }

    private void printCoins(int start, int end, int[] coins) {
        while (start <= end) {
            // Calculate choices
            int choice1 = coins[start] + ((start + 2 <= end) ? Math.min(dpTable[start + 2][end], dpTable[start + 1][end - 1]) : 0);
            int choice2 = coins[end] + ((start <= end - 2) ? Math.min(dpTable[start + 1][end - 1], dpTable[start][end - 2]) : 0);

            if (choice1 >= choice2) {
                moves.add(coins[start]);
                if (start + 2 <= end && dpTable[start + 2][end] <= dpTable[start + 1][end - 1]) {
                    start += 2;
                } else {
                    start++;
                    end--;
                }
            } else {
                moves.add(coins[end]);
                if (start <= end - 2 && dpTable[start + 1][end - 1] <= dpTable[start][end - 2]) {
                    end -= 2;
                } else {
                    start++;
                    end--;
                }
            }
        }
    }

    public String printDpTable(int[] coins) {
        StringBuilder sb = new StringBuilder();
        sb.append("       ");
        for (int j = 0; j < dpTable[0].length; j++) {
            sb.append(String.format("%6d", coins[j])).append(" ");
        }
        sb.append("\n");

        for (int i = 0; i < dpTable.length; i++) {
            sb.append(String.format("%6d", coins[i]));
            for (int j = 0; j < dpTable[i].length; j++) {
                sb.append(String.format("%6d", dpTable[i][j])).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }



            /*
         * Getters and Setters
         */
        public ArrayList<Integer> getSolution() {
            return moves;
        }

        public void setSolution(ArrayList<Integer> solution) {
            this.moves = solution;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int n) {
            this.size = n;
        }

        public int[][] getDp() {
            return dpTable;
        }

        public void setDp(int[][] dp) {
            this.dpTable = dp;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public static void main(String[] args) {

        }
    }

