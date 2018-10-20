package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SapperApplication extends Application {

    private static final int WINDOW_WIDTH = 605;
    private static final int WINDOW_HIGHT = 650;
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HIGHT = 10;
    private Object Button;

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane root = new GridPane();
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HIGHT);
        
        //TODO запиливаем двумерный массив из кнопок в первом цикле
        //TODO во втором цикле их прорисовываем, попутно вешая на них действия
        /*ниже это организовано не так, надо пофиксить
          в массиве minefield хранятся данные о каждой ячейке с кнопкой
              (координата х, координата у, и булеевская переменная с информацией о наличии бомбы)
          массив с кнопками пока не смог создать и честно хз как это можно реализовать, но идея мне кажется хорошей
        */

        List<Cell> minefield = new ArrayList<>();

        for (int y = 0; y < GRID_HIGHT; y++)
            for (int x = 0; x < GRID_WIDTH; x++) {
                Button button = new ButtonMine(false);
                button.setMaxWidth(Double.MAX_VALUE);
                button.setMaxHeight(Double.MAX_VALUE);

                Random random = new Random();
                int number = random.nextInt(150);
                if (number % 5 == 0) {
                    ((ButtonMine) button).setMined(true);
                    minefield.add(new Cell(x, y, ((ButtonMine) button).isMined()));
                }

                /*
                InputStream input = getClass().getResourceAsStream("/Pics/boom.png");
                Image image = new Image(input);
                ImageView imageView = new ImageView(image);
                button.setGraphic(imageView);
                */

                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (((ButtonMine) button).isMined()) {
                            InputStream input = getClass().getResourceAsStream("/Pics/boom.png");
                            Image image = new Image(input);
                            ImageView imageView = new ImageView(image);
                            button.setGraphic(imageView);
                        }
                        else {

                        }
                    }
                });

                root.getColumnConstraints().add(new ColumnConstraints(60));
                root.getRowConstraints().add(new RowConstraints(65));
                root.add(button, x, y);
            }

        primaryStage.setScene(scene);
        primaryStage.setTitle("Sapper");
        primaryStage.setResizable(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
