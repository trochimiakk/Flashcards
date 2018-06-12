package com.trochimiakk.controllers;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainController implements Initializable, Controller {

    @FXML private MenuItem menuItemAddWords;
    @FXML private MenuItem menuItemPractice;
    @FXML private MenuItem menuItemSettings;
    @FXML private StackPane contentPanel;
    @Inject
    Injector injector;

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
        MenuItem clickedMenuItem = (MenuItem) actionEvent.getSource();
        String panelName = createPanelName(clickedMenuItem.getId());
        boolean panelAlreadyVisible = contentPanel.getChildren().stream().anyMatch(children -> children.getId().equals(String.format("%s%s", panelName, "Panel")));
        if (!panelAlreadyVisible){
            try {
                loadPanel(panelName);
            } catch (IOException e) {
                showAlert("Failed to load: " + panelName, Alert.AlertType.ERROR);
            }
        }
    }

    private void loadPanel(String panelName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(injector::getInstance);
        fxmlLoader.setLocation(getClass().getResource(String.format("/views/%s.fxml", panelName)));
        Pane loadedPanel = fxmlLoader.load();
        contentPanel.getChildren().setAll(loadedPanel);
    }

    private String createPanelName(String menuItemId) {
        String panelName = menuItemId.replace("menuItem", "");
        panelName = Character.toLowerCase(panelName.charAt(0)) + panelName.substring(1);
        return panelName;
    }
}
