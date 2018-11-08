package dbc.main.experiment.visual;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * Constructs the visual table grid result according to the type of platform and if the experiment is single or dual.
 */
public class LayoutGrid extends Pane {

    /** The number of the lane.*/
    private int nbLane;

    /** The number of samples to pool.*/
    private int multiplexingLevel;

    /** A gridPane containing the result.*/
    private GridPane gridPane;

    /** An array containing each lane result (lane labels and tagged samples).*/
    private VBox[] laneResult;

    /** An array containing each lane Label.*/
    private LaneLabel[] laneLabels;

    /** The length of the DNA barcode.*/
    private int sequenceLength;

    /** The type of experiment.*/
    private int singleOrDual;

    /** The platform of the experiment.*/
    private int platform;

    /**
     * Constructor.
     *
     * @param nbLane the number of lanes
     * @param multiplexingLevel the multiplexing level
     * @param result the result provide by the R Package
     * @param platform the platform of the experiment
     */
    public LayoutGrid (int nbLane,String multiplexingLevel, String [][] result , int platform){
        this.singleOrDual = result[0].length;
        this.platform = platform;
        this.gridPane = new GridPane();
        this.getChildren().add(this.gridPane);
        this.gridPane.setLayoutX(60.0);
        this.gridPane.setLayoutY(30.0);
        this.gridPane.setAlignment(Pos.BASELINE_CENTER);
        this.nbLane = nbLane;
        this.multiplexingLevel = Integer.parseInt(multiplexingLevel);
        this.gridPane.setVgap(20.0);
        this.gridPane.setHgap(20.0);
        this.setPadding(new Insets(60,60,50,60));
        buildLaneResult(result);
        buildLanesResult();
        setTitle();
        this.gridPane.setGridLinesVisible(false);

        this.setVisible(true);
    }


    /**
     * Build the visual result.
     *
     * @param result the result provide by the R Package
     */
        private void buildLaneResult(String [][] result) {
            this.laneResult = new VBox[this.nbLane];
            this.laneLabels = new LaneLabel[this.nbLane];
            TaggedSample[] taggedSamples = new TaggedSample[result.length];
            for (int i = 0; i <= laneResult.length - 1; i++) {
                laneResult[i] = new VBox(5.9);
                laneLabels[i] = new LaneLabel("Lane " + Integer.toString(i+1));
                for (int j = i * multiplexingLevel; j < (i + 1) * multiplexingLevel; j++) {
                    taggedSamples[j] = new TaggedSample(result[j], platform);
                    laneResult[i].getChildren().add(taggedSamples[j]);
                }
            }
            this.sequenceLength = taggedSamples[0].getSequenceLength();
        }

    /**
     * Adds the lane number.
     */
    private void buildLanesResult(){
                for(int i = 0; i <= laneResult.length-1; i++){
                    this.gridPane.add(laneLabels[i],0,i+1);
                    this.gridPane.add(laneResult[i], 1,i+1);

            }

    }

    /**
     * Sets the titles of each column according to the type of experiment (single or dual).
     */
    private void setTitle(){
        if (singleOrDual == 4) {
            Label sample = new SampleLabel("Sample");
            Label sequence = new Label("Sequence");
            sequence.setMinWidth(sequenceLength * 40.0);
            sequence.setAlignment(Pos.BASELINE_CENTER);
            Label index = new IdLabel("Id");
            HBox title = new HBox(sample, sequence, index);
            this.gridPane.add(title, 1, 0);
        }else{
            Label index1 = new IdLabel("Id1");
            Label sequence1 = new Label("Sequence1");
            sequence1.setMinWidth(sequenceLength * 40.0);
            sequence1.setAlignment(Pos.BASELINE_CENTER);
            Label sample = new SampleLabel("Sample");
            Label sequence2 = new Label("Sequence2");
            sequence2.setMinWidth(sequenceLength * 40.0);
            sequence2.setAlignment(Pos.BASELINE_CENTER);
            Label index2 = new IdLabel("Id2");
            HBox title = new HBox(index1, sequence1, sample, sequence2, index2);
            this.gridPane.add(title, 1, 0);
        }
    }
}
