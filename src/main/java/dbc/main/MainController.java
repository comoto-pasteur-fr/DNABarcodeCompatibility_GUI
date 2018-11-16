package dbc.main;

import dbc.engine.EngineHandler;
import dbc.main.experiment.ExperimentController;
import dbc.main.experiment.dual.Dual;
import dbc.main.experiment.single.SingleLoader;
import dbc.main.experiment.visual.htmlOutput.DocumentationPage;
import dbc.main.experiment.visual.htmlOutput.TutorialPage;
import dbc.settings.SettingsLogger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class MainController implements Initializable{


    private ExperimentController experimentController;

    public static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    public void onFileClose() {
        LOGGER.info("Closing app ..");
        if (EngineHandler.exist()) {
            System.out.println("engine status : " + EngineHandler.exist());
            EngineHandler.killEngine();
        }
        Platform.exit();
    }

    public void onConfigurationReset() throws BackingStoreException {
        LOGGER.warn("Reset Prefs !!");
        Preferences.userRoot().clear();
    }

    @FXML
    public void exportLog(){
        this.experimentController.exportLog();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public String chooseExperiment() {
        LOGGER.info("Showing experiment choice dialog ...");

        ChoiceDialog<String> alert;
        alert = new ChoiceDialog<>("", "Single", "Dual", "R Settings");
        alert.setTitle("Choose an experiment");
        alert.setHeaderText("Single or Dual indexing ? ");
        alert.setResizable(false);


        final Optional<String> result = alert.showAndWait();
        if (result.isPresent()) {
            final String path = result.orElse("EMPTY");
            LOGGER.info("Choosing : {}", path);
            SettingsLogger.set(SettingsLogger.R_HOME, path);
        }
        return alert.getSelectedItem();
    }



    @FXML
    public void exportResult(){
      this.experimentController.exportResult();
    }

    @FXML
    public void switchExperiment(){
        if(Main.getBorderPane().getCenter() instanceof SingleLoader){
            Main.getBorderPane().setCenter(new Dual());
        }else {
            Main.getBorderPane().setCenter(new SingleLoader());
        }


    }

    @FXML
    protected void setRWindow(){
        RPathLoader loader = new RPathLoader();
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Choose your path(s) to R...");
        stage.setScene(new Scene(loader.getTheParent()));
        stage.showAndWait();
    }

    public void setExperimentController(ExperimentController experimentController) {
        LOGGER.debug("the experiment controller is set");
        this.experimentController = experimentController;
    }


    /**
     * Display the user setting window.
     */
    @FXML
    protected void showAbout() {
        AboutLoader about = new AboutLoader();
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("About DNA Barcode Compatibility");
        stage.setScene(new Scene(about.getTheParent()));
        stage.setResizable(true);
        stage.showAndWait();
    }


    @FXML
    void showTutorial() {
        TutorialPage browser = new TutorialPage();
        Stage stage = new Stage();
        stage.setTitle("Dna Barcode Compatibility Tutorial");
        Scene scene = new Scene(browser);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void showDocumentation() {
        DocumentationPage browser = new DocumentationPage();
        Stage stage = new Stage();
        stage.setTitle("Dna Barcode Compatibility Documentation");
        Scene scene = new Scene(browser);
        stage.setScene(scene);
        stage.show();
    }


}
