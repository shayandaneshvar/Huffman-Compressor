package ir.shayandaneshvar.presenter;

import com.jfoenix.controls.*;
import ir.shayandaneshvar.model.Text;
import ir.shayandaneshvar.services.ServiceProvider;
import ir.shayandaneshvar.services.persistence.CompressedFilePersistence;
import ir.shayandaneshvar.services.persistence.TextFilePersistence;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
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
import java.util.Optional;
import java.util.ResourceBundle;

public final class MainController implements Initializable {
    private String pwd = "";
    private Text text;
    private ServiceProvider provider;

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
                Pair<String, String> pair = provider.persistence().compressed().
                        read(file.toString());
                textArea.setText(pair.getValue());
            } catch (IOException e) {
                e.printStackTrace();
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
        if(passwordCheckbox.isSelected()){
            // TODO: 1/31/2020
        }else {
            System.err.println("u");
        }
    }

    @FXML
    void handleSecurity(MouseEvent event) {

    }

    @FXML
    void handleToggle(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        provider = ServiceProvider.provide();
        text = new Text("");
        textArea.textProperty().bindBidirectional(text.getTextProperty());
    }
}
