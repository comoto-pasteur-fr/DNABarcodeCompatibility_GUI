package dbc.main.experiment.visual;

import javafx.geometry.Pos;

/**
 * Use for the construction of visual result.
 */
class SampleLabel extends StandardLabel {

    /**
     * Constructor.
     * @param sampleNumber the sample number
     */
    SampleLabel(String sampleNumber){
        super(sampleNumber);
        this.setMinWidth(60.0);
        this.setAlignment(Pos.BASELINE_CENTER);

    }
}
