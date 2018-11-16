package dbc.engine;

import dbc.adapters.Adapter;
import dbc.adapters.AdapterList;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;
import org.slf4j.LoggerFactory;

/**
 * Created by Wam on 11/06/2018.
 */
public class EngineHandler {

    private static final String DEFAULT_R_PACKAGE = "DNABarcodeCompatibility";
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(EngineHandler.class);
    private static Rengine engine;


    public static Rengine getEngine() {
        return engine;
    }

    public static boolean checkEngine() {
        initRengine();
        return callPackage(engine);

    }

    /**
     * Loads a R session
     */
    private static void initRengine() {
        if (engine == null) {
            try{
            engine = new Rengine(new String[]{"--vanilla"}, false, null);
            }catch (Exception e){
                assert false;
                if (!engine.waitForR()) {
                LOGGER.error("Cannot load R");
            }else{
                    LOGGER.info("R is running !");
            }
            }
        }
    }

    /**
     * Runs the script into R session
     *  @param rengine  the R engine
     *
     */
    private static boolean callPackage(Rengine rengine) { // + String[] scriptArgs eventually

        // check if source file was successfully loaded and executed
        if (rengine.eval("library(\"" + EngineHandler.DEFAULT_R_PACKAGE + "\")") == null) {
            LOGGER.error("** JRI R-Engine: error charging the package!");
            LOGGER.error("** package  : \"" + EngineHandler.DEFAULT_R_PACKAGE + "\"");
            return false;
        } else {
            LOGGER.debug("JRI R-Engine: executing " + EngineHandler.DEFAULT_R_PACKAGE + " ...done!");
            engine.eval("library(\"dplyr\")");
            return true;
        }

    }

    public static REXP file(String indexDfName, String filePath) {
        String s = indexDfName + " = file_loading_and_checking(" + "\"" + filePath + "\")";
        EngineHandler.getEngine().eval(s);
        return (EngineHandler.getEngine().eval(indexDfName));
    }

    private static String getIndexId(String indexDfName) {
        return ("v = " + indexDfName + "$Id");
    }

    private static String getIndexSequence(String indexDfName) {
        return ("v = " + indexDfName + "$sequence");
    }

    private static String getIndexgccontent(String indexDfName) {
        return ("v = " + indexDfName + "$GC_content");
    }

    private static String getIndexHomopolymer(String indexDfName) {
        return ("v = as.numeric(" + indexDfName + "$homopolymer)");
    }

    private static void index(String index, String indexDfName){
        engine.eval(index + "= " + indexDfName);
    }

    public static void sampleNumber(){
        engine.eval("sample_number = as.numeric(sample_number)");
    }
    private static void mx(){
        EngineHandler.getEngine().eval( "mplex_level = as.numeric(mplex_level)");
    }
    public static REXP mxSet(){
        return(EngineHandler.getEngine().eval("as.character(DNABarcodeCompatibility::: multiplexing_level_set(as.integer(sample_number)))"));
    }


    private static void getResult1(String platform, String metric, String d) {
        sampleNumber();
        mx();
        EngineHandler.getEngine().eval("result1 = DNABarcodeCompatibility:::final_result(index1, sample_number, mplex_level, platform = " +
                platform + ", metric =" + metric + ", d = " + d + ")");
    }

    private static void getResultDual(String platform, String metric, String d) {
        sampleNumber();
        mx();
        EngineHandler.getEngine().eval("result = DNABarcodeCompatibility:::final_result_dual(index1, index2, sample_number, mplex_level," +
                " platform = " + platform + ", metric = " + metric + ", d = " + d + ")");
        engine.eval("mplex_level").asString();
        engine.eval("result");
        engine.eval("index1");
    }

    public static void rightCsv(String export) {
        engine.eval("write.csv2(row.names = FALSE, result1, file = \"" + export + "\")");
    }

    public static String error(){
        String s;
        try{
            s  = engine.eval("error_message").asString();// reads the error message
        }catch (NullPointerException npe){
           s = null;
        }
        return s;
    }

    public static AdapterList getIndexList(String indexDfName, String filePath){
        engine.assign("error_message","");
        file(indexDfName, filePath);
        if (EngineHandler.error()== null ||EngineHandler.error().equals("")) {
            return(buildAdapters(indexDfName));
        }else {
            return (null);
        }
    }


