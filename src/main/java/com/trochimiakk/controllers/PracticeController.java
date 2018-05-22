package com.trochimiakk.controllers;

import com.google.inject.Inject;
import com.trochimiakk.exceptions.EmptyFlashcardsListException;
import com.trochimiakk.exceptions.FailedToLoadFilesListException;
import com.trochimiakk.exceptions.FailedToLoadFlashcardsException;
import com.trochimiakk.flashcards.Flashcard;
import com.trochimiakk.flashcards.FlashcardsManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PracticeController implements Initializable, Controller {

    @FXML
    private ComboBox<String> filesListComboBox;

    @FXML
    private Label mistakesLabel;

    @FXML
    private Label progressLabel;

    @FXML
    private Label correctAnswerLabel;

    @FXML
    private TextField wordTextField;

    @FXML
    private TextField translationTextField;

    @FXML
    private CheckBox reverseWordsOrderCheckBox;

    @FXML
    private Button checkAnswerButton;

    @Inject
    private FlashcardsManager flashcardsManager;

    private int totalNumberOfFlashcards;
    private int numberOfMistakes;
    private int numberOfTranslatedWords;
    private String correctTranslation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateFilesList();
    }

    private void generateFilesList() {
        try {
            List<String> filesList = flashcardsManager.getFilesList();
            filesListComboBox.getItems().addAll(filesList);
        } catch (FailedToLoadFilesListException e) {
            showAlert("Failed to load list of files.", Alert.AlertType.ERROR);
        }
    }

    public void loadFlashcardsAndStart(ActionEvent actionEvent) {
        if (filesListComboBox.getSelectionModel().getSelectedIndex() == -1){
            showAlert("No file selected", Alert.AlertType.ERROR);
        } else{
            String fileName = filesListComboBox.getSelectionModel().getSelectedItem();
            try {
                flashcardsManager.loadFlashcards(fileName);
                cleanCorrectAnswerLabel();
                initializeStats();
                displayFlashcard();
                showStatsLabels(true);
                refreshStats();
                disableInputControls(false);
            } catch (FailedToLoadFlashcardsException e) {
                showAlert("Failed to load flashcards", Alert.AlertType.ERROR);
            }
        }
    }

    private void cleanCorrectAnswerLabel() {
        correctAnswerLabel.getStyleClass().removeAll("correct-answer", "wrong-answer");
        if (!correctAnswerLabel.getText().equals("")){
            correctAnswerLabel.setText("");
        }
    }

    private void initializeStats() {
        totalNumberOfFlashcards = flashcardsManager.getNumberOfFlashcards();
        numberOfMistakes = 0;
        numberOfTranslatedWords = 0;
    }

    private void displayFlashcard() {
        try {
            Flashcard flashcard = flashcardsManager.getRandomFlashcard();
            if (!reverseWordsOrderCheckBox.isSelected()){
                wordTextField.setText(flashcard.getWord());
                correctTranslation = flashcard.getTranslation();
            } else {
                wordTextField.setText(flashcard.getTranslation());
                correctTranslation = flashcard.getWord();
            }
        } catch (EmptyFlashcardsListException e) {
            showAlert(String.format("All words have been translated.\nMistakes: %d", numberOfMistakes), Alert.AlertType.INFORMATION);
            showStatsLabels(false);
            disableInputControls(true);
        }
    }

    private void showStatsLabels(boolean visible) {
        progressLabel.setVisible(visible);
        mistakesLabel.setVisible(visible);
    }

    private void refreshStats() {
        mistakesLabel.setText(String.format("Mistakes: %d", numberOfMistakes));
        progressLabel.setText(String.format("Progress: %d/%d", numberOfTranslatedWords, totalNumberOfFlashcards));
    }

    private void disableInputControls(boolean disable) {
        wordTextField.setDisable(disable);
        translationTextField.setDisable(disable);
        checkAnswerButton.setDisable(disable);
    }

    public void checkAnswer(ActionEvent actionEvent) {
        String word = wordTextField.getText();
        String translation = translationTextField.getText();
        cleanCorrectAnswerLabel();
        if (!translation.equals(correctTranslation)){
            correctAnswerLabel.setText(String.format("Wrong! %s - %s", word, correctTranslation));
            numberOfMistakes++;
            addCssClassToCorrectAnswerLabel("wrong-answer");
        } else{
            correctAnswerLabel.setText(String.format("Correct! %s - %s", word, correctTranslation));
            addCssClassToCorrectAnswerLabel("correct-answer");
        }
        numberOfTranslatedWords++;
        prepareTranslationTextField();
        refreshStats();
        displayFlashcard();
    }

    private void prepareTranslationTextField() {
        translationTextField.setText("");
        translationTextField.requestFocus();
    }

    private void addCssClassToCorrectAnswerLabel(String cssClass) {
        correctAnswerLabel.getStyleClass().add(cssClass);
    }
}
