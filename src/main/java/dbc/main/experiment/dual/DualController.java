package dbc.main.experiment.dual;


import dbc.main.experiment.DualModel;
import dbc.main.experiment.ExperimentController;
import dbc.main.experiment.SingleModel;
import dbc.settings.SettingsController;
import dbc.utils.ErrorMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.slf4j.LoggerFactory;

import java.io.File;

public class DualController extends ExperimentController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DualController.class);


    @FXML
    private TextField userFile1;

    @FXML
    private TextField userFile2;

    @FXML
    private TableView tableView1 ;

    @FXML
    private TableView tableView2 ;


    private boolean chooseFile1;

    private boolean chooseFile2;



    private final int index1 = 0;
    private final int index2 = 1;

    public DualController(){
        super();
        String [] names = {"Sample", "Lane", "Id1", "Sequence1","Id2", "Sequence2"};
        this.setTableResultName(names);

    }


    @FXML
    private void chooseFile1(){
        FileChooser fileChooser = chooseAFile(null,"Select a tab delimited text file",
                "Flat files",new String []{"*.txt" ,"*.csv","*.fasta"});
        File file = fileChooser.showOpenDialog(null);
        try{
            String filepath = file.getAbsolutePath();
            userFile1.setText(file.getName());
            String indexDfName1 = "index_df_1";
            DualModel.buildAdapters(indexDfName1, filepath, index1);
            tableView1 = this.buildTableView(DualModel.getAdapters(index1));
            this.fillTab(tableView1, this.getIndexView().getTabs().get(0));
            this.chooseFile1 = true;
            userFile1.setVisible(true);
        } catch (NullPointerException npe){
            LOGGER.info("the user didn't load a file");
        }
    }


    @FXML
    private void chooseFile2(){
        FileChooser fileChooser = chooseAFile(null,"Select a tab delimited text file",
                "Flat files",new String []{"*.txt" ,"*.csv","*.fasta"});
        File file = fileChooser.showOpenDialog(null);
        try{
            String filepath = file.getAbsolutePath();
            userFile2.setText(file.getName());
            String indexDfName2 = "index_df_2";
            DualModel.buildAdapters(indexDfName2, filepath, index2);
            tableView2 = this.buildTableView(DualModel.getAdapters(index2));
            this.fillTab(tableView2, this.getIndexView().getTabs().get(index2));
            this.chooseFile2 = true;
            userFile2.setVisible(true);
        } catch (NullPointerException npe){
            LOGGER.info("the user didn't load a file");
        }
    }


    @FXML
    public void searchForASolution() {
        String mplexLevel = this.getMx().getValue();
        if (getPlatform().getValue() != null && chooseFile1 && chooseFile2 && mplexLevel != null && !getSample().getText().equals("")) {
            DualModel.setMultiplexingLevel(mplexLevel);
            DualModel.setMetric();
            DualModel.search();

            /* Builds the tableResult */
            String[][] df = DualModel.buildResult();
            buildResult(DualModel.getMultiplexingLevel(), df);
            setVisual(DualModel.nbLane(), DualModel.getMultiplexingLevel(), df);
        } else if (getPlatform().getValue() == null) {
            ErrorMessage.showMessage("Please enter a platform");
        } else if (!chooseFile1) {
            ErrorMessage.showMessage("Please choose a first file");
        } else if (!chooseFile2) {
            ErrorMessage.showMessage("Please choose a second ile");
        }else if (getSample().getText().equals("")) {
            ErrorMessage.showMessage("Please enter a sample number");
        } else if (mplexLevel == null) {
            ErrorMessage.showMessage("Please enter a multiplexing level value");
        }
    }



    @FXML
    private void handleSettings() {
        setSettingsWindow();
        Tab tab1 = this.getIndexView().getTabs().get(index1);
        Tab tab2 = this.getIndexView().getTabs().get(index2);

        if (SettingsController.modification) {

            if (tab1.getContent() != null) {
                tab1.setContent(null);
                DualModel.getPreferences().setPreferences();
                DualModel.getAdaptersList()[index1].filterList(SingleModel.getPreferences());
                tableView1 = buildTableView(DualModel.getAdapters(index1));
                tab1.setContent(this.tableView1);

            }
            if (tab2.getContent() != null) {
                tab2.setContent(null);
                DualModel.getPreferences().setPreferences();
                DualModel.getAdaptersList()[index2].filterList(SingleModel.getPreferences());
                tableView2 = buildTableView(DualModel.getAdapters(index2));
                tab2.setContent(this.tableView2);

            }
        }
    }
}
