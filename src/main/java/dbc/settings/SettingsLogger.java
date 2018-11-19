package dbc.settings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.prefs.Preferences;

public class SettingsLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsLogger.class);


    public static final String R_HOME = "R_HOME";
    public static final String Min_GC = "Min_GC";
    public static final String Max_GC = "Max_GC";
    public static final String Homopolymer = "Homopolymer";
    public static final String Hamming = "Hamming";
    public static final String SeqLev = "SeqLev";
    public static final String Phaseshift = "Phaseshift";
    public static final String Distance = "Distance";
    public static final String Version = "1.0.0";


    public static void set(String key, String value) {
        LOGGER.debug("Set {} -> {}", key, value);
        Preferences.userRoot().put(key, value);
    }

    public static String get(String key) {
        final String value = Preferences.userRoot().get(key, null);
        LOGGER.debug("Get {} : {}", key, value);
        return value;
    }


}
