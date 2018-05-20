package com.trochimiakk.flashcards;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.trochimiakk.exceptions.EmptyFlashcardsFileNameException;
import com.trochimiakk.exceptions.EmptyFlashcardsListException;
import com.trochimiakk.exceptions.FiledToSaveFlashcardsException;
import com.trochimiakk.exceptions.InvalidFlashcardException;

import javax.inject.Named;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LocalFlashcardsManager implements FlashcardsManager {

    private List<Flashcard> flashcardList = new ArrayList<Flashcard>();
    @Inject
    @Named("outputFolder")
    private String outputFolder;

    public void addFlashcard(String word, String translation) throws InvalidFlashcardException {
        if (word.equals("") || translation.equals("")){
            throw new InvalidFlashcardException("Word and/or translation is empty");
        }
        flashcardList.add(new Flashcard(word, translation));
    }

    public void setOutputFolder(String outputFolder){
        this.outputFolder = outputFolder;
    }

    @Override
    public void saveFlashcards(String translationsFileName) throws EmptyFlashcardsListException, EmptyFlashcardsFileNameException, FiledToSaveFlashcardsException {
        if (translationsFileName.equals("")){
            throw new EmptyFlashcardsFileNameException("Flashcards file name can't be empty");
        }
        if (flashcardList.size() == 0){
            throw new EmptyFlashcardsListException("Flashcards list is empty");
        }
        Gson gson = new Gson();
        try {
            Files.write(Paths.get(outputFolder + translationsFileName + ".json"), gson.toJson(flashcardList).getBytes());
        } catch (IOException e) {
            throw new FiledToSaveFlashcardsException("Failed to save flashcards");
        }
    }


}
