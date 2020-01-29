package ir.shayandaneshvar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public final class Main extends Application {
    private AnchorPane root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        try {
            URL url = new File("src/main/java/ir/shayandaneshvar/view" +
                    "/main.fxml").toURI().toURL();
            root = (AnchorPane) FXMLLoader.load(url);

        } catch (MalformedURLException e) {
            throw new RuntimeException("Exception in URL");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Exception while loading fxml");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight()
                , true, SceneAntialiasing.BALANCED);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Huffman Text Compressor");
        primaryStage.show();
    }
}
