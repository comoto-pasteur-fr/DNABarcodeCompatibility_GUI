package dbc.adapters;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Created by ctreb on 19/01/2018.
 */
public class Adapter {

    private StringProperty id;
    private StringProperty sequence;
    private StringProperty gcContent;
    private StringProperty homopolymer;
    private BooleanProperty selected;
    private int length;

    public Adapter(String id, String sequence, double gcContent, double homopolymer){
        this.id = new SimpleStringProperty(id);
        this.sequence = new SimpleStringProperty(sequence);
        this.length = sequence.length();

        this.gcContent = new SimpleStringProperty(Double.toString(gcContent));

        if(homopolymer == 0.0){
            this.homopolymer = new SimpleStringProperty("No");
        }else{
            this.homopolymer = new SimpleStringProperty("Yes");
        }
        this.selected = new SimpleBooleanProperty(false);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getSequence() {
        return sequence.get();
    }

    public StringProperty sequenceProperty() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence.set(sequence);
    }



    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty unselectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public void setSelectionStatus(){
        if(isSelected()){
            setSelected(true);
        }else{
            setSelected(false);
        }
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public double getGcContent() {
        return Double.parseDouble(gcContent.get());
    }

    public StringProperty gcContentProperty() {
        return gcContent;
    }

    public Boolean getHomopolymerStatus() {
        return homopolymer.get().equals("Yes");
    }

    public StringProperty homopolymerProperty() {
        return homopolymer;
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public String toString(){
        return ("\""+getId()+"\"");
    }

    public String adapterInfo(){
        return (String.valueOf(this.isSelected()) +
                "    " +
                this.getId() +
                "    " +
                this.getSequence() +
                "    " +
                this.getGcContent() +
                "    " +
                this.getHomopolymerStatus());
    }
}
