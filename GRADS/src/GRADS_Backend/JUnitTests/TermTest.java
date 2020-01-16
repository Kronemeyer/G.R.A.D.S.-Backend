package GRADS_Backend.JUnitTests;

import GRADS_Backend.Semester;
import GRADS_Backend.Term;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TermTest {

    //sets up a Term to be used in each test, newly generated for each test
    Term testingTerm = new Term();


    //tests getters and setters
    @Test
    void getSemester() {

        //Semester tempSemester = new Semester();
        testingTerm = new Term(Semester.FALL, 2015);
        assertEquals(Semester.FALL, testingTerm.getSemester());
        //testingTerm
    }

    @Test
    void setSemester() {

        testingTerm.setSemester(Semester.SUMMER);
        assertEquals(Semester.SUMMER, testingTerm.getSemester() );

    }

    @Test
    void getYear() {

        testingTerm = new Term(Semester.FALL, 2015);

        assertEquals(2015, testingTerm.getYear());

    }

    @Test
    void setYear() {

        testingTerm.setYear(2018);
        assertEquals(2018, testingTerm.getYear());
    }

    //tests the toString method
    @Test
    void toString1() {

        testingTerm = new Term(Semester.FALL, 2015);

        assertEquals("Term{" +
                "semester=" + Semester.FALL +
                ", year=" + 2015 +
                '}', testingTerm.toString());
    }


}