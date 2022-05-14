package userInterface.logic;

import constants.GameStates;
import constants.Messages;
import problemDomain.IStorage;
import userInterface.IUserInterface;

import java.io.IOException;

public class ControlLogic implements IUserInterface.EventListner {

    private IStorage storage;
    private IUserInterface.View view;

    public ControlLogic(IStorage storage, IUserInterface.View view) {
        this.storage = storage;
        this.view = view;
    }

    @Override
    public void onSudokuInput(int x, int y, int input) {

        try {

            SudokuGame gameData=storage.getGameData();
            int [][] newGridState = gameData.getCopyOfGridState();
            newGridState[x][y]=input;

            gameData=new SudokuGame(
                    GameLogic.checkForCompletion(newGridState),
                    newGridState
                    );
            view.updateSquare(x,y,input);
            if(gameData.getGameState()== GameStates.COMPLETE){
                view.showDialog(Messages.GAME_COMPLETE);
            }
        }
        catch (IOException e){
            e.printStackTrace();
            view.showError(Messages.ERROR);
        }
    }

    @Override
    public void onDialogClick() {

        try{

            storage.updateGameData(GameLogic.getNewgame());
            view.updateBoard(storage.getGameData());

        }catch (IOException e)
        {
            e.printStackTrace();
            view.showError(Messages.ERROR);
        }
    }
}
