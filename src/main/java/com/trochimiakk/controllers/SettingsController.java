package com.trochimiakk.controllers;

import com.google.inject.Inject;
import com.trochimiakk.exceptions.FailedToSaveSettingsException;
import com.trochimiakk.flashcards.ApiFlashcardsManager;
import com.trochimiakk.flashcards.FlashcardsManager;
import com.trochimiakk.flashcards.LocalFlashcardsManager;
import com.trochimiakk.settings.SettingsManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import javax.inject.Named;
import java.io.File;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class SettingsController implements Initializable, Controller {

    @Inject
    private FlashcardsManager flashcardsManager;

    @Inject
    private SettingsManager settingsManager;

    @FXML
    private TextField outputFolderTextField;

    @FXML
    private CheckBox localFlashcardsManagerCheckbox;

    @FXML
    private CheckBox apiFlashcardsManagerCheckbox;

    @FXML
    private Button saveSettingsButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInitialOutputFolder(flashcardsManager.getOutputFolder());
        initializeFlashcardManagersCheckboxes();
    }

    private void disableSaveSettingsButton(boolean disable) {
        saveSettingsButton.setDisable(disable);
    }

    private void initializeFlashcardManagersCheckboxes() {
        if (flashcardsManager.getClass().equals(LocalFlashcardsManager.class)){
            localFlashcardsManagerCheckbox.setSelected(true);
            apiFlashcardsManagerCheckbox.setSelected(false);
        } else{
            localFlashcardsManagerCheckbox.setSelected(false);
            apiFlashcardsManagerCheckbox.setSelected(true);
        }
    }

    public void callChangeOutputFolder(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select folder");
        try {
            String outputFolder = directoryChooser.showDialog(outputFolderTextField.getScene().getWindow()).getAbsolutePath();
            outputFolder = validatePath(outputFolder);
            changeOutputFolder(outputFolder);
            disableSaveSettingsButton(false);
        } catch (NullPointerException e){
            //No directory selected
        }
    }

    private String validatePath(String outputFolder) {
        if (outputFolder.endsWith(File.separator)){
            return outputFolder;
        }
        return outputFolder + File.separator;
    }

    private void setInitialOutputFolder(String outputFolder){
        outputFolderTextField.setText(outputFolder);
    }

    private void changeOutputFolder(String outputFolder) {
        outputFolderTextField.setText(outputFolder);
        try{
            flashcardsManager.setOutputFolder(outputFolder);
        } catch (UnsupportedOperationException e){
            //ApiFlashcardsManager does not store information about output folder
        }
    }

    public void changeFlashcardsManager(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(localFlashcardsManagerCheckbox)){
            boolean useLocalFlashcardsManager = localFlashcardsManagerCheckbox.isSelected();
            apiFlashcardsManagerCheckbox.setSelected(!useLocalFlashcardsManager);
        } else{
            boolean useApiFlashcardsManager = apiFlashcardsManagerCheckbox.isSelected();
            localFlashcardsManagerCheckbox.setSelected(!useApiFlashcardsManager);
        }
        disableSaveSettingsButton(false);
    }

    public void saveSettings(ActionEvent actionEvent) {
        String selectedFlashcardsManager = localFlashcardsManagerCheckbox.isSelected() ? "local" : "api";
        String outputFolder = outputFolderTextField.getText();
        Properties settings = new Properties();
        settings.setProperty("outputFolder", outputFolder);
        settings.setProperty("flashcardsManager", selectedFlashcardsManager);
        try {
            settingsManager.saveSettings(settings);
            disableSaveSettingsButton(true);
            if (checkIfChangesRequireRestart()){
                showAlert("Settings has been saved\nRestart application to apply changes", Alert.AlertType.INFORMATION);
            } else{
                showAlert("Settings has been saved", Alert.AlertType.INFORMATION);
            }
        } catch (FailedToSaveSettingsException e) {
            showAlert("Failed to save settings", Alert.AlertType.ERROR);
        }
    }

    private boolean checkIfChangesRequireRestart() {
        if (flashcardsManager.getClass().equals(LocalFlashcardsManager.class) && !localFlashcardsManagerCheckbox.isSelected()){
            return true;
        } else if (flashcardsManager.getClass().equals(ApiFlashcardsManager.class) && !apiFlashcardsManagerCheckbox.isSelected()){
            return true;
        }
        return false;
    }
}
