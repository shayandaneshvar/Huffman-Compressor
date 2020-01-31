package ir.shayandaneshvar.presenter;

import com.jfoenix.controls.*;
import ir.shayandaneshvar.Main;
import ir.shayandaneshvar.model.Text;
import ir.shayandaneshvar.services.ServiceProvider;
import ir.shayandaneshvar.services.persistence.CompressedFilePersistence;
import ir.shayandaneshvar.services.persistence.TextFilePersistence;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public final class MainController implements Initializable {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private String enteredPassword = "";
    private String readPassword = "";
    private BooleanProperty xtreme = new SimpleBooleanProperty(false);
    private Text text;
    private ServiceProvider provider;
    private Pair<String, String> compressedRead = null;
    private Callable<Boolean> handleDecode;
    private String decoded = null;

    {
        handleDecode = () -> {
            readPassword = provider.processor().compressedExtractor()
                    .getPassword(compressedRead);
            xtreme.setValue(provider.processor().compressedExtractor()
                    .getExtremeSecurityStatus(compressedRead));
            String result = provider.processor().compressedExtractor()
                    .extract(compressedRead);
            System.err.println("result:" + result);
            if (xtreme.get()) {
                decoded = provider.security().base64().decode(result);
            } else {
                decoded = result;
            }
            return true;
        };
    }

    @FXML
    private AnchorPane root;

    @FXML
    private Label encodeLabel;

    @FXML
    private JFXTextArea textArea;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXButton processButton;

    @FXML
    private JFXToggleButton encodeDecodeToggleButton;

    @FXML
    private JFXCheckBox passwordCheckbox;

    @FXML
    private JFXCheckBox securityCheckbox;

    @FXML
    void addressClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File file = new File(address.getText());
            if (!address.getText().isEmpty() && file.exists()) {
                directoryChooser.setInitialDirectory(file);
            } else {
                address.setText("");
            }
            File selectedDirectory = directoryChooser.showDialog(getStage());
            if (selectedDirectory != null && selectedDirectory.exists()) {
                address.setText(selectedDirectory.toString());
                TextInputDialog dialog = new TextInputDialog("new-file");
                dialog.setTitle("Name Chooser");
                dialog.setHeaderText("Choose a name!");
                dialog.setContentText("Result will be saved in a file with " +
                        "this name at the chosen directory: ");
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    address.setText(new File(address.getText() +
                            "/" + result.get() + getExtension()).toString());
                } else {
                    address.setText("");
                }
            }
        }
    }


    @FXML
    void fileClick(MouseEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) handleFileClick(file);
    }

    private void handleFileClick(File file) throws StringIndexOutOfBoundsException{
        int index = file.toString().lastIndexOf(".");
        if (index <= 0) {
            somethingWentWrong();
        }
        compressedRead = null;
        if (file.toString().substring(index)
                .equals(CompressedFilePersistence.EXTENSION())) {
            encodeDecodeToggleButton.selectedProperty().set(false);
            try {
                handleDecoding(file);
            } catch (IOException e) {
                e.printStackTrace();
                somethingWentWrong();
            }
        } else if (file.toString().substring(index)
                .equals(TextFilePersistence.EXTENSION())) {
            decoded = null;
            encodeDecodeToggleButton.selectedProperty().set(true);
            textArea.setText(provider.persistence().text().read(file.toString()));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Format not supported!");
            alert.setContentText("Files can be in .txt or .shct format");
            alert.showAndWait();
        }
    }

    private void somethingWentWrong() {
        Platform.runLater(()->{
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong!");
        alert.setContentText("Files can be corrupted or damaged!");
        alert.showAndWait();
        });
    }

    private boolean handleDecoding(File file) throws IOException {
        compressedRead = provider.persistence().compressed().read(file.toString());
        textArea.setText(compressedRead.getValue());
        try {
            boolean result = executor.submit(handleDecode).get();
            if (readPassword.length() != 0) {
                passwordCheckbox.selectedProperty().set(true);
            } else {
                passwordCheckbox.selectedProperty().set(false);
            }
            return result;
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
    }

    @FXML
    void processClick(MouseEvent event) {
        File file = new File(address.getText());
        boolean error;
        try {
            error = !file.getParentFile().exists();
        } catch (NullPointerException e) {
            error = false;
        }
        if (error) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Such Directory");
            alert.setHeaderText("Wrong destination address");
            alert.setContentText("The selected directory doesn't exist!");
            alert.showAndWait();
            addressClick(event);
            return;
        }
        if (!encodeDecodeToggleButton.selectedProperty().get()) {
            if (compressedRead == null) {
                provider.persistence().text().write(address.getText() + ".txt", text.getText());
                try {
                    handleDecoding(new File(address.getText() + ".txt"));
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Something went wrong!");
                    alert.setContentText("Files can be corrupted or damaged!");
                    alert.showAndWait();
                    return;
                }
            }
            if (isDecodePermitted()) {
                textArea.setText(decoded);
                provider.persistence().text().write(address.getText(), decoded);
                return;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Wrong Password");
                alert.setContentText("The Entered password Doesn't Match!");
                alert.showAndWait();
                handlePassword(event);
            }
        } else {
            //fixme => chain of responsibility dp
            JFXProgressBar progressBar = new JFXProgressBar(0.01);
            progressBar.setPrefWidth(200);
            progressBar.setPrefHeight(50);
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initOwner(getStage());
            Scene scene = new Scene(progressBar);
            stage.setScene(scene);
            stage.show();
            executor.execute(() -> {
                progressBar.setProgress(0.10);
                String text = textArea.getText();
                float initialSize = text.getBytes().length;
                progressBar.setProgress(0.20);
                if (xtreme.get()) {
                    text = provider.security().base64().encode(text);
                }
                progressBar.setProgress(0.30);
                Map<Character, Integer> count = provider.processor().textExtractor()
                        .extract(text);
                progressBar.setProgress(0.45);
                Map<String, String> map = provider.processor().huffmanEncoder().extract(count);
                progressBar.setProgress(0.55);
                text = provider.processor().huffmanEncoder().encode(text, map);
                progressBar.setProgress(0.75);
                text = provider.processor().huffmanEncoder().appendDicToCipher(map, text);
                progressBar.setProgress(0.8);
                String header = provider.processor().compressedExtractor()
                        .makeHeader(xtreme.get(), enteredPassword);
                progressBar.setProgress(0.9);
                provider.persistence().compressed().write(address.getText(), header, text);
                progressBar.setProgress(0.95);
                Platform.runLater(() -> {
                    Alert alert =
                            new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Done!");
                    alert.setHeaderText("Decoding Complete!");
                    float size = file.length();
                    float compressionRatio = size / initialSize * 100;
                    progressBar.setProgress(1);
                    alert.setContentText("Compression Ratio:" + compressionRatio);
                    stage.close();
                    alert.showAndWait();
                });
            });
        }
    }

    private boolean isDecodePermitted() {
        return readPassword.equals(enteredPassword);
    }

    private Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }

    private String getExtension() {
        if (encodeDecodeToggleButton.selectedProperty().get()) {
            return CompressedFilePersistence.EXTENSION();
        } else {
            return TextFilePersistence.EXTENSION();
        }
    }

    @FXML
    void handlePassword(MouseEvent event) {
        enteredPassword = "";
        if (passwordCheckbox.isSelected()) {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Password");
            dialog.setHeaderText("Enter Password:");
            dialog.setContentText(encodeDecodeToggleButton.selectedProperty()
                    .getValue() ? "Enter your Desired Password" : "The Selected " +
                    "File Requires a password to Open");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                enteredPassword = provider.security().md5().getHashedValue(result.get());
            } else {
                if (!encodeDecodeToggleButton.selectedProperty()
                        .getValue()) {
                    handlePassword(event);
                } else {
                    passwordCheckbox.selectedProperty().set(false);
                }
            }
        }
    }

    @FXML
    void handleToggle(MouseEvent event) {
        StringTokenizer tokenizer = new StringTokenizer(text.getText(), "=>");
        if ((tokenizer.countTokens() > 3 && encodeDecodeToggleButton
                .selectedProperty().get()) || tokenizer.countTokens() == 0 && !encodeDecodeToggleButton
                .selectedProperty().get()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Warning : This might be the wrong move!");
            Optional<ButtonType> result = alert.showAndWait();
            if (!result.isPresent()) {
                encodeDecodeToggleButton.selectedProperty().setValue(
                        !encodeDecodeToggleButton.selectedProperty().getValue());
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        provider = ServiceProvider.provide();
        text = new Text("");
        textArea.textProperty().bindBidirectional(text.getTextProperty());
        xtreme.bindBidirectional(securityCheckbox.selectedProperty());
        encodeDecodeToggleButton.selectedProperty().set(true);
        if (Main.getArgs().length > 0) {
            File file = new File(Main.getArgs()[0]);
            executor.execute(() -> {
                if (root == null) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handleFileClick(file);
            });
        }
    }

    @FXML
    void handleDragDrop(DragEvent event) {
        System.err.println("b");
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            List<File> files = db.getFiles().stream().filter(x -> x.toString()
                    .endsWith(".txt") || x.toString().endsWith(".shct")).collect(Collectors.toList());
            if (!files.isEmpty()) {
                handleFileClick(files.get(0));
            }
//            event.setDropCompleted(true);
        }
        event.consume();
    }
}