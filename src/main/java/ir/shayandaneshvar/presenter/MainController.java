package ir.shayandaneshvar.presenter;

import com.jfoenix.controls.*;
import ir.shayandaneshvar.model.Text;
import ir.shayandaneshvar.services.ServiceProvider;
import ir.shayandaneshvar.services.persistence.CompressedFilePersistence;
import ir.shayandaneshvar.services.persistence.TextFilePersistence;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public final class MainController implements Initializable {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private String enteredPassword = "";
    private String readPassword = "";
    private boolean xtreme = false;
    private Text text;
    private ServiceProvider provider;
    private Pair<String, String> compressedRead = null;
    private Runnable handleDecode;
    private String decoded;

    {
        handleDecode = () -> {
            readPassword = provider.processor().compressedExtractor()
                    .getPassword(compressedRead);
            xtreme = provider.processor().compressedExtractor()
                    .getExtremeSecurityStatus(compressedRead);
            String result = provider.processor().compressedExtractor()
                    .extract(compressedRead);
            if (xtreme) {
                decoded = provider.security().base64().decode(result);
            } else {
                decoded = result;
            }
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

    private void handleFileClick(File file) {
        int index = file.toString().lastIndexOf(".");
        if (file.toString().substring(index)
                .equals(CompressedFilePersistence.EXTENSION())) {
            encodeDecodeToggleButton.selectedProperty().set(false);
            try {
                compressedRead = provider.persistence().compressed().read(file.toString());
                textArea.setText(compressedRead.getValue());
                executor.execute(handleDecode);
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Something went wrong!");
                alert.setContentText("Files can be corrupted or damaged!");
                alert.showAndWait();
            }
        } else if (file.toString().substring(index)
                .equals(TextFilePersistence.EXTENSION())) {
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

    @FXML
    void processClick(MouseEvent event) {
        // TODO: 1/31/2020
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
            event.setDropCompleted(true);
        }
        event.consume();
    }
}