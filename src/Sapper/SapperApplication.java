package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.List;

import static javafx.scene.input.MouseButton.PRIMARY;

public class SapperApplication extends Application {

    public static final int WINDOW_WIDTH = 590;
    public static final int WINDOW_HIGHT = 640;
    public static final int X_CELLS = 10;
    public static final int Y_CELLS = 10;
    public static final double RAND_CONST = 0.10;

    public ButtonMine[][] minefield = new ButtonMine[X_CELLS][Y_CELLS];

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane root = new GridPane();
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HIGHT);

        for (int x = 0; x < X_CELLS; x++)
            for (int y = 0; y < Y_CELLS; y++) {
                ButtonMine button = new ButtonMine(x, y, Math.random() < RAND_CONST, false, false, 0, null);
                minefield[x][y] = button;
                List<ButtonMine> neighbours = button.getBombsAround(minefield);
                button.setNeighbours(neighbours);

                button.setMaxWidth(Double.MAX_VALUE);
                button.setMaxHeight(Double.MAX_VALUE);
                button.setStyle("-fx-background-color: lightgrey");
                button.setStyle("-fx-border-color: grey");

                root.getColumnConstraints().add(new ColumnConstraints(60));
                root.getRowConstraints().add(new RowConstraints(65));
                root.add(button, x, y);
            }

        for (int x = 0; x < X_CELLS; x++)
            for (int y = 0; y < Y_CELLS; y++) {
                ButtonMine button = minefield[x][y];
                long bombs = button.getBombsAround(minefield).stream().filter(t -> t.isMined).count();
                button.setBombsAround((int) bombs);
            }


        for (int x = 0; x < X_CELLS; x++)
            for (int y = 0; y < Y_CELLS; y++) {
                ButtonMine button = minefield[x][y];
                List<ButtonMine> n = button.neighbours;
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) throws NullPointerException {
                        MouseButton mouseButton = event.getButton();
                        if (mouseButton == PRIMARY) {
                            if (button.isMined()) {
                                InputStream input = getClass().getResourceAsStream("/Pics/boom.png");
                                Image image = new Image(input);
                                ImageView imageView = new ImageView(image);
                                button.setGraphic(imageView);
                                button.setStyle("-fx-background-color: white; -fx-border-color: grey");
                                button.setActivated(true);
                                gameover();
                            } else { button.press(minefield);
                                /*if (button.getNumberOfBombsAround() != 0) {
                                    int number = button.getNumberOfBombsAround();
                                    String path = String.format("/Pics/%s.png", number);
                                    InputStream input = getClass().getResourceAsStream(path);
                                    Image image = new Image(input);
                                    ImageView imageView = new ImageView(image);
                                    button.setGraphic(imageView);
                                    button.setStyle("-fx-background-color: white; -fx-border-color: grey");
                                    button.setActivated(true);
                                } else {
                                    button.setStyle("-fx-background-color: white; -fx-border-color: grey");
                                    try {
                                        button.getNeighbours().forEach(ButtonMine::press(minefield));
                                    } catch (NullPointerException npe) {
                                        System.out.println(" ");
                                    } catch (StackOverflowError soe) {
                                        System.out.println(" ");
                                    }
                                    button.setActivated(true);
                                }*/
                            }
                        } else {
                            if (button.isActivated() == false) {
                                InputStream input = getClass().getResourceAsStream("/Pics/flag.png");
                                Image image = new Image(input);
                                ImageView imageView = new ImageView(image);
                                button.setGraphic(imageView);
                                button.setStyle("-fx-border-color: grey");
                                button.setFlagged(true);
                            }
                        }
                    }
                });
            }

        primaryStage.setScene(scene);
        primaryStage.setTitle("Sapper");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void gameover() {
        for (int i = 0; i < X_CELLS; i++)
            for (int j = 0; j < Y_CELLS; j++) {
                ButtonMine buttonMine = minefield[i][j];
                if (buttonMine.isMined() && buttonMine.isActivated() == false) {
                    Image image1 = new Image("/Pics/bomb.png");
                    ImageView imageView1 = new ImageView(image1);
                    buttonMine.setGraphic(imageView1);
                    buttonMine.setStyle("-fx-background-color: white; -fx-border-color: grey");
                }
            }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
