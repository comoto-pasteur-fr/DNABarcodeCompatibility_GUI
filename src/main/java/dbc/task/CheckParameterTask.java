package dbc.task;

import javafx.concurrent.Task;

@Deprecated
public class CheckParameterTask extends Task<Boolean> {

    private final String mx;
    private final String chemistry;
    private final boolean isAnInteger;
    private final boolean fileLoaded;

    public CheckParameterTask(String mx, String chemistry, boolean isAnInteger, boolean fileLoaded) {
        this.mx = mx;
        this.chemistry = chemistry;
        this.isAnInteger = isAnInteger;
        this.fileLoaded = fileLoaded;
    }

    @Override
    protected Boolean call() throws Exception {
        return checkParameters();
    }

    private boolean checkParameters() {
        boolean goodParameters = false;
        if (chemistry != null) {
            if (isAnInteger) {
                if (mx != null) {

                    if (mx.contains(mx)) {
                        if (fileLoaded) {
                            goodParameters = true;
                        } else {
                            this.getOnFailed();
                            updateMessage("Please choose a file");
                        }
                    } else {
                        this.getOnFailed();
                        updateMessage("Please select a suitable multiplexing level");
                    }
                } else {
                    this.getOnFailed();
                    updateMessage("Please select a  multiplexing level");
                }
            }else{
                this.getOnFailed();
                updateMessage("Please select a sample number.");
            }
        } else {
            this.getOnFailed();
            updateMessage("Please select a type of chemistry");
        }
        return goodParameters;
    }



}
