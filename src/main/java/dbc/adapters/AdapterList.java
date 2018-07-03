package dbc.adapters;



import dbc.utils.Preferences;

import java.util.ArrayList;

public class AdapterList extends ArrayList<Adapter>{

    private int lengthSeq;
    private double selected;

    public void filterList(Preferences preferences){
        for (Adapter adapter:this) {
            if(preferences.getGcContentMin()<= adapter.getGcContent() && adapter.getGcContent()<= preferences.getGcContentMax()){
                adapter.setSelected(true);
                if(preferences.homopolymerSuppression() && adapter.getHomopolymerStatus()){
                    adapter.setSelected(false);
                }else{
                    adapter.setSelected(true);
                }
            }else{
                adapter.setSelected(false);
            }
        }
    }

    public AdapterList getUnselected(){
        AdapterList unselectedAdapters = new AdapterList();
        for (Adapter a : this) {
            if (!a.isSelected()) {
                unselectedAdapters.add(a);
            }
        }
        return unselectedAdapters;
    }

    public int getLengthSeq() {
        return lengthSeq;
    }

    public void setLengthSeq() {
        this.lengthSeq = (this.get(0).getLength());
    }

    public double getSelected() {
        int i = 0;
        for(Adapter a : this){
           if (a.isSelected()) {
               i++;
           }
        }
        this.selected =  ((double)i/(double)this.size()*100);
        return selected;
    }

    public String [] info(){
        String [] info = new String[this.size()];
        int i = 0;
        for(Adapter a : this){
            info[i] = a.adapterInfo();
            i++;
        }
        return info;
    }
}
