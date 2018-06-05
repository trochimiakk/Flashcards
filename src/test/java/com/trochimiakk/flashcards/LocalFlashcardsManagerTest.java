package com.trochimiakk.flashcards;

import com.trochimiakk.exceptions.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class LocalFlashcardsManagerTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private LocalFlashcardsManager localFlashcardsManager;

    @Before
    public void setUp() throws Exception {
        localFlashcardsManager = new LocalFlashcardsManager();
    }

    @Test
    public void shouldAddFlashcardToList() throws InvalidFlashcardException {
        //given
        Flashcard flashcard = new Flashcard("word", "translation");

        //when
        localFlashcardsManager.addFlashcard(flashcard);

        //then
        assertEquals(1, localFlashcardsManager.getNumberOfFlashcards());
    }

    @Test(expected = InvalidFlashcardException.class)
    public void shouldThrowInvalidFlashcardExceptionWhenFlashcardIsNotValid() throws InvalidFlashcardException {
        //given
        Flashcard invalidFlashcard = new Flashcard("", "translation");

        //when
        localFlashcardsManager.addFlashcard(invalidFlashcard);
    }

    @Test(expected = EmptyFlashcardsListException.class)
    public void shouldThrowEmptyFlashcardsListExceptionWhenNoFlashcardsHaveBeenAdded() throws EmptyFlashcardsListException, FiledToSaveFlashcardsException, EmptyFlashcardsFileNameException {
        //when
        localFlashcardsManager.saveFlashcards("fileName");
    }

    @Test(expected = EmptyFlashcardsFileNameException.class)
    public void shouldThrowEmptyFlashcardsFileNameExceptionWhenFileNameHasNotBeenProvided() throws EmptyFlashcardsListException, FiledToSaveFlashcardsException, EmptyFlashcardsFileNameException {
        //when
        localFlashcardsManager.saveFlashcards("");
    }

    @Test(expected = FiledToSaveFlashcardsException.class)
    public void shouldThrowFailedToSaveFlashcardsExceptionWhenOutputFolderIsWrong() throws EmptyFlashcardsListException, FiledToSaveFlashcardsException, EmptyFlashcardsFileNameException, InvalidFlashcardException {
        //given
        String wrongOutputFolder = "c\\d\\x\\";
        String translationsFileName = "fileName";
        localFlashcardsManager.setOutputFolder(wrongOutputFolder);
        localFlashcardsManager.addFlashcard(new Flashcard("word", "translation"));

        //when
        localFlashcardsManager.saveFlashcards(translationsFileName);
    }

    @Test
    public void shouldSaveFlashcardsToFile() throws IOException, InvalidFlashcardException, EmptyFlashcardsListException, FiledToSaveFlashcardsException, EmptyFlashcardsFileNameException {
        //given
        Flashcard flashcard = new Flashcard("word", "translation");
        String expectedFileContent = String.format("[{\"word\":\"%s\",\"translation\":\"%s\"}]", flashcard.getWord(), flashcard.getTranslation());
        String outputFolder = temporaryFolder.newFolder("flashcardsSaveTest").getAbsolutePath() + "\\";
        String translationsFileName = "fileName";
        localFlashcardsManager.setOutputFolder(outputFolder);
        localFlashcardsManager.addFlashcard(flashcard);

        //when
        localFlashcardsManager.saveFlashcards(translationsFileName);

        //then
        String savedFilePath = outputFolder + translationsFileName + ".json";
        assertTrue(Files.exists(Paths.get(savedFilePath)));
        String fileContent = new String(Files.readAllBytes(Paths.get(savedFilePath)));
        assertEquals(0, localFlashcardsManager.getNumberOfFlashcards());
        assertEquals(expectedFileContent, fileContent);

    }

    @Test
    public void shouldGenerateListOfFilesWithFlashcards() throws IOException, FailedToLoadFilesListException {
        //given
        String outputFolder = temporaryFolder.newFolder("generateListOfFilesTest").getAbsolutePath() + "\\";
        localFlashcardsManager.setOutputFolder(outputFolder);
        String firstFileWithValidExtension = "firstFile.json";
        String secondFileWithValidExtension = "secondFile.json";
        String fileWithInvalidExtension = "thirdFile.txt";
        temporaryFolder.newFile("generateListOfFilesTest\\" + firstFileWithValidExtension);
        temporaryFolder.newFile("generateListOfFilesTest\\" + secondFileWithValidExtension);
        temporaryFolder.newFile("generateListOfFilesTest\\" + fileWithInvalidExtension);

        //when
        List<String> listOfFiles = localFlashcardsManager.getFilesList();

        //then
        assertEquals(2, listOfFiles.size());
        assertThat(listOfFiles, hasItems(secondFileWithValidExtension, firstFileWithValidExtension));

    }

    @Test(expected = FailedToLoadFilesListException.class)
    public void shouldThrowFailedToLoadFilesListExceptionWhenOutputFolderIsWrong() throws FailedToLoadFilesListException {
        //given
        String wrongOutputFolder = "c\\d\\xx\\yy";
        localFlashcardsManager.setOutputFolder(wrongOutputFolder);

        //when
        localFlashcardsManager.getFilesList();
    }

    @Test(expected = FailedToLoadFlashcardsException.class)
    public void shouldThrowFailedToLoadFlashcardsExceptionWhenOutputFolderIsWrong() throws FailedToLoadFlashcardsException {
        //given
        String wrongOutputFolder = "c\\d\\xx\\yy";
        String fileName = "fileName";
        localFlashcardsManager.setOutputFolder(wrongOutputFolder);

        //when
        localFlashcardsManager.loadFlashcards(fileName);

    }

    @Test
    public void shouldLoadFlashcardsFromFile() throws IOException, FailedToLoadFlashcardsException {
        //given
        String outputFolder = temporaryFolder.newFolder("loadFlashcardsFromFileTest").getAbsolutePath() +"\\";
        String fileName = "fileWithFlashcards.json";
        localFlashcardsManager.setOutputFolder(outputFolder);
        Flashcard firstFlashcard = new Flashcard("word1", "translation1");
        Flashcard secondFlashcard = new Flashcard("word2", "translation2");
        createFileWithFlashcards(outputFolder, fileName, firstFlashcard, secondFlashcard);

        //when
        List<Flashcard> flashcardsList = localFlashcardsManager.loadFlashcards(fileName);

        //then
        assertEquals(2, flashcardsList.size());
        assertThat(flashcardsList, hasItems(firstFlashcard, secondFlashcard));
    }

    private void createFileWithFlashcards(String outputFolder, String fileName, Flashcard firstFlashcard, Flashcard secondFlashcard) throws IOException {
        String fileContent = String.format("[{\"word\":\"%s\",\"translation\":\"%s\"}, {\"word\":\"%s\",\"translation\":\"%s\"}]",
                firstFlashcard.getWord(), firstFlashcard.getTranslation(), secondFlashcard.getWord(), secondFlashcard.getTranslation());
        Files.write(Paths.get(outputFolder + fileName), fileContent.getBytes());
    }

    @Test(expected = EmptyFlashcardsListException.class)
    public void shouldThrowEmptyFlashcardsListExceptionWhenTryToGetRandomFlashcardFromEmptyList() throws EmptyFlashcardsListException {
        //when
        localFlashcardsManager.getRandomFlashcard();
    }

    @Test
    public void shouldReturnRandomFlashcardAndRemoveItFromList() throws InvalidFlashcardException, EmptyFlashcardsListException {
        //given
        Flashcard expectedFlashcard = new Flashcard("word", "translation");
        localFlashcardsManager.addFlashcard(expectedFlashcard);

        //when
        Flashcard randomFlashcard = localFlashcardsManager.getRandomFlashcard();

        //then
        assertEquals(expectedFlashcard, randomFlashcard);
        assertEquals(0, localFlashcardsManager.getNumberOfFlashcards());
    }
}
