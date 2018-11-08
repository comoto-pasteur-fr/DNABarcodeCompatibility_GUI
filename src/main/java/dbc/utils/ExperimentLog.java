package dbc.utils;

import dbc.adapters.AdapterList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ExperimentLog extends ArrayList<String>{


    private Preferences preferences;

    private Date date;

    public ExperimentLog(Preferences preferences){
        super(10);
        this.preferences = preferences;
    }

    public void setLog(String filePath, String platform, AdapterList adapters, int sampleNumber, int multiplexingLevel){
        this.clear();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
        this.date = new Date();
        this.add(simpleDateFormat.format(this.date));
        this.add("");
        this.add("File : "+ filePath);
        this.add("Platform : " + platform);
        this.add("Sequence length : "+ Integer.toString(adapters.getLengthSeq()));
        this.add("Sample number : " + sampleNumber);
        this.add("Multiplexing level : "+ multiplexingLevel);
        this.addAll(Arrays.asList(preferences.get()));
        this.add("----- Index ----------------------------");
        this.addAll(Arrays.asList(adapters.info()));
    }

    public void setLog(String filePath1,String filePath2,String platform, AdapterList adapters1,AdapterList adapters2,int sampleNumber, int multiplexingLevel){
        this.clear();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
        this.add(simpleDateFormat.format(new Date()));
        this.add("");
        this.add("File1 : "+ filePath1);
        this.add("File2 : "+ filePath2);
        this.add("Platform : " + platform);
        this.add("Sequence length file1: "+ Integer.toString(adapters1.getLengthSeq()));
        this.add("Sequence length file2: "+ Integer.toString(adapters2.getLengthSeq()));
        this.add("Sample number : " + sampleNumber);
        this.add("Multiplexing level : "+ multiplexingLevel);
        this.addAll(Arrays.asList(preferences.get()));
        this.add("----- Index 1 ----------------------------");
        this.addAll(Arrays.asList(adapters1.info()));
        this.add("----- Index 2 ----------------------------");
        this.addAll(Arrays.asList(adapters2.info()));
    }

    public  String getLog(){
        StringBuilder log = new StringBuilder();
        for (String s:this) {
          log.append(s).append(System.getProperty("line.separator"));
        }
        return (log.toString());
    }

    public Date getDate() {
        return date;
    }

    public String getFormattedDate(){
        if(this.getDate()!= null){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    return ("XXXXXX_"+ simpleDateFormat.format(this.date));}
    else {return (null);}
    }
}


