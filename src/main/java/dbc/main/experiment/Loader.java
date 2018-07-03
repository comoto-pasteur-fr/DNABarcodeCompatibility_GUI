package dbc.main.experiment;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ctreb on 17/06/2018.
 */



public class Loader extends AnchorPane implements Initializable {

    private FXMLLoader loader;

    protected Loader() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    protected FXMLLoader getLoader() {
        return loader;
    }

    protected void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

}
