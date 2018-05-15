package com.trochimiakk.controllers;

import com.trochimiakk.flashcards.FlashcardsManager;
import com.trochimiakk.flashcards.LocalFlashcardsManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private AddWordsController addWordsPanelController;
    @FXML private PracticeController practicePanelController;
    @FXML private SettingsController settingsPanelController;
    @FXML private GridPane addWordsPanel;
    @FXML private GridPane practicePanel;
    @FXML private GridPane settingsPanel;
    @FXML private MenuItem menuItemAddWords;
    @FXML private MenuItem menuItemPractice;
    @FXML private MenuItem menuItemSettings;

    private FlashcardsManager flashcardsManager;

    public void initialize(URL location, ResourceBundle resources) {
        flashcardsManager = new LocalFlashcardsManager();
        addWordsPanelController.setParentController(this);
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

    public FlashcardsManager getFlashcardsManager() {
        return flashcardsManager;
    }

    public void setFlashcardsManager(FlashcardsManager flashcardsManager) {
        this.flashcardsManager = flashcardsManager;
    }

    public void showAlert(String message, Alert.AlertType alertType) {
        new Alert(alertType, message).show();
    }
}
