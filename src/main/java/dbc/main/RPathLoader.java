package dbc.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by ctreb on 17/06/2018.
 */
public class RPathLoader extends AnchorPane {

    private Parent parent;

    RPathLoader() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RPaths.fxml"));
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final Parent getTheParent() {
        return parent;
    }




}
