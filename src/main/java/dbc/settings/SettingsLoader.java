package dbc.settings;


import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ctreb on 17/06/2018.
 */
public class SettingsLoader extends AnchorPane implements Initializable {

    private Parent parent;

    public SettingsLoader() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Settings.fxml"));

        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final Parent getTheParent() {
        return parent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
