package dbc.main.experiment.dual;

import dbc.main.experiment.Loader;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ctreb on 17/06/2018.
 */
public class Dual  extends Loader implements Initializable {


    public Dual() {
        this.setLoader(new FXMLLoader(getClass().getResource("DualG.fxml")));
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
