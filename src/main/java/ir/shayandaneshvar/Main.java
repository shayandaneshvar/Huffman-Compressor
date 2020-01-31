package ir.shayandaneshvar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public final class Main extends Application {
    private AnchorPane root;
    private static String[] args;

    public static void main(String[] args) {
        Main.args = args;
        System.out.println(Arrays.deepToString(args));
        launch();
    }

    public static String[] getArgs() {
        return args;
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }

    @Override
    public void init() {
        try {
            URL url = new File("src/main/java/ir/shayandaneshvar/view/main.fxml").toURI().toURL();
            System.out.println(url);
            root = FXMLLoader.load(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Exception in URL");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Exception while loading fxml");
        }
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(root, root.getPrefWidth() - 20,
                root.getPrefHeight()
                , true, SceneAntialiasing.BALANCED);
        primaryStage.getIcons().add(new Image("img/512.png"));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Huffman Text Compressor");
        primaryStage.show();
    }
}