package dbc.main.experiment;

import dbc.engine.EngineHandler;
import dbc.main.experiment.dual.Dual;
import dbc.main.experiment.dual.DualController;

/**
 * Created by ctreb on 16/06/2018.
 */
public class DualModel extends ExperimentModel {



    private static final String indexDfName1 = "index_df_1";
    private static final String indexDfName2 = "index_df_2";

    public DualModel(Dual dual) {
        super(dual);
        this.setExperimentController( new DualController());
    }

    public static String [][] buildResult(){
        return EngineHandler.buildDualTableResult();
    }

    public static void search(){
        EngineHandler.searchForDualExperiment(
                DualModel.indexDfName1,
                DualModel.indexDfName2,
                DualModel.getMultiplexingLevel(),
                DualModel.getChemistry(),
                DualModel.getMetric(),
                DualModel.getD(),
                DualModel.getAdapters(0),
                DualModel.getAdapters(1));
    }


}
