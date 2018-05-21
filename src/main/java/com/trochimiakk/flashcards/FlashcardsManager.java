package com.trochimiakk.flashcards;

import com.trochimiakk.exceptions.EmptyFlashcardsFileNameException;
import com.trochimiakk.exceptions.EmptyFlashcardsListException;
import com.trochimiakk.exceptions.FiledToSaveFlashcardsException;
import com.trochimiakk.exceptions.InvalidFlashcardException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface FlashcardsManager {

    void addFlashcard(String word, String translation) throws InvalidFlashcardException;

    default void setOutputFolder(String outputFolder){
        throw new NotImplementedException();
    }

    void saveFlashcards(String translationsFileName) throws EmptyFlashcardsListException, EmptyFlashcardsFileNameException, FiledToSaveFlashcardsException;

    String getOutputFolder();
}
