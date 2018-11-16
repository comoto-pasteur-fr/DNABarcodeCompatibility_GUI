package dbc.main;

import dbc.engine.EngineHandler;
import dbc.main.experiment.DualModel;
import dbc.main.experiment.ExperimentModel;
import dbc.main.experiment.SingleModel;
import dbc.main.experiment.dual.Dual;
import dbc.main.experiment.single.SingleLoader;
import dbc.settings.SettingsLogger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;


public class Main extends Application {

    private static BorderPane borderPane;

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final String JAVA_LIBRARY_PATH = "java.library.path";
    private static final String ENV_PROPERTY_R_HOME = "R_HOME";


    public void start(Stage primaryStage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("DNA Barcode Compatibility");
        borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            if (EngineHandler.exist()) {
                System.out.println("engine status : " + EngineHandler.exist());
                EngineHandler.killEngine();
            }
        });
        MainController mainController = loader.getController();
        if (SettingsLogger.get(ENV_PROPERTY_R_HOME)== null|| SettingsLogger.get(JAVA_LIBRARY_PATH )== null || SettingsLogger.get(JAVA_LIBRARY_PATH ).equals("")){
           mainController.setRWindow();
        }
        try {
            System.setProperty(JAVA_LIBRARY_PATH, SettingsLogger.get(JAVA_LIBRARY_PATH));
            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
            LOGGER.info("R library path : {}", System.getProperty(JAVA_LIBRARY_PATH));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.error("Unable to set library path", e);
            throw new LibraryException("Unable to set library path from ");
        }
        // Get R_HOME
        final String rHome = System.getenv(ENV_PROPERTY_R_HOME);

        // Check R_HOME
        if (rHome == null) {
            LOGGER.error("R_HOME must be set !");
            throw new LibraryException("R_HOME is not set");
        } else {
            LOGGER.info("R_HOME : {}", rHome);
        }

        String choice = mainController.chooseExperiment();

        if (choice.equals("Single")) {
            mainController.SingleExperiment();
        } else if (choice.equals("Dual")){
            mainController.DualExperiment();
        }else if (choice.equals("R Settings")){
            mainController.setRWindow();
        }
    }


    public static void main (String [] args){
        launch(args);
    }


    public static BorderPane getBorderPane() {
        return borderPane;
    }
}
