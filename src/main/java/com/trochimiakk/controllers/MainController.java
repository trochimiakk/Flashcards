package com.trochimiakk.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainController implements Initializable, Controller {

    @FXML private GridPane addWordsPanel;
    @FXML private GridPane practicePanel;
    @FXML private GridPane settingsPanel;
    @FXML private MenuItem menuItemAddWords;
    @FXML private MenuItem menuItemPractice;
    @FXML private MenuItem menuItemSettings;

    public void initialize(URL location, ResourceBundle resources) {
        createRequiredFolders();
    }

    private void createRequiredFolders() {
        String userHomeDirectory = System.getProperty("user.home");
        try {
            if (Files.notExists(Paths.get(userHomeDirectory + File.separator + ".Flashcards" + File.separator + "translations"))) {
                Files.createDirectories(Paths.get(userHomeDirectory + File.separator + ".Flashcards" + File.separator + "translations"));
            }
            if (Files.notExists(Paths.get(userHomeDirectory + File.separator + ".Flashcards" + File.separator + "settings"))) {
                Files.createDirectories(Paths.get(userHomeDirectory + File.separator + ".Flashcards" + File.separator + "settings"));
            }
        } catch (IOException e) {
            showAlert("Failed to create required folders and files", Alert.AlertType.ERROR);
        }
    }


        public void changePanel(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(menuItemAddWords)){
            addWordsPanel.setVisible(true);
            practicePanel.setVisible(false);
            settingsPanel.setVisible(false);
        } else if (actionEvent.getSource().equals(menuItemPractice)){
            practicePanel.setVisible(true);
            addWordsPanel.setVisible(false);
            settingsPanel.setVisible(false);
        } else{
            addWordsPanel.setVisible(false);
            practicePanel.setVisible(false);
            settingsPanel.setVisible(true);
        }
    }
}
