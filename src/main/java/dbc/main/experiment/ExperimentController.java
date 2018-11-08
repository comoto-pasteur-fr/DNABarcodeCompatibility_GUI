package dbc.main.experiment;


import dbc.adapters.Adapter;
import dbc.adapters.AdapterList;
import dbc.adapters.tableadapter.DefaultTableAdapter;
import dbc.main.experiment.visual.LayoutGrid;
import dbc.settings.SettingsLoader;
import dbc.utils.ErrorMessage;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ExperimentController implements Initializable {


    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ExperimentController.class);
    /**
     * the visual result of the application.
     */
    @FXML
    private ScrollPane visual;

    /**
     * the log of the current run.
     */
    @FXML
    private TextArea logPane;

    /**
     * the possible choice of platform.
     */
    @FXML
    private ComboBox<String> platform;


    /**
     * The tabPane containing the index.
     */
    @FXML
    private TabPane indexView;

    /**
     * The table containing the result of the application.
     */
    @FXML
    private TableView tableResult;

    /**
     * The TextField containing the sample number.
     */
    @FXML
    private TextField sample;

    /**
     * The comboBox possible choices for multiplexing level.
     */
    @FXML
    private ComboBox<String> mx;

    /**
     * the Column names of the table result.
     */
    private String[] tableResultName;


    /**
     * Initialisation of the window, loading of R engine.
     */
    public ExperimentController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.getMx().valueProperty().addListener((observable, oldValue, newValue) ->
                ExperimentModel.setMultiplexingLevel(newValue));
        this.getPlatform().valueProperty().addListener((observable, oldValue, newValue) ->

                ExperimentModel.setPlatform(newValue));
        getPlatform().getItems().addAll("MiSeq ™, HiSeq ™", "MiniSeq ™, NextSeq ™, NovaSeq ™", "iSeq100 ™", "Other Platform");

    }


    protected void fillTab(TableView tableView, Tab tab) {
        tab.setContent(null);
        tab.setContent(tableView);
        tableView.setVisible(true);
        tableView.setEditable(true);
    }


    protected TableView buildTableView(AdapterList adapters) {
        TableView tableView = new TableView();
        tableView.setItems(FXCollections.observableList(adapters));

        TableColumn<Object, Object> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));

        TableColumn<Object, Object> sequenceCol = new TableColumn<>("sequence");
        sequenceCol.setCellValueFactory(new PropertyValueFactory<>("sequence"));


        TableColumn<Object, Object> gcContentCol = new TableColumn("GC_content");
        gcContentCol.setCellValueFactory(new PropertyValueFactory<>("gcContent"));

        TableColumn<Object, Object> homopolymerCol = new TableColumn("Homopolymer");
        homopolymerCol.setCellValueFactory(new PropertyValueFactory<>("homopolymer"));

        TableColumn<Adapter, Boolean> checkCol = new TableColumn<>("Selected");
        checkCol.setCellValueFactory(new PropertyValueFactory<>("Selected"));


        checkCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Adapter, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Adapter, Boolean> param) {
                Adapter adapter = param.getValue();

                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(adapter.isSelected());
                booleanProp.addListener(new ChangeListener<Boolean>() {

                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                        Boolean newValue) {
                        adapter.setSelected(newValue);
                    }
                });
                return booleanProp;
            }
        });

        checkCol.setCellFactory(new Callback<TableColumn<Adapter, Boolean>, //
                TableCell<Adapter, Boolean>>() {
            @Override
            public TableCell<Adapter, Boolean> call(TableColumn<Adapter, Boolean> p) {
                CheckBoxTableCell<Adapter, Boolean> cell = new CheckBoxTableCell<Adapter, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
        tableView.getColumns().addAll(checkCol, idCol, sequenceCol, gcContentCol, homopolymerCol);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setEditable(true);
        return tableView;
    }


    /**
     * Builds multiplexing level options
     */
    @FXML
    public void buildMultiplexingLevel() {
        String sampleNumber = sample.getText();
        if (ExperimentModel.checkSampleNumber(sampleNumber)) {
            String[] p = ExperimentModel.possibleMxChoices(sampleNumber);
            this.getMx().getItems().setAll(p);
        }
    }

    /**
     * Builds the tableResult
     */
    protected void setVisual(int nbLane, String multiplexingLevel, String[][] result) {
        if (this.visual.getContent() != null) {
            this.visual.setContent(null);
        }
        LayoutGrid visualLayout = new LayoutGrid(nbLane, multiplexingLevel, result, Integer.parseInt(ExperimentModel.getPlatform()));
        System.out.print(ExperimentModel.getPlatform());
        this.visual.setContent(visualLayout);
    }

    /**
     * @param index       an array containing the result
     * @param table       the table to display the result
     * @param columnNames the names of each column
     */
    private void buildTableData(Object[][] index, TableView table, String[] columnNames) {
        new DefaultTableAdapter(table, index, columnNames, true, true);
    }

    /**
     * Exports and saves the log onto the user system.
     */
    @FXML
    public void exportLog() {
        if (ExperimentModel.getExperimentLog().getFormattedDate()!= null){
        FileChooser fileChooser = chooseAFile(ExperimentModel.getExperimentLog().getFormattedDate(), "Save log...", "TXT files (*.txt)", "*.txt");
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            SaveFile(ExperimentModel.getExperimentLog().getLog(), file);
        }
        }else {ErrorMessage.showMessage("No log to export");}

    }

    /**
     * Exports and saves the result onto the user system.
     */
    @FXML
    public void exportResult() {
        if (ExperimentModel.getExperimentLog().getFormattedDate() != null) {
            try {
                FileChooser fileChooser = chooseAFile("result_" + ExperimentModel.getExperimentLog().getDate(), "Save results...", "CSV File", "*.csv");
                File file = fileChooser.showSaveDialog(null);
                String filePath = file.getAbsolutePath();
                String export = filePath.replaceAll("\\\\", "/");/* just for R language */
                ExperimentModel.exportResult(export);

            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }
        } else {ErrorMessage.showMessage("No result to export");}

    }

    protected FileChooser chooseAFile(String initialFileNme, String title, String description, String extension) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialFileName(initialFileNme);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(description, extension);
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser;
    }

    protected FileChooser chooseAFile(String initialFileNme, String title, String description, String[] extension) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialFileName(initialFileNme);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(description, extension);
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser;
    }

    /**
     * Saves a content into a file.
     *
     * @param content the content to be saved
     * @param file    the file to save the content into
     */
    private void SaveFile(String content, File file) {
        try {
            FileWriter fileWriter;
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            LOGGER.error("Saving file has failed");
        }
    }

    /**
     * Display the user setting window.
     */
    protected void setSettingsWindow() {
        SettingsLoader settings = new SettingsLoader();
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("SettingsLoader");
        stage.setScene(new Scene(settings.getTheParent()));
        stage.showAndWait();
    }


    protected void buildResult(String d, String[][] df) {
        int nbLane = Integer.parseInt(getSample().getText()) / Integer.parseInt(d);
        buildTableData(df, getTableResult(), getTableResultName());
        this.getTableResult().setVisible(true);
        setVisual(nbLane, d, df);
        ExperimentModel.setExperimentLog();
        this.getLogPane().setText(ExperimentModel.getExperimentLog().getLog());

    }

    /**
     * Erases the list of possible multiplexing levels.
     */



    public ComboBox<String> getPlatform() {
        return platform;
    }

    public ScrollPane getVisual() {
        return visual;
    }

    public void setVisual(ScrollPane visual) {
        this.visual = visual;
    }

    private TextArea getLogPane() {
        return logPane;
    }

    protected TabPane getIndexView() {
        return indexView;
    }

    private TableView getTableResult() {
        return tableResult;
    }

    protected ComboBox<String> getMx() {
        return mx;
    }

    public void setMx(ComboBox<String> mx) {
        this.mx = mx;
    }

    public TextField getSample() {
        return sample;
    }



    private String[] getTableResultName() {
        return tableResultName;
    }

    protected void setTableResultName(String[] tableResultName) {
        this.tableResultName = tableResultName;
    }





}

