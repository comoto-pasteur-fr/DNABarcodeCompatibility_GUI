package dbc.task;

import dbc.engine.EngineHandler;
import javafx.concurrent.Task;

/**
 * Created by ctreb on 16/06/2018.
 */


@SuppressWarnings("ALL")
public class StartREngineTask extends Task<Boolean> {


    @Override
    protected Boolean call() throws Exception {
        return EngineHandler.checkEngine();
    }



}
