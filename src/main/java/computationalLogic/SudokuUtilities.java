package computationalLogic;

import problemDomain.SudukoGame;

public class SudokuUtilities {
    public static void copySudokuArrayValues(
            int [][] oldArray,
            int [][]newArray)
    {

        for (int xIndex = 0; xIndex < SudukoGame.GRID; xIndex++) {
            for (int yIndex = 0; yIndex < SudukoGame.GRID; yIndex++) {

                newArray[xIndex][yIndex]=oldArray[xIndex][yIndex];


            }
        }
    }

    public static int[][] copyToNewArray(int[][] oldArray){
        int[][] newArray=new int[SudukoGame.GRID][SudukoGame.GRID];

        for (int i = 0; i < SudukoGame.GRID; i++) {
            for (int j = 0; j < SudukoGame.GRID; j++) {
                newArray[i][j]=oldArray[i][j];
            }
        }
        return newArray;
    }
}
