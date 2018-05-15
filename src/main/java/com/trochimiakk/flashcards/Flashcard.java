package com.trochimiakk.flashcards;

public class Flashcard {

    private String word;
    private String translation;

    public Flashcard(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }
}
