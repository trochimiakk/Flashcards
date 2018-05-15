package com.trochimiakk.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;

public class MainController {

    @FXML private GridPane addWordsPanel;
    @FXML private GridPane practicePanel;
    @FXML private GridPane settingsPanel;
    @FXML private MenuItem menuItemAddWords;
    @FXML private MenuItem menuItemPractice;
    @FXML private MenuItem menuItemSettings;


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
