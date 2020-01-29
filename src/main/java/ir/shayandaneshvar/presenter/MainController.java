package ir.shayandaneshvar.presenter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import ir.shayandaneshvar.model.Text;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public final class MainController implements Initializable {

    private Text text;

    @FXML
    private AnchorPane root;

    @FXML
    private Label encodeLabel;

    @FXML
    private Label decodeLabel;

    @FXML
    private JFXTextArea textArea;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXButton processButton;

    @FXML
    void addressClick(MouseEvent event) {

    }

    @FXML
    void decodeClick(MouseEvent event) {

    }

    @FXML
    void encodeClick(MouseEvent event) {

    }

    @FXML
    void processClick(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text = new Text("");
        textArea.textProperty().bindBidirectional(text.getTextProperty());
    }
}
