package dbc.main.experiment.single;


import dbc.main.experiment.ExperimentController;
import dbc.main.experiment.SingleModel;
import dbc.settings.SettingsController;
import dbc.utils.ErrorMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class SingleController extends ExperimentController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SingleController.class);

    private final int index1 = 0;

    @FXML
    public ImageView browseImageView;

    @FXML
    private TableView tableView = new TableView();

    @FXML
    private TextField userFile;

    private boolean fileLoaded = false;

    public SingleController() {
        super();
        String[] names = {"Sample", "Lane", "Id", "Sequence"};
        this.setTableResultName(names);
    }

    @FXML
    private void chooseAndSetFile() {
        FileChooser fileChooser = chooseAFile(null, "Select a tab delimited text file",
                "Flat files", new String []{"*.txt" ,"*.csv","*.fasta"});
        File file = fileChooser.showOpenDialog(null);
        try {
            String filepath = file.getAbsolutePath();
            userFile.setText(file.getName());
            String indexDfName = "index_df_1";
            SingleModel.buildAdapters(indexDfName, filepath, index1);
            SingleModel.getAdaptersList()[index1].filterList(SingleModel.getPreferences());
            tableView = this.buildTableView(SingleModel.getAdapters(index1));
            this.fillTab(tableView, this.getIndexView().getTabs().get(index1));
            LOGGER.debug("the file is supposed to be loaded");
            fileLoaded = true;
            userFile.setVisible(true);
        } catch (NullPointerException npe) {
            LOGGER.error("The file loading has failed");
        }
    }


    @FXML
    public void searchForASolution() {
        String mplexLevel = this.getMx().getValue();
        if (getPlatform().getValue() != null &&
                fileLoaded && mplexLevel != null && !getSample().getText().equals("")) {
            SingleModel.setMultiplexingLevel(mplexLevel);
            SingleModel.setMetric();
            SingleModel.search();

            /* Builds the tableResult */
            String[][] df = SingleModel.buildResult();
            buildResult(SingleModel.getMultiplexingLevel(), df);
            setVisual(SingleModel.nbLane(), SingleModel.getMultiplexingLevel(), df);
        } else if (getPlatform().getValue() == null) {
            ErrorMessage.showMessage("Please enter a platform");
        } else if (!fileLoaded) {
            ErrorMessage.showMessage("Please choose a file");
        } else if (getSample().getText().equals("")) {
            ErrorMessage.showMessage("Please enter a sample number");
        } else if (mplexLevel == null) {
            ErrorMessage.showMessage("Please enter a multiplexing level value");
        }
    }


    @FXML
    private void handleSettings() {
        setSettingsWindow();
        Tab tab = this.getIndexView().getTabs().get(index1);
        if (SettingsController.modification) {
            if (tab.getContent() != null) {
                tab.setContent(null);
                SingleModel.getPreferences().setPreferences();
                SingleModel.getAdaptersList()[index1].filterList(SingleModel.getPreferences());
                tableView = buildTableView(SingleModel.getAdapters(index1));
                tab.setContent(this.tableView);
            }
        }
    }
}
