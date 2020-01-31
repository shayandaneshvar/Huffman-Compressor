package ir.shayandaneshvar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public final class Main extends Application {
    private AnchorPane root;

    public static void main(String[] args) {
        launch(args);
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
//            URL url = new File("classpath:view/main.fxml").toURI().toURL();
//            URL url = new URL("classpath:ir/shayandaneshvar");
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

    String fx = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "\n" +
            "<?import com.jfoenix.controls.JFXButton?>\n" +
            "<?import com.jfoenix.controls.JFXCheckBox?>\n" +
            "<?import com.jfoenix.controls.JFXTextArea?>\n" +
            "<?import com.jfoenix.controls.JFXTextField?>\n" +
            "<?import com.jfoenix.controls.JFXToggleButton?>\n" +
            "<?import javafx.scene.control.Label?>\n" +
            "<?import javafx.scene.control.Tooltip?>\n" +
            "<?import javafx.scene.effect.DropShadow?>\n" +
            "<?import javafx.scene.effect.Glow?>\n" +
            "<?import javafx.scene.layout.AnchorPane?>\n" +
            "<?import javafx.scene.paint.LinearGradient?>\n" +
            "<?import javafx.scene.paint.Stop?>\n" +
            "<?import javafx.scene.text.Font?>\n" +
            "\n" +
            "<AnchorPane fx:id=\"root\" prefHeight=\"576.0\" prefWidth=\"720.0\" style=\"-fx-background-color: #212125;\" xmlns=\"http://javafx.com/javafx/8.0.171\" xmlns:fx=\"http://javafx.com/fxml/1\" fx:controller=\"ir.shayandaneshvar.presenter.MainController\">\n" +
            "   <children>\n" +
            "      <Label fx:id=\"fileLabel\" alignment=\"CENTER\" onDragDropped=\"#handleDragDrop\" onDragOver=\"#handleDragDrop\" onMouseClicked=\"#fileClick\" prefHeight=\"134.0\" prefWidth=\"720.0\" style=\"-fx-background-color: #239d60;\" text=\"Choose/Drag a File Here....\" textAlignment=\"CENTER\" textFill=\"#f7f39a\" wrapText=\"true\">\n" +
            "         <font>\n" +
            "            <Font name=\"HelveticaRounded-Bold\" size=\"24.0\" />\n" +
            "         </font>\n" +
            "         <effect>\n" +
            "            <DropShadow blurType=\"ONE_PASS_BOX\" />\n" +
            "         </effect>\n" +
            "      </Label>\n" +
            "      <JFXTextArea fx:id=\"textArea\" labelFloat=\"true\" layoutX=\"20.0\" layoutY=\"192.0\" onDragDropped=\"#handleDragDrop\" prefHeight=\"322.0\" prefWidth=\"680.0\" style=\"-fx-background-color: #a3de83;\" unFocusColor=\"#239d60\" wrapText=\"true\">\n" +
            "         <effect>\n" +
            "            <Glow />\n" +
            "         </effect>\n" +
            "         <focusColor>\n" +
            "            <LinearGradient endX=\"1.0\" endY=\"1.0\">\n" +
            "               <stops>\n" +
            "                  <Stop color=\"#f7f39a\" />\n" +
            "                  <Stop color=\"#f7f39a\" offset=\"0.007662835249042145\" />\n" +
            "                  <Stop color=\"#239d60\" offset=\"1.0\" />\n" +
            "               </stops>\n" +
            "            </LinearGradient>\n" +
            "         </focusColor>\n" +
            "         <font>\n" +
            "            <Font name=\"HelveticaRounded-Bold\" size=\"16.0\" />\n" +
            "         </font>\n" +
            "         <tooltip>\n" +
            "            <Tooltip autoHide=\"true\" opacity=\"0.7\" text=\"Enter Text Here:\">\n" +
            "               <font>\n" +
            "                  <Font name=\"HelveticaRounded-Bold\" size=\"12.0\" />\n" +
            "               </font>\n" +
            "            </Tooltip>\n" +
            "         </tooltip>\n" +
            "      </JFXTextArea>\n" +
            "      <JFXTextField fx:id=\"address\" layoutX=\"20.0\" layoutY=\"530.0\" onMouseClicked=\"#addressClick\" prefHeight=\"29.0\" prefWidth=\"544.0\" promptText=\"Destination: right click to change with file chooser-use left click to edit\" style=\"-fx-background-color: #a3de83;\" unFocusColor=\"#239d60\">\n" +
            "         <effect>\n" +
            "            <Glow />\n" +
            "         </effect>\n" +
            "         <focusColor>\n" +
            "            <LinearGradient endX=\"1.0\" endY=\"1.0\">\n" +
            "               <stops>\n" +
            "                  <Stop color=\"#f7f39a\" />\n" +
            "                  <Stop color=\"#239d60\" offset=\"1.0\" />\n" +
            "               </stops>\n" +
            "            </LinearGradient>\n" +
            "         </focusColor>\n" +
            "         <font>\n" +
            "            <Font name=\"Helvetica-Bold\" size=\"16.0\" />\n" +
            "         </font>\n" +
            "      </JFXTextField>\n" +
            "      <JFXButton fx:id=\"processButton\" buttonType=\"RAISED\" layoutX=\"580.0\" layoutY=\"530.0\" onMouseClicked=\"#processClick\" prefHeight=\"32.0\" prefWidth=\"120.0\" style=\"-fx-background-color: #239d60;\" text=\"Process\" textFill=\"#f7f39a\">\n" +
            "         <ripplerFill>\n" +
            "            <LinearGradient endX=\"1.0\" endY=\"1.0\">\n" +
            "               <stops>\n" +
            "                  <Stop color=\"#f7f39a\" />\n" +
            "                  <Stop color=\"#f7f39a\" offset=\"0.007662835249042145\" />\n" +
            "                  <Stop color=\"#239d60\" offset=\"1.0\" />\n" +
            "               </stops>\n" +
            "            </LinearGradient>\n" +
            "         </ripplerFill>\n" +
            "         <font>\n" +
            "            <Font name=\"HelveticaRounded-Bold\" size=\"16.0\" />\n" +
            "         </font>\n" +
            "      </JFXButton>\n" +
            "      <JFXToggleButton fx:id=\"encodeDecodeToggleButton\" layoutX=\"20.0\" layoutY=\"134.0\" onMouseClicked=\"#handleToggle\" prefHeight=\"46.0\" prefWidth=\"183.0\" text=\"Decode/Encode\" textAlignment=\"CENTER\" textFill=\"#f7f39a\" toggleColor=\"#239d60\" toggleLineColor=\"#a3de83\" unToggleColor=\"#a3de83\" unToggleLineColor=\"#f7f39a\">\n" +
            "         <font>\n" +
            "            <Font name=\"HelveticaRounded-Bold\" size=\"12.0\" />\n" +
            "         </font>\n" +
            "      </JFXToggleButton>\n" +
            "      <JFXCheckBox fx:id=\"passwordCheckbox\" checkedColor=\"#239d60\" layoutX=\"414.0\" layoutY=\"154.0\" onMouseClicked=\"#handlePassword\" text=\"Use Password\" textFill=\"#f7f39a\" unCheckedColor=\"#a3de83\">\n" +
            "         <font>\n" +
            "            <Font name=\"HelveticaRounded-Bold\" size=\"12.0\" />\n" +
            "         </font>\n" +
            "      </JFXCheckBox>\n" +
            "      <JFXCheckBox fx:id=\"securityCheckbox\" checkedColor=\"#239d60\" layoutX=\"564.0\" layoutY=\"154.0\" text=\"Xtreme Security\" textAlignment=\"CENTER\" textFill=\"#f7f39a\" unCheckedColor=\"#a3de83\">\n" +
            "         <font>\n" +
            "            <Font name=\"HelveticaRounded-Bold\" size=\"12.0\" />\n" +
            "         </font>\n" +
            "      </JFXCheckBox>\n" +
            "   </children>\n" +
            "</AnchorPane>\n";
}