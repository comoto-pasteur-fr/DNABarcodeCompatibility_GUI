package dbc.main.experiment.visual;


import javafx.geometry.Pos;

/**
 * Use for the construction of visual result.
 */

class IdLabel extends StandardLabel {

    /**
     *
     * Constructor.
     * @param id the Id of the adapter.
     */
    IdLabel(String id){
        super(id);
        this.setAlignment(Pos.BASELINE_CENTER);
        this.setMinWidth(60.0);
    }
}
