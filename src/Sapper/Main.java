package Sapper;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.InputStream;

public class Main extends Application{

    public static void main(String[] args) {

        Application.launch(args);
    }
    int SIZE = 50;
    int length = SIZE;
    int width = SIZE;

    @Override
    public void start(Stage stage) {

        GridPane root = new GridPane();
        Scene scene = new Scene(root, 500, 500);

        for(int y = 0; y < length; y++) {

            for (int x = 0; x < width; x++) {
                Button button = new Button("");
                button.setMaxWidth(Double.MAX_VALUE);
                button.setMaxHeight(Double.MAX_VALUE);
                InputStream input =  getClass().getResourceAsStream("/Pics/boom.png");
                Image image = new Image(input);
                ImageView imageView = new ImageView(image);
                button.setGraphic(imageView);
                root.getColumnConstraints().add(new ColumnConstraints(50));
                root.getRowConstraints().add(new RowConstraints(50));
                root.add(button, x, y);
            }

        }
        stage.setScene(scene);
        root.setGridLinesVisible(true);

        stage.show();
    }
}