    private static AdapterList buildAdapters(String indexDfName){
        REXP indexId = engine.eval(getIndexId(indexDfName));
        REXP indexSequence = engine.eval(getIndexSequence(indexDfName));
        REXP indexGcContent = engine.eval(getIndexgccontent(indexDfName));
        REXP indexHomopolymer = engine.eval(getIndexHomopolymer(indexDfName));
        String[] id = indexId.asStringArray();
        String[] sequence = indexSequence.asStringArray();
        double[] GC_content = indexGcContent.asDoubleArray();
        double[] homopolymer = indexHomopolymer.asDoubleArray();
        int row = id.length;
        AdapterList adapterList = new AdapterList();
        /* Fill the tableView */
        for (int i = 0; i <= row - 1; i++) {
            adapterList.add(new Adapter(id[i], sequence[i], GC_content[i], homopolymer[i]));
        }
        adapterList.setLengthSeq();
        return adapterList;
    }

    public static void searchForSingleExperiment(String indexDfName, String mplex_level,
                                                 String platform, String metric, String d, AdapterList adapterList){

        index("index1 ", indexDfName);
        assign("mplex_level",mplex_level);
        mx();
        AdapterList removedIndex = adapterList.getUnselected();
        String regex = removedIndex.toString().replaceAll("\\[|\\]", "");
        String javavector = "c(" + regex + ")";
        engine.eval("regex =" + javavector);
        engine.eval("index1 = filter(index1,!(Id %in% regex))");
        getResult1(platform,metric,d);
    }

    public static void searchForDualExperiment(String indexDfName1,
                                               String indexDfName2 ,
                                               String mplex_level,
                                               String platform,
                                               String metric,
                                               String d,
                                               AdapterList adapterList1,
                                               AdapterList adapterList2){
        index("index1 ", indexDfName1);
        index("index2 ", indexDfName2);
        assign("mplex_level",mplex_level);
        mx();
        AdapterList removedIndex1 = adapterList1.getUnselected();
        AdapterList removedIndex2 = adapterList2.getUnselected();
        String regex1 = removedIndex1.toString().replaceAll("\\[|\\]", "");
        String regex2 = removedIndex2.toString().replaceAll("\\[|\\]", "");
        String javaVector1 = "c(" + regex1 + ")";
        String javaVector2 = "c(" + regex2 + ")";
        engine.eval("regex =" + javaVector1);
        engine.eval("index1 = index1 %>% filter(Id != regex)");
        engine.eval("regex =" + javaVector2);
        engine.eval("index2 = index2 %>% filter(Id != regex)");
        getResultDual(platform, metric, d);
    }

    public static String [][] buildSingleTableResult(){
        REXP sample = engine.eval("result1$sample");
        REXP lane = engine.eval("v = result1$Lane");
        REXP id = engine.eval("v = result1$Id");
        REXP sequence = engine.eval("v = result1$sequence");
        String[] samples = sample.asStringArray();
        int row = samples.length;
        String[] lanes = lane.asStringArray();
        String[] ids = id.asStringArray();
        String[] sequences = sequence.asStringArray();
        String[][] df = new String[row][4];
        for (int i = 0; i <= samples.length - 1; i++) {
            df[i][0] = samples[i];
            df[i][1] = lanes[i];
            df[i][2] = ids[i];
            df[i][3] = sequences[i];
        }
        return (df);
    }

    public static String [][] buildDualTableResult(){
        REXP sample = engine.eval("result$sample");
        REXP lane = engine.eval("v = result$Lane");
        REXP id1 = engine.eval("v = result$Id1");
        REXP sequence1 = engine.eval("v = result$sequence1");
        REXP id2 = engine.eval("v = result$Id2");
        REXP sequence2 = engine.eval("v = result$sequence2");
        String[] samples = sample.asStringArray();

        int row = samples.length;
        String[] lanes = lane.asStringArray();
        String[] ids1 = id1.asStringArray();
        String[] sequences1 = sequence1.asStringArray();
        String[] ids2 = id2.asStringArray();
        String[] sequences2 = sequence2.asStringArray();
        String[][] df = new String[row][6];
        for (int i = 0; i <= samples.length - 1; i++) {
            df[i][0] = samples[i];
            df[i][1] = lanes[i];
            df[i][2] = ids1[i];
            df[i][3] = sequences1[i];
            df[i][4] = ids2[i];
            df[i][5] = sequences2[i];
        }
        return (df);
    }

    public static boolean exist(){
        return (engine != null);
    }

    public static void assign(String  variable, String value){
        engine.assign(variable, value);

    }

    public static void killEngine(){
        engine.end();
    }
}
