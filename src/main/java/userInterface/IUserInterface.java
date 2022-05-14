package userInterface;


import problemDomain.SudukoGame;

public interface IUserInterface {
     interface EventListner{
         void onSudokuInput(int x,int y,int input);
         void onDialogClick();

     }
     interface View{
         void setListner(IUserInterface.EventListner listener);
         void updateSquare(int x,int y,int input);
         void updateBoard(SudukoGame game);
         void showDialog(String message);
         void showError(String message);


     }
}
