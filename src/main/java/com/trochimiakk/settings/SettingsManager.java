package com.trochimiakk.settings;

import com.trochimiakk.exceptions.FailedToLoadSettingsException;
import com.trochimiakk.exceptions.FailedToSaveSettingsException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SettingsManager {

    private String settinsFileLocation = System.getProperty("user.home") + "\\.Flashcards\\settings\\settings.properties";

    public Properties loadProperties() throws FailedToLoadSettingsException {
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

    public Properties getDefaultProperties() {
        Properties settings = new Properties();
        settings.setProperty("outputFolder", System.getProperty("user.home") + "\\.Flashcards\\translations\\");
        settings.setProperty("flashcardsManager", "local");
        return settings;
    }
}
