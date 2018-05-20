package com.trochimiakk.config;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.trochimiakk.flashcards.ApiFlashcardsManager;
import com.trochimiakk.flashcards.FlashcardsManager;
import com.trochimiakk.flashcards.LocalFlashcardsManager;

import javax.inject.Named;

public class FlashcardsManagerProvider implements Provider<FlashcardsManager> {

    @Inject
    @Named("flashcardsManager")
    String flashcardsManager;

    @Inject
    @Named("outputFolder")
    private String outputFolder;

    @Override
    public FlashcardsManager get() {
        System.out.println(flashcardsManager);
        if (flashcardsManager.equals("local")){
            LocalFlashcardsManager localFlashcardsManager = new LocalFlashcardsManager();
            localFlashcardsManager.setOutputFolder(outputFolder);
            return localFlashcardsManager;
        }
        return new ApiFlashcardsManager();
    }
}
