package com.trochimiakk.flashcards;

import com.trochimiakk.exceptions.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public interface FlashcardsManager {

    void addFlashcard(Flashcard flashcard) throws InvalidFlashcardException;

    default void setOutputFolder(String outputFolder){
        throw new NotImplementedException();
    }

    void saveFlashcards(String translationsFileName) throws EmptyFlashcardsListException, EmptyFlashcardsFileNameException, FiledToSaveFlashcardsException;

    String getOutputFolder();

    List<String> getFilesList() throws FailedToLoadFilesListException;

    List<Flashcard> loadFlashcards(String fileName) throws FailedToLoadFlashcardsException;

    int getNumberOfFlashcards();

    Flashcard getRandomFlashcard() throws EmptyFlashcardsListException;
}
