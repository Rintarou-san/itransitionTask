package com.mycompany.gameproject;

public class GameRule {

    int[][] combinations;
    int size;

    public final int WIN = 0;
    public final int LOSE = -1;
    public final int DRAW = 3;

    public GameRule(int size) {
        this.size = size;
        combinations = new int[size][size];
        for (int i = 0; i < size; i++) {
            combinations[i][i] = 3;
            for (int j = 0; j < (size - 1) / 2; j++) {
                if (i + j + 1 >= size) {
                    combinations[i][i + j + 1 - size] = -1;
                } else {
                    combinations[i][i + j + 1] = -1;
                }
            }
        }
    }

    public int getWinner(int first, int second) {
        return combinations[first][second];
    }

    public void showCombinations() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(String.format("%5d", combinations[i][j]));
            }
            System.out.println();
        }
    }
}
