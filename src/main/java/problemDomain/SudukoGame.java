package problemDomain;

import computationalLogic.SudokuUtilities;
import constants.GameStates;

import java.io.Serializable;

public class SudukoGame implements Serializable {
private final GameStates gameState;
private final int[][] gridState;
public static final int GRID=9;

//    public SudukoGame() {
//        gameState=null;
//        gridState=null;
//    }
    public SudukoGame(GameStates gameState, int[][] gridState) {
        this.gameState = gameState;
        this.gridState = gridState;
    }
    public GameStates getGameState() {
        return gameState;
    }

    public int[][] getGridState() {
        return SudokuUtilities.copyToNewArray(gridState);
    }

}
