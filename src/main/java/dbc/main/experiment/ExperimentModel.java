package dbc.main.experiment;

import dbc.adapters.AdapterList;
import dbc.engine.EngineHandler;
import dbc.task.StartREngineTask;
import dbc.utils.*;
import org.slf4j.LoggerFactory;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created by ctreb on 16/06/2018.
 */
 public class ExperimentModel implements ChangeListener{


    private static String platform;
    private static String sampleNumber;
    private static String multiplexingLevel;
    private static Preferences preferences;
    private static String[] filePaths;
    private static AdapterList [] adaptersList;
    private static ExperimentLog experimentLog;
    private ExperimentController experimentController;
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ExperimentModel.class);
    private Loader loader;
    private static String metric;
    private static String platformName;

    public static ExperimentModel current;

    public ExperimentModel(Loader loader) {

        EngineHandler.checkEngine();
        this.loader = loader;
        preferences = new Preferences();
        experimentLog = new ExperimentLog(preferences);
        adaptersList = new AdapterList[2];
        filePaths = new String[2];
        current = this;
    }

    public void startREngine() {
        StartREngineTask startREngineTask = new StartREngineTask() {
            @Override
            protected Boolean call() throws Exception {
                return super.call();
            }
        };
        startREngineTask.setOnSucceeded((event) -> ErrorMessage.showMessage("The R engine has suceeded."));
        startREngineTask.setOnFailed((event) -> ErrorMessage.showMessage("The R engine has failed."));
    }



    public static AdapterList fileReading(String indexName, String filePath) {
        filePath = filePath.replaceAll("\\\\", "/");/* just for R language */
        System.out.println(filePath);
        AdapterList adapters = EngineHandler.getIndexList(indexName, filePath);
        if (adapters == null){
            LOGGER.error("FileLoading has failed");
            ErrorMessage.showMessage(EngineHandler.error());
        }else{
            LOGGER.debug("FileLoading has succeeded");
        }
        return adapters;
    }

    public static AdapterList buildAdapters(String indexName, String filepath){
        AdapterList adapterList = fileReading(indexName, filepath);
        adapterList.filterList(preferences);
        return(adapterList);
    }


    public static void buildAdapters(String indexName, String filepath, int number){
        setFilePath(filepath, number);
        AdapterList adapterList = fileReading(indexName, filepath);
        if (adapterList != null) {
            adapterList.filterList(preferences);
            setAdapters(adapterList, number);
        }
    }
    public static boolean checkSampleNumber(String sampleNumber) {
        boolean goodInt = true;
        if (sampleNumber.equals("")) {
            goodInt = false;
            ErrorMessage.showMessage("Please enter a sample number");
            LOGGER.debug("no sample number was entered");
        } else {
            try {
                int number = Integer.parseInt(sampleNumber);/* is it an integer ? */
                if (number < 2) {
                    goodInt = false;
                    ErrorMessage.showMessage("You need at least 2 samples in order to use multiplexing !");
                }
            } catch (NumberFormatException nfe) {
                goodInt = false;
                ErrorMessage.showMessage("you have to enter an integer value");
            }
        }
        return goodInt;
    }

    public static String[] possibleMxChoices(String sampleNumber) {
        ExperimentModel.setSampleNumber(sampleNumber);
        EngineHandler.assign("sample_number", sampleNumber);
        return(EngineHandler.mxSet().asStringArray());
    }


    public static int nbLane() {
       return (Integer.parseInt(sampleNumber) / Integer.parseInt(multiplexingLevel));

    }

    public static void setExperimentLog(){
        if (filePaths[1]== null){
            experimentLog.setLog(filePaths[0], ExperimentModel.platformName,adaptersList[0],Integer.parseInt(sampleNumber), Integer.parseInt(multiplexingLevel));
        }else if (filePaths.length == 2){
            experimentLog.setLog(filePaths[0],filePaths[1], ExperimentModel.platformName,adaptersList[0],adaptersList[1],Integer.parseInt(sampleNumber), Integer.parseInt(multiplexingLevel));
        }
    }

    void setExperimentController(ExperimentController experimentController) {
        this.experimentController = experimentController;
    }

    public ExperimentController getExperimentController() {
        return experimentController;
    }

    private static void setSampleNumber(String sampleNumber) {
        ExperimentModel.sampleNumber = sampleNumber;
    }

    public static String getMultiplexingLevel() {
        return multiplexingLevel;
    }

    public static void setMultiplexingLevel(String multiplexingLevel) {
        ExperimentModel.multiplexingLevel = multiplexingLevel;
    }

    public static Preferences getPreferences() {
        return preferences;
    }

    public static String[] getFilepaths() {
        return filePaths;
    }


    public static ExperimentLog getExperimentLog() {
        return experimentLog;
    }


    public Loader getLoader() {
        return loader;
    }

    public static String getPlatform() {
        return platform;
    }

    public static void setPlatform(String platform) {
        platformName = platform;
        if (platform.equals("MiSeq ™, HiSeq ™")){
            ExperimentModel.platform = "4";
        }else if (platform.equals("MiniSeq ™, NextSeq ™, NovaSeq ™")){
            ExperimentModel.platform = "2";
        }else if (platform.equals("iSeq100 ™")){
            ExperimentModel.platform = "1";
        }else if (platform.equals("Other Platform")){
            ExperimentModel.platform = "0";
        }
    }

    public static String getMetric() {
        return metric;
    }

    public static void setMetric() {
        getPreferences().getFilter();
        ExperimentModel.metric = getPreferences().getFilter();
    }

    public static String getD() {
        return ExperimentModel.getPreferences().getDistance().toString();
    }

    public static AdapterList getAdapters(int number) {
        return getAdaptersList()[number];
    }

    public static void setAdapters(AdapterList adapters, int number) {
        ExperimentModel.getAdaptersList()[number] = adapters;
    }

    public static AdapterList[] getAdaptersList() {
        return adaptersList;
    }

    public  static void setFilePath(String filePath, int number) {
        ExperimentModel.getFilepaths()[number] = filePath;
    }

    public static void exportResult(String export){
        EngineHandler.rightCsv(export);
    }

    @Override
    public void stateChanged(ChangeEvent e) {

    }
}
