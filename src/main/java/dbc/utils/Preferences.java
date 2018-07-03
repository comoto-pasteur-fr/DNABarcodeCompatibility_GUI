package dbc.utils;


import dbc.settings.SettingsLogger;


public class Preferences {


    private double gcContentMin;
    private double gcContentMax;
    private boolean homopolymer;
    private boolean hammingDistanceFilter;
    private boolean seqLevDistanceFilter;
    private int metric;

    public Preferences() {
        if (java.util.prefs.Preferences.userRoot().get(SettingsLogger.Min_GC, null)== null){
            SettingsLogger.set(SettingsLogger.Min_GC, "0");
            SettingsLogger.set(SettingsLogger.Max_GC, "100");
            SettingsLogger.set(SettingsLogger.Homopolymer, "false");
            SettingsLogger.set(SettingsLogger.Hamming, "false");
            SettingsLogger.set(SettingsLogger.SeqLev, "false");
            SettingsLogger.set(SettingsLogger.MetricD, "3");
        }
        setPreferences();
    }

    public double getGcContentMin() {
        return gcContentMin;
    }

    private void setGcContentMin(double gcContentMin) {
        this.gcContentMin = gcContentMin;
    }

    public double getGcContentMax() {
        return gcContentMax;
    }

    private void setGcContentMax(double gcContentMax) {
        this.gcContentMax = gcContentMax;
    }

    public boolean homopolymerSuppression() {
        return homopolymer;
    }

    private void setHomopolymerSuppression(boolean homopolymerSuppression) {
            this.homopolymer = homopolymerSuppression;
    }

    private boolean isHammingDistanceFilter() {
        return hammingDistanceFilter;
    }

    private void setHammingDistanceFilter(boolean hammingDistanceFilter) {
        this.hammingDistanceFilter = hammingDistanceFilter;
    }

    private boolean isSeqLevDistanceFilter() {
        return seqLevDistanceFilter;
    }

    private void setSeqLevDistanceFilter(boolean seqLevDistanceFilter) {
        this.seqLevDistanceFilter = seqLevDistanceFilter;
    }

    public Integer getMetric() {
        if (isHammingDistanceFilter()|| isSeqLevDistanceFilter()) {
            return metric ;
        } else {
            return (3);//beware the log text put na instead of 3 if filter = null
        }
    }

    private void setMetric(int metric) {
        this.metric = metric;
    }

    public void setPreferences() {

        setGcContentMin(Double.parseDouble(SettingsLogger.get(SettingsLogger.Min_GC)));
        setGcContentMax(Double.parseDouble(SettingsLogger.get(SettingsLogger.Max_GC)));
        setHomopolymerSuppression(Boolean.valueOf(SettingsLogger.get(SettingsLogger.Homopolymer)));
        setHammingDistanceFilter(Boolean.valueOf(SettingsLogger.get(SettingsLogger.Hamming)));
        setSeqLevDistanceFilter(Boolean.valueOf(SettingsLogger.get(SettingsLogger.SeqLev)));
        setMetric(Integer.parseInt(SettingsLogger.get(SettingsLogger.MetricD)));
    }

    public String[] get() {
        String[] preferences = new String[6];
        preferences[0] = "% min GC content : " + getGcContentMin();
        preferences[1] = "% max GC content : " + getGcContentMax();
        preferences[2] = "Homopolymer supression : " + homopolymerSuppression();
        preferences[3] = "Hamming filter : " + isHammingDistanceFilter();
        preferences[4] = "SeqLev filter : " + isSeqLevDistanceFilter();
        preferences[5] = "metric : " + getMetric();
        return (preferences);
    }

    public String getFilter() {
        if (isHammingDistanceFilter()) {
            return ("\"hamming\"");
        } else if (isSeqLevDistanceFilter()) {
            return ("\"seqlev\"");
        } else {
            return "NULL";
        }
    }
}

