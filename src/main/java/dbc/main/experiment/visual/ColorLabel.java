package dbc.main.experiment.visual;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Use for the construction of visual result.
 * Creates the colored sequence according to platform.
 */
class ColorLabel extends StandardLabel{


    /**
     * Constructor.
     * @param nucleotide, the nucleotide read
     * @param platform the platform used
     */
    ColorLabel(String nucleotide, int platform) {

        super(nucleotide);
        super.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.setMinWidth(40.0);
        switch (platform) {
            case 4:

                if (nucleotide.equals("A") || nucleotide.equals("C")) {
                    this.setStyle(" -fx-background-color: #f16a6a;");
                } else {
                    this.setStyle(" -fx-background-color: #99e7a9;");
                }

                break;
            case 2:
                switch (nucleotide) {
                    case "A":
                        this.setStyle(" -fx-background-color: #ffcf40;");
                        break;
                    case "T":
                        this.setStyle(" -fx-background-color: #99e7a9;");
                        break;
                    case "G":
                        this.setStyle(" -fx-background-color: #999999;");
                        break;
                    case "C":
                        this.setStyle(" -fx-background-color: #f16a6a;");
                        break;
                }

                break;
            case 1:
                switch (nucleotide) {
                    case "A":
                        this.setStyle(" -fx-background-color: #99e7a9;");
                        break;
                    case "T":
                        this.setStyle(" -fx-background-color: #ffcf40;");
                        break;
                    case "G":
                        this.setStyle(" -fx-background-color: #999999;");
                        break;
                    case "C":
                        this.setStyle(" -fx-background-color: #f16a6a;");
                        break;
                }
                break;
            case 0:
                switch (nucleotide) {
                    case "A":
                        this.setStyle(" -fx-background-color: #99e7a9;");
                        break;
                    case "T":
                        this.setStyle(" -fx-background-color: #f16a6a;");
                        break;
                    case "G":
                        this.setStyle(" -fx-background-color: #999999;");
                        break;
                    case "C":
                        this.setStyle(" -fx-background-color: #6BB8E7;");
                        break;
                }

                break;
        }
    }


}
