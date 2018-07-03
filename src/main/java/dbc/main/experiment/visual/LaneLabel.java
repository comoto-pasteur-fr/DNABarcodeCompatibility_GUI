package dbc.main.experiment.visual;

import javafx.geometry.Pos;
import javafx.scene.text.Font;

/**
 * Use for the construction of visual result.
 */

class LaneLabel extends StandardLabel{

    /**
     *
     * Constructor.
     * @param lane, the lane considered.
     */
    LaneLabel(String lane){
        super(lane);
        this.setRotate(-90);
        this.setStyle("-fx rotate : -90.0");
        this.setAlignment(Pos.BASELINE_CENTER);
        this.setFont(new Font("lucinda bold", 16));
    }
}
