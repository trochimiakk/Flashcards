package com.trochimiakk.flashcards;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trochimiakk.exceptions.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

public class LocalFlashcardsManager implements FlashcardsManager {

    private List<Flashcard> flashcardList = new ArrayList<Flashcard>();
    private String outputFolder;

    public void addFlashcard(Flashcard flashcard) throws InvalidFlashcardException {
        if (!flashcard.isValid()){
            throw new InvalidFlashcardException("Word and/or translation is empty");
        }
        flashcardList.add(flashcard);
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
            flashcardList.clear();
        } catch (IOException e) {
            throw new FiledToSaveFlashcardsException("Failed to save flashcards");
        }
    }

    @Override
    public String getOutputFolder() {
        return outputFolder;
    }

    @Override
    public List<String> getFilesList() throws FailedToLoadFilesListException {
        try {
            List<String> filesList = Files.list(Paths.get(outputFolder))
                    .filter(file -> file.toString().endsWith(".json"))
                             .map(file -> file.getFileName().toString())
                    .collect(toList());
            return filesList;
        } catch (IOException e) {
            throw new FailedToLoadFilesListException("Failed to load files list");
        }
    }

    @Override
    public void loadFlashcards(String fileName) throws FailedToLoadFlashcardsException {
        try {
            Gson gson = new Gson();
            String json = new String(Files.readAllBytes(Paths.get(outputFolder + fileName)));
            Type type = new TypeToken<List<Flashcard>>(){}.getType();
            flashcardList = gson.fromJson(json, type);
        } catch (IOException e) {
            throw new FailedToLoadFlashcardsException("Failed to load flashcards");
        }
    }

    @Override
    public int getNumberOfFlashcards() {
        return flashcardList.size();
    }

    @Override
    public Flashcard getRandomFlashcard() throws EmptyFlashcardsListException {
        if (flashcardList.size() == 0){
            throw new EmptyFlashcardsListException("Flashcards list is empty");
        }
        Random random = new Random();
        int randomNumber = random.nextInt(flashcardList.size());
        Flashcard randomFlashcard = flashcardList.get(randomNumber);
        flashcardList.remove(randomFlashcard);
        return randomFlashcard;
    }


}
