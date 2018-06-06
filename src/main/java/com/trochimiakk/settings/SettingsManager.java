package com.trochimiakk.settings;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.trochimiakk.exceptions.FailedToLoadSettingsException;
import com.trochimiakk.exceptions.FailedToSaveSettingsException;

import java.io.*;
import java.util.Properties;

public class SettingsManager {

    private String settinsFileLocation;

    @Inject
    public SettingsManager(@Named("settings file location")String settinsFileLocation) {
        this.settinsFileLocation = settinsFileLocation;
    }

    public Properties loadSettings() throws FailedToLoadSettingsException {
        try {
            FileInputStream fileInputStream = new FileInputStream(settinsFileLocation);
            Properties settings = new Properties();
            settings.load(fileInputStream);
            fileInputStream.close();
            return settings;
        } catch (IOException e) {
            throw new FailedToLoadSettingsException("Failed to load settings");
        }
    }

    public void saveSettings(Properties settings) throws FailedToSaveSettingsException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(settinsFileLocation);
            settings.store(fileOutputStream, "Settings");
            fileOutputStream.close();
        } catch (IOException e) {
            throw new FailedToSaveSettingsException("Failed to save settings");
        }
    }

    public Properties getDefaultSettings() {
        Properties settings = new Properties();
        settings.setProperty("outputFolder", System.getProperty("user.home") + File.separator + ".Flashcards" + File.separator + "translations" + File.separator);
        settings.setProperty("flashcardsManager", "local");
        return settings;
    }
}
