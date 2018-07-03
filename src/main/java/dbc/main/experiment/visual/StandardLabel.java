package dbc.main.experiment.visual;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;


/**
 * Use for the construction of visual result.
 */
class StandardLabel extends Label {

    /**
     * Constructor.
     * @param s a String : lane number, sample number, nucleotide(coloredLabel), barcode Id.
     */
    StandardLabel(String s){
        super(s);
        this.setFont(new Font("lucinda bold", 13));
        this.setAlignment(Pos.CENTER);
        this.setMinHeight(10.0);
    }
}
