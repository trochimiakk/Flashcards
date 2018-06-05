package com.trochimiakk.controllers;

import com.google.inject.Inject;
import com.trochimiakk.exceptions.EmptyFlashcardsFileNameException;
import com.trochimiakk.exceptions.EmptyFlashcardsListException;
import com.trochimiakk.exceptions.FiledToSaveFlashcardsException;
import com.trochimiakk.exceptions.InvalidFlashcardException;
import com.trochimiakk.flashcards.Flashcard;
import com.trochimiakk.flashcards.FlashcardsManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AddWordsController implements Controller {

    @FXML private TextField wordTextField;
    @FXML private TextField translationTextField;
    @FXML private TextField saveAsTextField;

    @Inject
    private FlashcardsManager flashcardsManager;

    public void callSaveFlashcardsMethod(ActionEvent actionEvent) {
        saveFlashcards();
    }

    private void saveFlashcards() {
        try {
            flashcardsManager.saveFlashcards(saveAsTextField.getText());
            showAlert("Flashcards has been saved", Alert.AlertType.INFORMATION);
        } catch (EmptyFlashcardsListException e) {
            showAlert("There is nothing to save. Flashcards list is empty", Alert.AlertType.ERROR);
        } catch (EmptyFlashcardsFileNameException e) {
            showAlert("Flashcards file name can't be empty", Alert.AlertType.ERROR);
        } catch (FiledToSaveFlashcardsException e) {
            showAlert("Failed to save flashcards", Alert.AlertType.ERROR);
        }
    }

    public void callAddFlashcardToListMethod(ActionEvent actionEvent) {
        addFlashcardToList();
    }

    private void addFlashcardToList() {
        try {
            Flashcard flashcard = new Flashcard(wordTextField.getText(), translationTextField.getText());
            flashcardsManager.addFlashcard(flashcard);
            clearFields();
        } catch (InvalidFlashcardException e) {
            showAlert("Word or/and translation is empty", Alert.AlertType.ERROR);
        }
    }

    private void clearFields() {
        wordTextField.setText("");
        translationTextField.setText("");
        wordTextField.requestFocus();
    }
}
