package GRADS_Backend.JUnitTests;

import GRADS_Backend.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    private Student testStudent;
    private StudentRecord testRecord;
    private Advisor testAdvisor;
    private Term testTerm;
    private ArrayList<CourseTaken> testCoursesTaken;
    private ArrayList<String> notes;


    //setup before each test
    @BeforeEach
    private void setUp(){
        testAdvisor = new Advisor("COMPUTER_SCIENCE","John","Smith");

        testTerm = new Term();
        testTerm.setSemester(Semester.FALL);
        testTerm.setYear(2019);

        testCoursesTaken = new ArrayList<>();
        Course testCourseTaken = new Course("Test Course", "TEST101", "3");
        CourseTaken testCourseTakenClass = new CourseTaken();
        testCourseTakenClass.setCourse(testCourseTaken);
        testCoursesTaken.add(testCourseTakenClass);

        notes = new ArrayList<>();
        notes.add("Test Note");
        testStudent = new Student("11111", "tStudent", "Test", "Student", "COMPUTER_SCIENCE");

        testRecord= new StudentRecord("COMPUTER_SCIENCE",testStudent,testAdvisor,testTerm,testCoursesTaken,notes);
        testStudent.setRecord(testRecord);
        testStudent.setStartingSemester("FALL");
        testStudent.setMajor("COMPUTER_SCIENCE");
    }

    //tests the general getters and setters

    @Test
    void getStudentID() {
        assertEquals("11111",testStudent.getStudentID());
    }

    @Test
    void setStudentIDWillNotChangeID() {
        testStudent.setStudentID("22222");
        assertEquals("11111", testStudent.getStudentID());

    }

    @Test
    void getNetworkID() {
        assertEquals("tStudent", testStudent.getNetworkID());
    }

    @Test
    void setNetworkID() {
        testStudent.setStudentID("tStudent123456");
    }

    @Test
    void getFirstName() {
        assertEquals("Test", testStudent.getFirstName());

    }

    @Test
    void setFirstName() {
        testStudent.setFirstName("Fail");
        assertEquals("Test", testStudent.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Student",testStudent.getLastName());
    }

    @Test
    void setLastName() {
        testStudent.setLastName("Fail");
        assertEquals("Student", testStudent.getLastName());

    }

    @Test
    void getMajor() {
        assertEquals("COMPUTER_SCIENCE",testStudent.getMajor());
    }

    @Test
    void getStartingSemester() {
        assertEquals("FALL", testStudent.getStartingSemester());
    }

    @Test
    void getRecord() {
        assertEquals("COMPUTER_SCIENCE",testStudent.getRecord().getMajor());
        assertEquals(testCoursesTaken,testStudent.getRecord().getCoursesTaken());
        assertEquals("John", testStudent.getRecord().getAdvisor().getFirstName());
        assertEquals(Semester.FALL, testStudent.getRecord().getTermBegan().getSemester());
        assertEquals("TEST101",testStudent.getRecord().getCoursesTaken().get(0).getCourse().getID());
        assertEquals("Test Note", testStudent.getRecord().getNotes().get(0));
        assertTrue(testStudent.getRecord().getNotes().contains("Test Note"));
    }

    @Test
    void getStudentId() {
        assertEquals("11111", testRecord.getStudent().getStudentID());
    }

    @Test
    void setID() {
        testStudent.setID("22222");
        assertEquals("11111", testStudent.getStudentID());

    }

    @Test
    void setMajor() {
        testStudent.setMajor("COMPUTER_ENGINEERING");
        assertEquals("COMPUTER_SCIENCE", testStudent.getMajor());

    }

    @Test
    void setStartingSemester() {
        testStudent.setStartingSemester("SUMMER");
        assertEquals("SUMMER", testStudent.getStartingSemester());
    }

    @Test
    void setRecord() {
        Advisor testAdvisor1 = new Advisor("COMPUTER_SCIENCE","Aaron","Jacobs");

        Term testTerm1 = new Term();
        testTerm1.setSemester(Semester.SPRING);
        testTerm1.setYear(2018);

        ArrayList<CourseTaken> testCoursesTaken1 = new ArrayList<>();
        Course testCourseTaken1 = new Course("New Course", "TEST999", "4");
        CourseTaken testCourseTakenClass1 = new CourseTaken();
        testCourseTakenClass1.setCourse(testCourseTaken1);
        testCoursesTaken1.add(testCourseTakenClass1);

        ArrayList<String> notes1 = new ArrayList<>();
        notes1.add("Test Note");

        StudentRecord testRecord1 = new StudentRecord("COMPUTER_SCIENCE",testStudent,testAdvisor1,testTerm1,testCoursesTaken1,notes1);
        testStudent.setRecord(testRecord1);

        assertEquals("Aaron", testStudent.getRecord().getAdvisor().getFirstName());
        assertEquals(Semester.SPRING, testStudent.getRecord().getTermBegan().getSemester());
        assertEquals("TEST999",testStudent.getRecord().getCoursesTaken().get(0).getCourse().getID());
        assertTrue(testStudent.getRecord().getCoursesTaken().contains(testCourseTakenClass1));

    }

}