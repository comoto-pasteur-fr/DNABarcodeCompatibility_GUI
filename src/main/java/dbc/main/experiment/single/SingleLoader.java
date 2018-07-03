package dbc.main.experiment.single;

import dbc.main.experiment.Loader;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ctreb on 17/06/2018.
 */
public class SingleLoader extends Loader implements Initializable {

    public SingleLoader() {
        this.setLoader(new FXMLLoader(getClass().getResource("SingleG.fxml")));
        this.getLoader().setRoot(this);
        try {
            this.getLoader().load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
