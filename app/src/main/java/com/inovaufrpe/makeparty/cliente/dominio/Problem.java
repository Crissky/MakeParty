package com.inovaufrpe.makeparty.cliente.dominio;

public class Problem {
    private int bagSize;
    private float[] profit;
    private int[] weight;
    private int[] group;

    public Problem() {
    }

    public int getBagSize() {
        return bagSize;
    }

    public float[] getProfit() {
        return profit;
    }

    public int[] getWeight() {
        return weight;
    }

    public int[] getGroup() {
        return group;
    }

    public void setBagSize(int bagSize) {
        this.bagSize = bagSize;
    }

    public void setProfit(float[] profit) {
        this.profit = profit;
    }

    public void setWeight(int[] weight) {
        this.weight = weight;
    }

    public void setGroup(int[] group) {
        this.group = group;
    }

    public boolean getSolution(float option1, float option2, float option3) {
        return (option2 > option1) && (option2 > option3);
    }

    public boolean checkIfInSameGroup(int n, int lastTakenGroup, int[] group) {
        return group[n] == lastTakenGroup;
    }

    public float getMax(int group, float[] row, int[] groups, int n) {
        float max = 0;
        for (int i = 1; i < n; i++) {
            if (groups[i] == group) {
                if (row[i] > max) max = row[i];
            }
        }
        return max;
    }

    public boolean calculateIsMax(int n, int w, int[] groups, float[][] matrix, int N) {
        int group = groups[n];
        float max = 0;
        for (int i = 1; i <= N; i++) {
            if (groups[i] == group) {
                if (matrix[w][i] > max) max = matrix[w][i];
            }
        }
        return matrix[w][n] == max;
    }
}
