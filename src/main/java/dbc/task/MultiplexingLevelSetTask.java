package dbc.task;

import dbc.engine.EngineHandler;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("FieldCanBeLocal")
public class MultiplexingLevelSetTask extends Task<Boolean> {

    private String sampleNumber;
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiplexingLevelSetTask.class);
    private String[]possibleMxChoices;

    public MultiplexingLevelSetTask(String sampleNumber) {
        this.sampleNumber = sampleNumber;
    }

    @Override
    protected Boolean call() throws Exception {
        return(setMultiplexingLevel());

    }

    public Boolean setMultiplexingLevel(){
        EngineHandler.assign("sample_number", sampleNumber);
        EngineHandler.sampleNumber();
        String[] possibleMx = EngineHandler.mxSet().asStringArray();
        if (possibleMx.length == 0){
            LOGGER.debug("Mx set has failed");
            this.getOnFailed();
            return false;
        }else {
            this.setPossibleMxChoices(EngineHandler.mxSet().asStringArray());
            LOGGER.debug("Mx set has succeeded");
            return (true);
        }
    }


    public void setPossibleMxChoices(String[] possibleMxChoices) {
        this.possibleMxChoices = null;
        this.possibleMxChoices = possibleMxChoices;
    }
}
