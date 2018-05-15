package com.trochimiakk.controllers;

import com.trochimiakk.exceptions.EmptyFlashcardsFileNameException;
import com.trochimiakk.exceptions.EmptyFlashcardsListException;
import com.trochimiakk.exceptions.FiledToSaveFlashcardsException;
import com.trochimiakk.exceptions.InvalidFlashcardException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AddWordsController {

    private MainController parentController;
    @FXML private TextField wordTextField;
    @FXML private TextField translationTextField;
    @FXML private TextField saveAsTextField;

    public void setParentController(MainController parentController) {
        this.parentController = parentController;
    }

    public void callSaveFlashcardsMethod(ActionEvent actionEvent) {
        saveFlashcards();
    }

    private void saveFlashcards() {
        try {
            parentController.getFlashcardsManager().saveFlashcards(saveAsTextField.getText());
            parentController.showAlert("Flashcards has been saved", Alert.AlertType.INFORMATION);
        } catch (EmptyFlashcardsListException e) {
            parentController.showAlert("There is nothing to save. Flashcards list is empty", Alert.AlertType.ERROR);
        } catch (EmptyFlashcardsFileNameException e) {
            parentController.showAlert("Flashcards file name can't be empty", Alert.AlertType.ERROR);
        } catch (FiledToSaveFlashcardsException e) {
            parentController.showAlert("Failed to save flashcards", Alert.AlertType.ERROR);
        }
    }

    public void callAddFlashcardToListMethod(ActionEvent actionEvent) {
        addFlashcardToList();
    }

    private void addFlashcardToList() {
        try {
            parentController.getFlashcardsManager().addFlashcard(wordTextField.getText(), translationTextField.getText());
            clearFields();
        } catch (InvalidFlashcardException e) {
            parentController.showAlert("Word or/and translation is empty", Alert.AlertType.ERROR);
        }
    }

    private void clearFields() {
        wordTextField.setText("");
        translationTextField.setText("");
        wordTextField.requestFocus();
    }
}
