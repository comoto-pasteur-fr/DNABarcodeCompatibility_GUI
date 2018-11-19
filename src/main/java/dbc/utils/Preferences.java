package dbc.utils;


import dbc.settings.SettingsLogger;


public class Preferences {


    private double gcContentMin;
    private double gcContentMax;
    private boolean homopolymer;
    private boolean hammingDistanceFilter;
    private boolean seqLevDistanceFilter;
    private boolean phaseshiftDistanceFilter;
    private int distance;

    public Preferences() {
        if (java.util.prefs.Preferences.userRoot().get(SettingsLogger.Min_GC, null)== null||
       java.util.prefs.Preferences.userRoot().get(SettingsLogger.Version, null) != "1.0.0") {
            SettingsLogger.set(SettingsLogger.Min_GC, "0");
            SettingsLogger.set(SettingsLogger.Max_GC, "0");
            SettingsLogger.set(SettingsLogger.Homopolymer, "false");
            SettingsLogger.set(SettingsLogger.Hamming, "false");
            SettingsLogger.set(SettingsLogger.SeqLev, "false");
            SettingsLogger.set(SettingsLogger.Phaseshift, "false");
            SettingsLogger.set(SettingsLogger.Distance, "3");
            SettingsLogger.set(SettingsLogger.Version, "1.0.0");

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

    private boolean isPhaseshiftDistanceFilter() {
        return phaseshiftDistanceFilter;
    }

    private void setPhaseshiftDistanceFilter(boolean phaseshiftDistanceFilter) {
        this.phaseshiftDistanceFilter = phaseshiftDistanceFilter;
    }

    public Integer getDistance() {
        if (isHammingDistanceFilter()|| isSeqLevDistanceFilter() || isPhaseshiftDistanceFilter()) {
            return distance;
        } else {
            return (3);//beware the log text put na instead of 3 if filter = null
        }
    }

    private void setDistance(int distance) {
        this.distance = distance;
    }

    public void setPreferences() {

        setGcContentMin(Double.parseDouble(SettingsLogger.get(SettingsLogger.Min_GC)));
        setGcContentMax(Double.parseDouble(SettingsLogger.get(SettingsLogger.Max_GC)));
        setHomopolymerSuppression(Boolean.valueOf(SettingsLogger.get(SettingsLogger.Homopolymer)));
        setHammingDistanceFilter(Boolean.valueOf(SettingsLogger.get(SettingsLogger.Hamming)));
        setSeqLevDistanceFilter(Boolean.valueOf(SettingsLogger.get(SettingsLogger.SeqLev)));
        setPhaseshiftDistanceFilter(Boolean.valueOf(SettingsLogger.get(SettingsLogger.Phaseshift)));
        setDistance(Integer.parseInt(SettingsLogger.get(SettingsLogger.Distance)));
    }

    public String[] get() {
        String[] preferences = new String[7];
        preferences[0] = "% min GC content : " + getGcContentMin();
        preferences[1] = "% max GC content : " + getGcContentMax();
        preferences[2] = "Homopolymer supression : " + homopolymerSuppression();
        preferences[3] = "Hamming filter : " + isHammingDistanceFilter();
        preferences[4] = "SeqLev filter : " + isSeqLevDistanceFilter();
        preferences[5] = "Phaseshift filter : " + isPhaseshiftDistanceFilter();
        preferences[6] = "distance : " + getDistance();
        return (preferences);
    }

    public String getFilter() {
        if (isHammingDistanceFilter()) {
            return ("\"hamming\"");
        } else if (isSeqLevDistanceFilter()) {
            return ("\"seqlev\"");
        } else if (isPhaseshiftDistanceFilter()) {
            return ("\"phaseshift\"");
        }else {
            return "NULL";
        }
    }
}

