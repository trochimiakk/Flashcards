package com.trochimiakk.flashcards;

import com.trochimiakk.exceptions.EmptyFlashcardsFileNameException;
import com.trochimiakk.exceptions.EmptyFlashcardsListException;
import com.trochimiakk.exceptions.FiledToSaveFlashcardsException;
import com.trochimiakk.exceptions.InvalidFlashcardException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ApiFlashcardsManager implements FlashcardsManager {
    @Override
    public void addFlashcard(String word, String translation) throws InvalidFlashcardException {
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
}
