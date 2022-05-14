package com.example.sidoku;

import javafx.application.Application;
import javafx.stage.Stage;
import userInterface.IUserInterface;
import userInterface.UserInterfaceImp;

import java.io.IOException;

public class SudokuApp extends Application {
    private IUserInterface.View uiImpl;
    @Override
    public void start(Stage stage) throws IOException {
        uiImpl=new UserInterfaceImp(primaryStage);
        try{
            SudukoBuidLogic.build(uiImpl);
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        launch();
    }
}