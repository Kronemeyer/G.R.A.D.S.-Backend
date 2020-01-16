package GRADS_Backend.JUnitTests;

import GRADS_Backend.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NotesListTest {
    ArrayList<String> testNotesList;
    StudentRecord testEmptyRecord;

    //code to run before each test
    @BeforeEach
    public void setUp(){
        testNotesList = new ArrayList();
        testNotesList.add("15 Bucks, little man");
        testNotesList.add("Put that cash in my hand");
        testNotesList.add("Made a grade of " + Grade.valueOf("B"));
        testNotesList.add("Made that grade during " + Semester.valueOf("FALL") + " 2019");
    }

    //getters and setters
    @Test
    void getNotes() {
        String expected1 = "15 Bucks, little man";
        String expected2 = "Put that cash in my hand";
        assertEquals(expected1, testNotesList.get(0));
        assertEquals(expected2, testNotesList.get(1));
    }

    @Test
    void setNotes() {
        testEmptyRecord = new StudentRecord();
        testEmptyRecord.setNotes(testNotesList);
        assertEquals("15 Bucks, little man",testEmptyRecord.getNotes().get(0));
    }
}