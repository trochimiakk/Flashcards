package com.trochimiakk.controllers;

import javafx.scene.control.Alert;

public interface Controller {

    default void showAlert(String message, Alert.AlertType alertType) {
        new Alert(alertType, message).show();
    }

}
