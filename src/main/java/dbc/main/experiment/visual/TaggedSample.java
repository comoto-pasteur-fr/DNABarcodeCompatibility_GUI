package dbc.main.experiment.visual;

import javafx.scene.layout.HBox;

/**
 * Use for the construction of visual result.
 * Creates the visual association between the sample and the barcode(s) according to the platform.
 */
class TaggedSample extends HBox {

    /** The result line for a specific simple.*/
    private String [] result;

    /** The length of the DNA barcode. */
    private int sequenceLength;

    /**
     * Constructor.
     * @param resultLine the current line of the double array result considered and provided by the R package.
     * @param platform the platform used for the experiment.
     */
    TaggedSample(String[] resultLine, int platform){
        this.result = resultLine;
        if(result.length == 4) {
            String[] sequence = resultLine[3].split("");
            this.sequenceLength = resultLine[3].length();

            this.getChildren().add(new SampleLabel(resultLine[0]));//the sample
            for (int i = 0; i <= sequenceLength - 1; i++) {// the sequence
                this.getChildren().add(new ColorLabel(sequence[i], platform));
            }
            this.getChildren().add(new IdLabel(resultLine[2])); // the lane
        }else{
            String[] sequence1 = resultLine[3].split("");
            String[] sequence2 = resultLine[5].split("");
            this.sequenceLength = resultLine[3].length();

            this.getChildren().add(new IdLabel(resultLine[2])); // the id1

            for (int i = 0; i <= sequenceLength - 1; i++) {// the sequence1
                this.getChildren().add(new ColorLabel(sequence1[i], platform));
            }
            this.getChildren().add(new SampleLabel(resultLine[0]));//the sample

            for (int i = 0; i <= sequenceLength - 1; i++) {// the sequence2
                this.getChildren().add(new ColorLabel(sequence2[i], platform));
            }
            this.getChildren().add(new IdLabel(resultLine[4])); // the id2
        }
    }


    public String toString(){
        StringBuilder a = new StringBuilder();
        for (int i = 0; i <= result.length-1 ; i++){
            a.append(" ").append(result[i]);
        }
        String NewLigne=System.getProperty("line.separator");
        return ("line =  " +a + NewLigne);
    }


    public int getSequenceLength() {
        return sequenceLength;
    }
}
