package dbc.settings;



import dbc.utils.ErrorMessage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * The controller for the settings , including the metric (hamming or seqLev and value, the homopolymer status.
 */
public class SettingsController implements Initializable{

    /** if any modification has been made on the settings.*/
    public static Boolean modification = false;

    //FIXME seqLength has to be set according to the length of the adapters
    /** the length of DNA barcodes.*/
    private final int seqLength = 6;

    /** If homopolymers are refused.*/
    @FXML
    private CheckBox homopolymer;

    /** If Hamming distance have to be taken to account.*/
    @FXML
    private CheckBox hamming;

    /** The value of the distance (Hamming or Seqlev) if selected.*/
    @FXML
    private TextField distance;

    /** if SeqLev distance have to be taken to account.*/
    @FXML
    private CheckBox seqlev;

    /** if Phaseshift distance have to be taken to account.*/
    @FXML
    private CheckBox phaseshift;

    /** the minimum value of GC content.*/
    @FXML
    private TextField minGC;

    /** the maximum value of GC content.*/
    @FXML
    private TextField maxGC;

    @FXML
    private Button save;

    /**
     * Initialize the window.
     * @param location n/a
     * @param resources n/a
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modification = false;
        setSettingWindow();
    }

    /**
     * Save the user settings.
     */
    @FXML
    private void saveSettings() {
        SettingsLogger.set(SettingsLogger.Min_GC, this.minGC.getText());
        SettingsLogger.set(SettingsLogger.Max_GC, this.maxGC.getText());
        SettingsLogger.set(SettingsLogger.Homopolymer, Boolean.toString(homopolymer.isSelected()));
        SettingsLogger.set(SettingsLogger.Hamming, Boolean.toString(hamming.isSelected()));
        SettingsLogger.set(SettingsLogger.SeqLev, Boolean.toString(seqlev.isSelected()));
        SettingsLogger.set(SettingsLogger.Phaseshift, Boolean.toString(phaseshift.isSelected()));
        SettingsLogger.set(SettingsLogger.Distance, distance.getText());


        if(Integer.parseInt(distance.getText()) <= this.seqLength){
            SettingsLogger.set(SettingsLogger.Distance, distance.getText());
        }else{
            ErrorMessage.showMessage("Please enter a suitable value (< DNA sequence)");
        }
        SettingsLogger.set(SettingsLogger.Distance, distance.getText());
        modification = true;
        ((Stage)this.minGC.getScene().getWindow()).close();
    }

    /**
     * Set the current settings values to default values.
     */
    @FXML
    private void setSettingsToDefault() {

        this.minGC.setText("0");
        this.maxGC.setText("100");
        this.homopolymer.setSelected(false);
        this.hamming.setSelected(false);
        this.phaseshift.setSelected(false);
        this.setHammingMetric();
        this.seqlev.setSelected(false);
        this.setSeqlevMetric();
        this.distance.setText("3");
    }

    @FXML
    void cancel(){
        ((Stage)this.minGC.getScene().getWindow()).close();
    }

    @FXML
    void setHammingMetric(){
        if(this.hamming.isSelected()){
            this.distance.setEditable(true);
            this.seqlev.setSelected(false);
            this.phaseshift.setSelected(false);
        }
    }

    @FXML
    void setSeqlevMetric(){
        if(this.seqlev.isSelected()){
            this.distance.setEditable(true);
            this.hamming.setSelected(false);
            this.phaseshift.setSelected(false);
        }

    }

    @FXML
    void setPhaseshiftMetric(){
        if(this.phaseshift.isSelected()){
            this.distance.setEditable(true);
            this.hamming.setSelected(false);
            this.seqlev.setSelected(false);
        }

    }

    /**
     * Sets the users settings according to previous run.
     */
    private void setSettingWindow(){
        this.minGC.setText(SettingsLogger.get(SettingsLogger.Min_GC));
        this.maxGC.setText(SettingsLogger.get(SettingsLogger.Max_GC));
        this.homopolymer.setSelected(Boolean.parseBoolean(SettingsLogger.get(SettingsLogger.Homopolymer)));
        this.hamming.setSelected(Boolean.parseBoolean(SettingsLogger.get(SettingsLogger.Hamming)));
        this.seqlev.setSelected(Boolean.parseBoolean(SettingsLogger.get(SettingsLogger.SeqLev)));
        this.phaseshift.setSelected(Boolean.parseBoolean(SettingsLogger.get(SettingsLogger.Phaseshift)));
        this.distance.setText(SettingsLogger.get(SettingsLogger.Distance));
    }

}
