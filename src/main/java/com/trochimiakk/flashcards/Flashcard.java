package com.trochimiakk.flashcards;

import java.util.Objects;

public class Flashcard {

    private String word;
    private String translation;

    public Flashcard(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    boolean isValid(){
        return !word.equals("") && !translation.equals("");
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flashcard flashcard = (Flashcard) o;
        return Objects.equals(word, flashcard.word) &&
                Objects.equals(translation, flashcard.translation);
    }

    @Override
    public int hashCode() {

        return Objects.hash(word, translation);
    }
}
