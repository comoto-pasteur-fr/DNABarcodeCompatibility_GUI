package dbc.main.experiment;


import dbc.engine.EngineHandler;
import dbc.main.experiment.single.SingleLoader;
import dbc.main.experiment.single.SingleController;

/**
 * Created by ctreb on 16/06/2018.
 */
public class SingleModel extends ExperimentModel {



    private static final String indexDfName = "index_df_1";

    public SingleModel(SingleLoader single) {
        super(single);
        this.setExperimentController( new SingleController());
    }

    public static String [][] buildResult(){
        return EngineHandler.buildSingleTableResult();
    }

    public static void search(){
        EngineHandler.searchForSingleExperiment(SingleModel.indexDfName, SingleModel.getMultiplexingLevel(),
                getPlatform(), SingleModel.getMetric(), SingleModel.getD(), SingleModel.getAdapters(0));

    }

}
