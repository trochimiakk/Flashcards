package com.trochimiakk.settings;

import com.trochimiakk.exceptions.FailedToLoadSettingsException;
import com.trochimiakk.exceptions.FailedToSaveSettingsException;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SettingsManagerTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void shouldLoadSettingsFromFile() throws IOException, FailedToLoadSettingsException {
        //given
        String outputFolder = "testOutputFolder";
        String flashcardsManager = "testFlashcardsManager";
        String testSettingsFilePath = temporaryFolder.newFile("loadSettings.properties").getAbsolutePath();
        SettingsManager settingsManager = new SettingsManager(testSettingsFilePath);
        createTestSettingFile(outputFolder, flashcardsManager, testSettingsFilePath);

        //when
        Properties settings = settingsManager.loadSettings();

        //then
        assertEquals(outputFolder, settings.getProperty("outputFolder"));
        assertEquals(flashcardsManager, settings.getProperty("flashcardsManager"));
    }

    private void createTestSettingFile(String outputFolder, String flashcardsManager, String testSettingsFilePath) throws IOException {
        Properties settings = new Properties();
        settings.setProperty("outputFolder", outputFolder);
        settings.setProperty("flashcardsManager", flashcardsManager);
        FileOutputStream fileOutputStream = new FileOutputStream(testSettingsFilePath);
        settings.store(fileOutputStream, "Test Settings");
        fileOutputStream.close();
    }

    @Test(expected = FailedToLoadSettingsException.class)
    public void shouldThrowFailedToLoadSettingsExceptionWhenSettingsFileDoesNotExist() throws FailedToLoadSettingsException {
        //given
        String wrongSettingsFilePath = "wrongSettingsFilePath.properties";
        SettingsManager settingsManager = new SettingsManager(wrongSettingsFilePath);

        //when
        Properties settings = settingsManager.loadSettings();

    }

    @Test
    public void shouldCreateDefaultProperties() {
        //given
        String outputFolder = System.getProperty("user.home") + "\\.Flashcards\\translations\\";
        String flashcardsManager = "local";
        SettingsManager settingsManager = new SettingsManager("somePath");

        //when
        Properties settings = settingsManager.getDefaultSettings();

        //then
        assertEquals(outputFolder, settings.getProperty("outputFolder"));
        assertEquals(flashcardsManager, settings.getProperty("flashcardsManager"));
    }

    @Test(expected = FailedToSaveSettingsException.class)
    public void shouldThrowFailedToSaveSettingsExceptionWhenSettingsFilePathIsWrong() throws FailedToSaveSettingsException {
        //given
        String wrongSettingsFilePath = "X\\Y\\Z";
        Properties emptySettings = new Properties();
        SettingsManager settingsManager = new SettingsManager(wrongSettingsFilePath);

        //then
        settingsManager.saveSettings(emptySettings);
    }

    @Test
    public void shouldSaveSettingsToFile() throws IOException, FailedToSaveSettingsException {
        //given
        String outputFolder = "saveOutputFolder";
        String flashcardsManager = "saveFlashcardsManager";
        String testSettingsFilePath = temporaryFolder.newFile("saveSettings.properties").getAbsolutePath();
        SettingsManager settingsManager = new SettingsManager(testSettingsFilePath);
        Properties settings = new Properties();
        settings.setProperty("outputFolder", outputFolder);
        settings.setProperty("flashcardsManager", flashcardsManager);


        //when
        settingsManager.saveSettings(settings);

        //then
        String content = new String(Files.readAllBytes(Paths.get(testSettingsFilePath)));
        assertTrue(Files.exists(Paths.get(testSettingsFilePath)));
        assertTrue(content.contains("flashcardsManager=saveFlashcardsManager"));
        assertTrue(content.contains("outputFolder=saveOutputFolder"));
    }
}
