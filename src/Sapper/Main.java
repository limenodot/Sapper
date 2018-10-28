package Sapper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.InputStream;

import static javafx.scene.input.MouseButton.PRIMARY;

public class Main extends Application {

    public static final int WINDOW_WIDTH = 590;
    public static final int WINDOW_HIGHT = 715;
    public static final int X_CELLS = 10;
    public static final int Y_CELLS = 10;
    public static final double RAND_CONST = 0.20;
    public static int BOMBS_LEFT = 0;
    public static int BOMBS_VIEW = 0;

    public ButtonMine[][] minefield = new ButtonMine[X_CELLS][Y_CELLS];

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane root = new GridPane();
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HIGHT);

        for (int x = 0; x < X_CELLS; x++)
            for (int y = 0; y < Y_CELLS; y++) {
                ButtonMine button = new ButtonMine(x, y, Math.random() < RAND_CONST, false, false, 0);
                button.setMaxWidth(Double.MAX_VALUE);
                button.setMaxHeight(Double.MAX_VALUE);
                button.setStyle("-fx-background-color: lightgrey");
                button.setStyle("-fx-border-color: grey");
                minefield[x][y] = button;
                root.getColumnConstraints().add(new ColumnConstraints(60));
                root.getRowConstraints().add(new RowConstraints(65));
                root.add(button, x, y);
            }
        root.getRowConstraints().add(new RowConstraints(65));


        for (int i = 0; i < X_CELLS; i++)
            for (int j = 0; j < Y_CELLS; j++) {
                ButtonMine buttonMine = minefield[i][j];
                if (buttonMine.isMined() == true) {
                    ++BOMBS_LEFT;
                    ++BOMBS_VIEW;
                }
            }

        System.out.print(BOMBS_LEFT);
        String bt;
        bt = String.format("%d", BOMBS_VIEW);
        Label label = new Label(String.format(bt));
        root.add(label, 8, 10);

        for (int x = 0; x < X_CELLS; x++)
            for (int y = 0; y < Y_CELLS; y++) {
                ButtonMine button = minefield[x][y];
                long bombs = button.getNeighbours(button, minefield).stream().filter(t -> t.isMined).count();
                button.setNumberOfBombsAround((int) bombs);
            }

        for (int x = 0; x < X_CELLS; x++)
            for (int y = 0; y < Y_CELLS; y++) {
                ButtonMine button = minefield[x][y];
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        MouseButton mouseButton = event.getButton();
                        if (mouseButton == PRIMARY) {
                            if (button.isMined() == true) {
                                InputStream input = getClass().getResourceAsStream("/Pics/boom.png");
                                Image image = new Image(input);
                                ImageView imageView = new ImageView(image);
                                button.setGraphic(imageView);
                                button.setStyle("-fx-background-color: white; -fx-border-color: grey");
                                button.setActivated(true);
                                gameover(primaryStage);
                            } else {
                                button.press(minefield);
                            }
                        } else {
                            if (button.isActivated() == false) {
                                if (button.isFlagged() == false && button.isAsked() == false) {
                                    InputStream input = getClass().getResourceAsStream("/Pics/flag.png");
                                    Image image = new Image(input);
                                    ImageView imageView = new ImageView(image);
                                    button.setGraphic(imageView);
                                    button.setFlagged(true);
                                    --BOMBS_VIEW;
                                    String bt;
                                    bt = String.format("%d", BOMBS_VIEW);
                                    label.setText(bt);
                                    if (button.isMined() == true) {
                                        --BOMBS_LEFT;
                                    } else {
                                        ++BOMBS_LEFT;
                                    }
                                    if (BOMBS_LEFT == 0) {
                                        youwin(primaryStage);
                                    }
                                } else {
                                    if (button.isFlagged() == true && button.isAsked() == false) {
                                        InputStream input = getClass().getResourceAsStream("/Pics/ask.png");
                                        Image image = new Image(input);
                                        ImageView imageView = new ImageView(image);
                                        button.setGraphic(imageView);
                                        button.setFlagged(false);
                                        button.setAsked(true);
                                        ++BOMBS_VIEW;
                                        String bt;
                                        bt = String.format("%d", BOMBS_VIEW);
                                        label.setText(bt);
                                        if (button.isMined() == true) {
                                            ++BOMBS_LEFT;
                                        } else {
                                            --BOMBS_LEFT;
                                        }
                                        if (BOMBS_LEFT == 0) {
                                            youwin(primaryStage);
                                        }
                                    } else {
                                        InputStream input = getClass().getResourceAsStream("/Pics/empty.png");
                                        Image image = new Image(input);
                                        ImageView imageView = new ImageView(image);
                                        button.setGraphic(imageView);
                                        button.setFlagged(false);
                                        button.setAsked(false);
                                    }
                                }
                            } else {
                                if (button.isActivated() == true && button.isFlagged() == true) {
                                    InputStream input = getClass().getResourceAsStream("/Pics/empty.png");
                                    ++BOMBS_VIEW;
                                    String bt;
                                    bt = String.format("%d", BOMBS_VIEW);
                                    label.setText(bt);
                                    Image image = new Image(input);
                                    ImageView imageView = new ImageView(image);
                                    button.setGraphic(imageView);
                                    button.setFlagged(false);
                                    button.setAsked(false);
                                } else {
                                    if (button.isAsked() == true) {
                                        InputStream input = getClass().getResourceAsStream("/Pics/empty.png");
                                        Image image = new Image(input);
                                        ImageView imageView = new ImageView(image);
                                        button.setGraphic(imageView);
                                        button.setFlagged(false);
                                        button.setAsked(false);
                                    }
                                }
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

    public void gameover(Stage primaryStage) {
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

        Label secondLabel = new Label("Game over :(");
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);
        Scene secondScene = new Scene(secondaryLayout, 230, 100);
        Stage secondaryStage = new Stage();
        secondaryStage.initModality(Modality.WINDOW_MODAL);
        secondaryStage.initOwner(primaryStage);
        secondaryStage.setTitle("Sapper");
        secondaryStage.setScene(secondScene);
        secondaryStage.show();

        secondaryStage.setOnCloseRequest(e -> Platform.exit());
    }

    public void youwin(Stage primaryStage) {
        Label secondLabel = new Label("You win!!!\nCongratulations!");
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);
        Scene secondScene = new Scene(secondaryLayout, 230, 100);
        Stage secondaryStage = new Stage();
        secondaryStage.initModality(Modality.WINDOW_MODAL);
        secondaryStage.initOwner(primaryStage);
        secondaryStage.setTitle("Sapper");
        secondaryStage.setScene(secondScene);
        secondaryStage.show();

        secondaryStage.setOnCloseRequest(e -> Platform.exit());
    }

    public static void main(String[] args) {
        launch(args);
    }

}