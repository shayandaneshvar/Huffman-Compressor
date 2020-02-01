package ir.shayandaneshvar;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

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
    public void start(Stage primaryStage) throws IOException {
        Scene scene = new Scene(root, root.getPrefWidth() - 20,
                root.getPrefHeight()
                , true, SceneAntialiasing.BALANCED);
        primaryStage.getIcons().add(new Image("img/512.png"));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Huffman Text Compressor");
        primaryStage.show();
        if (args.length == 0) {
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initOwner(primaryStage);
            stage.initModality(Modality.WINDOW_MODAL);
            try {
                URL url = new File("src/main/java/ir/shayandaneshvar/view/splash" +
                        ".fxml").toURI().toURL();
                AnchorPane spl = FXMLLoader.load(url);
                Scene splash = new Scene(spl, spl.getPrefWidth(),
                        spl.getPrefHeight(), true, SceneAntialiasing.BALANCED);
                stage.setScene(splash);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> stage.close());
                    }
                }, 3200);
                stage.show();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}