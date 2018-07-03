package dbc.task;


import dbc.adapters.AdapterList;
import dbc.engine.EngineHandler;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileReadingTask extends Task<AdapterList> {

   private String filePath;
   private String indexDfName;
   private AdapterList adapters;
   private static final Logger LOGGER = LoggerFactory.getLogger(FileReadingTask.class);


    public FileReadingTask( String indexDfName,String filePath) {
        this.filePath = filePath;
        this.indexDfName = indexDfName;
        try {
            call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected AdapterList call() throws Exception {
        this. adapters = ReadFile();
        return adapters;
    }

    public AdapterList ReadFile(){
        AdapterList adapters = EngineHandler.getIndexList(indexDfName, filePath);
        if (adapters == null){
            LOGGER.error("FileLoading has failed");
            this.getOnFailed();
        }else{
            LOGGER.debug("FileLoading has succeeded");
        }
        return adapters;
    }

    public AdapterList getAdapters() {
        return adapters;
    }
}
