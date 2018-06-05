package com.trochimiakk.flashcards;

import com.trochimiakk.exceptions.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class ApiFlashcardsManager implements FlashcardsManager {
    @Override
    public void addFlashcard(Flashcard flashcard) throws InvalidFlashcardException {
        throw new NotImplementedException();
    }

    @Override
    public void setOutputFolder(String outputFolder) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveFlashcards(String translationsFileName) throws EmptyFlashcardsListException, EmptyFlashcardsFileNameException, FiledToSaveFlashcardsException {
        throw new NotImplementedException();
    }

    @Override
    public String getOutputFolder() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getFilesList() throws FailedToLoadFilesListException {
        throw new NotImplementedException();
    }

    @Override
    public void loadFlashcards(String fileName) throws FailedToLoadFlashcardsException {
        throw new NotImplementedException();
    }

    @Override
    public int getNumberOfFlashcards() {
        throw new NotImplementedException();
    }

    @Override
    public Flashcard getRandomFlashcard() throws EmptyFlashcardsListException {
        throw new NotImplementedException();
    }

}
