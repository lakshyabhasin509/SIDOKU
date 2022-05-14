package userInterface;

import constants.GameStates;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import problemDomain.Coordinates;
import problemDomain.SudukoGame;

import java.util.HashMap;


public class UserInterfaceImp implements IUserInterface.View,
        EventHandler<KeyEvent>{
    private final Stage stage;
    private final Group root;

private HashMap<Coordinates,SudokuTextField> textFieldCoordinates;
private IUserInterface.EventListner listner;

private static final double WINDOW_X=732;
private static final double WINDOW_Y=668;
private static final double BOARD_PADDING=50;
private static final double BOARD_X_AND_Y=576;

private static final Color WINDOW_BACKGROUND=Color.rgb(0,190,255);
private static final Color BOARD_BACKGROUND=Color.rgb(255,255,255);

    public UserInterfaceImp(Stage stage, Group root) {
        this.stage = stage;
        this.root = root;
        this.textFieldCoordinates=new HashMap<>();
        initializeUserInterface();
    }

    private void initializeUserInterface() {
        drawBackground(root);
        drawTitle(root);
        drawSudokuBoard(root);
        drawTextFields(root);
        drawGridLines(root);

        stage.show();
    }

    private void drawGridLines(Group root) {
        int xAndY=114;
        int index=0;
        int thickness;

        if(index==2 || index==5){
            thickness=3;
        }else thickness=2;

        Rectangle verticalLine=getLine(
                xAndY+64*index,
                BOARD_PADDING,
                BOARD_X_AND_Y,
                thickness
        );
        Rectangle horizontalLine=getLine(

                BOARD_PADDING,
                xAndY+64*index,
                thickness,
                BOARD_X_AND_Y

        );
        root.getChildren().addAll(
                verticalLine,
                horizontalLine);

        index++;

    }

    private Rectangle getLine(double x,
                              double y,
                              double height,
                              double width) {
        Rectangle line=new Rectangle();
        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);

        line.setFill(Color.BLACK);


        return line;
    }

    private void drawTextFields(Group root) {
        final int xOrigin=50;
        final int yOrigin=50;

        final int xAndYDelta=64;

        for (int xIndex = 0; xIndex < 9; xIndex++) {
            for (int yIndex = 0; yIndex < 9; yIndex++) {
                int x=xOrigin+xIndex+xAndYDelta;
                int y=yOrigin+yIndex+xAndYDelta;
                SudokuTextField tile=new SudokuTextField(xIndex,yIndex);
                styleSudokutile(tile,x,y);
                tile.setOnKeyPressed(this);
                textFieldCoordinates.put(new Coordinates(xIndex,yIndex),tile);

                root.getChildren().add(tile);


            }
        }

    }

    private void styleSudokutile(SudokuTextField tile,
                                 int x,
                                 int y) {
        Font numberFont=new Font(32);
        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);
        tile.setLayoutX(x);
        tile.setLayoutY(y);

        tile.setPrefHeight(64);
        tile.setPrefWidth(64);
        tile.setBackground(Background.EMPTY);

    }

    private void drawSudokuBoard(Group root) {
    Rectangle background=new Rectangle();
    background.setX(BOARD_PADDING);
    background.setY(BOARD_PADDING);
    background.setWidth(BOARD_X_AND_Y);
    background.setHeight(BOARD_X_AND_Y);

    background.setFill(BOARD_BACKGROUND);
    root.getChildren().addAll(background);



    }

    private void drawTitle(Group root) {
        Text text=new Text(235,690,"SIDOKU");
        text.setFill(Color.WHITE);
        Font textFont=new Font(43);

        text.setFont(textFont);
        root.getChildren().addAll(text);

    }

    private void drawBackground(Group root) {
        Scene scene=new Scene(root,WINDOW_X,WINDOW_Y);
        scene.setFill(WINDOW_BACKGROUND);
        stage.setScene(scene);

    }

    @Override
    public void setListner(IUserInterface.EventListner listener) {
        this.listner=listener;
    }

    @Override
    public void updateSquare(int x, int y, int input) {

        SudokuTextField tile=textFieldCoordinates.get(new Coordinates(x,y));
 String in=Integer.toString(input);

 if(in.equals("0"))in=" ";
 tile.textProperty().setValue(in);

    }

    @Override
    public void updateBoard(SudukoGame game) {

        for (int xIndex = 0; xIndex < 9; xIndex++) {
        for (int yIndex = 0; yIndex < 9; yIndex++) {
            TextField tile=textFieldCoordinates.get(new Coordinates(xIndex,yIndex));

            String value=Integer.toString(game.getGridState()[xIndex][yIndex]);

            if(value.equals("0"))value=" ";

            tile.setText(value);

            if(game.getGameState()==GameStates.NEW)
            {
                if(value.equals(""))
                {
                    tile.setStyle("-fx-opacity : 1;");
                    tile.setDisable(false);

                }else {
                    tile.setStyle("-fx-opacity : 0.8;");
                    tile.setDisable(false);

                }

            }
        }

        }
    }

    @Override
    public void showDialog(String message) {

        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,message, ButtonType.OK);
        alert.showAndWait();

    }

    @Override
    public void showError(String message) {
        Alert error=new Alert(Alert.AlertType.ERROR,message, ButtonType.OK);
        error.showAndWait();
    }
    @Override
    public void handle(KeyEvent keyEvent) {

        if(keyEvent.getEventType()==keyEvent.KEY_PRESSED)
        {
            if(keyEvent.getText().matches("[0-9]"))
            {
                int value=Integer.parseInt(keyEvent.getText());
                handleInput(value,keyEvent.getSource());
            }
            else if(keyEvent.getCode()== KeyCode.BACK_SPACE){
                handleInput(0,keyEvent.getSource());

            }else {
                ((TextField)keyEvent.getSource()).setText("");
            }
        }
        keyEvent.consume();
    }

    private void handleInput(int value, Object source) {

        listner.onSudokuInput(
                ((SudokuTextField) source).getX(),
                (((SudokuTextField) source).getY()),
                value
        );
    }
}
