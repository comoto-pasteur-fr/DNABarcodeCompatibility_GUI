package dbc.main;


import dbc.settings.SettingsLogger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 *  Handle the path to JRI native Library
 */
public class RPathController implements Initializable {




    @FXML
    TextField path1;

    @FXML
    Label error;

    @FXML
    GridPane grid;

    private int rowNumber = 2;



    private ArrayList<TextField> textFieldArrayList;

    public RPathController() {

    }


    private static final String JAVA_LIBRARY_PATH = "java.library.path";
    private static final Logger LOGGER = LoggerFactory.getLogger(RPathController.class);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.textFieldArrayList = new ArrayList<>();
        this.textFieldArrayList.add(path1);
    }

    /**
     * Add TextField dynamically to the gridpane
     */
    @FXML
    public void addPath() {
        TextField otherPath = new TextField("");
        this.grid.addRow(rowNumber);
        this.grid.add(otherPath, 0, rowNumber);
        this.textFieldArrayList.add(otherPath);
        rowNumber++;
    }

    @FXML
    public void saveRPaths() throws LibraryException {
        SettingsLogger.set(SettingsLogger.R_HOME, this.textFieldArrayList.get(0).getText());
        for (TextField tf : textFieldArrayList) {
            try {
                Files.find(Paths.get(tf.getText()),
                        Integer.MAX_VALUE,
                        (filePath, fileAttr) -> fileAttr.isRegularFile()
                                && filePath.getFileName().endsWith("JRI.jar")
                                && !filePath.toAbsolutePath().toString().matches(".*(i386|x64).*"))
                        .forEach(f -> {
                            LOGGER.info("File : " + f.toAbsolutePath());
                            LOGGER.info("Dossier parent  " + f.toAbsolutePath().getParent());
                            SettingsLogger.set(JAVA_LIBRARY_PATH, f.toAbsolutePath().getParent().toString());
                        });

            } catch (IOException e) {
                LOGGER.error("Java library path isn't correct");
            }

        }
        String libraryPath = SettingsLogger.get(JAVA_LIBRARY_PATH);
        if (libraryPath.equals("")) {
            error.setText("Unable to set library path");
        }else
        ((Stage)this.path1.getScene().getWindow()).close();
    }

}
