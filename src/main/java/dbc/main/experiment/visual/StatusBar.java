package dbc.main.experiment.visual;

import dbc.engine.EngineHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatusBar extends GridPane implements Initializable {

    public static StatusBar current;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label progressText;

    @FXML
    private Label engineText;
    @FXML
    private ImageView engineImage;

    public StatusBar() {
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StatusBar.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        current = this;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stopProgress();
        if (EngineHandler.checkEngine()) {
            engineImage.setImage(new Image(getClass().getResource("/icons/bullet_green.png").toString()));
            engineText.setText("R Engine status : OK");
        } else {
            engineImage.setImage(new Image(getClass().getResource("/icons/bullet_red.png").toString()));
            engineText.setText("R Engine status : KO");
        }
    }

    public void startProgress(String s) {
        progressBar.setVisible(true);
        progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        progressText.setText(s);
    }

    public void stopProgress() {
        progressBar.setVisible(false);
        progressText.setText(null);
    }
}
