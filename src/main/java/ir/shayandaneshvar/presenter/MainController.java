package ir.shayandaneshvar.presenter;

import com.jfoenix.controls.*;
import ir.shayandaneshvar.model.Text;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public final class MainController implements Initializable {

    private Text text;

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

    }


    @FXML
    void fileClick(MouseEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        // TODO: 1/29/2020  
        // FIXME: 1/29/2020 
    }

    @FXML
    void processClick(MouseEvent event) {

    }

    @FXML
    void handlePassword(MouseEvent event) {

    }

    @FXML
    void handleSecurity(MouseEvent event) {

    }

    @FXML
    void handleToggle(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text = new Text("");
        textArea.textProperty().bindBidirectional(text.getTextProperty());
    }
}
