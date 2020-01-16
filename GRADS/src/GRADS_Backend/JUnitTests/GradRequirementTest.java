package GRADS_Backend.JUnitTests;

import GRADS_Backend.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GradRequirementTest {

    private GradRequirement testRequirment;
    private ArrayList<CourseTaken> testCoursesTaken;
    private ArrayList<Course> testCourseList;

    //code that runs before each test,
    @BeforeEach
    private void setUp(){
        testCoursesTaken = new ArrayList<>();
        testCourseList = new ArrayList<>();

        CourseTaken testCourseTaken1 = new CourseTaken();
        Course testCourse1 = new Course("Introduction to Software Engineering","CSCE247", "3");
        Term testTerm1 = new Term();
        testTerm1.setSemester(Semester.FALL);
        testTerm1.setYear(2019);
        testCourseTaken1.setCourse(testCourse1);
        testCourseTaken1.setTerm(testTerm1);
        testCourseTaken1.setGrade(Grade.A);
        testCoursesTaken.add(testCourseTaken1);

        CourseTaken testCourseTaken2 = new CourseTaken();
        Course testCourse2 = new Course("Advanced Programming","CSCE240", "3");
        Term testTerm2 = new Term();
        testTerm2.setSemester(Semester.SPRING);
        testTerm2.setYear(2018);
        testCourseTaken2.setCourse(testCourse2);
        testCourseTaken2.setTerm(testTerm2);
        testCourseTaken2.setGrade(Grade.B);
        testCoursesTaken.add(testCourseTaken2);

        //testStudent.getStudentRecord().getCoursesTaken().add(testCoursesTaken);
        testCourseList.add(testCourse2);

    }

    @BeforeEach
    private void testRequirementsSetUp(){
        testRequirment = new GradRequirement("Test Requirements",testCourseList);
    }

    //getters and setters
    @Test
    void getName() {
        assertEquals("Test Requirements", testRequirment.getName());
    }

    @Test
    void setName() {
        testRequirment.setName("New Test Requirements");
        assertEquals("New Test Requirements", testRequirment.getName());
    }

    @Test
    void getRequiredClassesSize() {
        assertEquals(2,testCourseList.size()+1);
    }
    @Test
    void getRequiredClassesNamesMatch() {
        assertEquals("Advanced Programming", testCourseList.get(0).getName());
    }

    @Test
    void setRequiredClasses() {
        Course testCourse = new Course("Test Class 1","TEST101", "3");
        testCourseList.add(testCourse);
        Course testCourse1 = new Course("Test Class 2","TEST102", "3");
        testCourseList.add(testCourse1);

        testRequirment.setRequiredClasses(testCourseList);
        assertTrue(testRequirment.contains(testCourse));
        assertTrue(testRequirment.contains(testCourse1));
    }

    @Test
    void isPassed() {
        assertFalse(testRequirment.isPassed());
    }

    @Test
    void setPassed() {
        testRequirment.setPassed(true);
        assertTrue(testRequirment.isPassed());
    }

    @Test
    void getTakenClasses() {
        assertNotNull(testCourseList);
        assertEquals("CSCE240",testCourseList.get(0).getID());
    }

    @Test
    void setTakenClasses() {
        ArrayList<CourseTaken> newTestCoursesTaken = new ArrayList<>();

        CourseTaken testCourseTaken1 = new CourseTaken();
        Course testCourse1 = new Course("Underwater Basket Weaving","ARTS101", "3");
        Term testTerm1 = new Term();
        testTerm1.setSemester(Semester.FALL);
        testTerm1.setYear(2017);
        testCourseTaken1.setCourse(testCourse1);
        testCourseTaken1.setTerm(testTerm1);
        testCourseTaken1.setGrade(Grade.F);

        CourseTaken testCourseTaken2 = new CourseTaken();
        Course testCourse2 = new Course("Modern Memes","MEME101", "4");
        Term testTerm2 = new Term();
        testTerm1.setSemester(Semester.SUMMER);
        testTerm1.setYear(2017);
        testCourseTaken2.setCourse(testCourse2);
        testCourseTaken2.setTerm(testTerm2);
        testCourseTaken2.setGrade(Grade.A);

        newTestCoursesTaken.add(testCourseTaken1);
        newTestCoursesTaken.add(testCourseTaken2);

        testRequirment.setTakenClasses(newTestCoursesTaken);

        assertEquals("ARTS101",testRequirment.getTakenClasses().get(0).getCourse().getID());
        assertEquals("MEME101",testRequirment.getTakenClasses().get(1).getCourse().getID());
    }

    @Test
    void addTakenClass() {
        Course testCourse1 = new Course("Introduction to Software Engineering","CSCE247", "3");
        testCourseList.add(testCourse1);
        assertTrue(testCourseList.contains(testCourse1));
    }

    @Test
    void initTakenClass() {  // TODO: REMOVE?
        ArrayList<CourseTaken> newTestCoursesTaken = new ArrayList<>();

        CourseTaken testCourseTaken1 = new CourseTaken();
        Course testCourse1 = new Course("Underwater Basket Weaving","ARTS101", "3");
        Term testTerm1 = new Term();
        testTerm1.setSemester(Semester.FALL);
        testTerm1.setYear(2017);
        testCourseTaken1.setCourse(testCourse1);
        testCourseTaken1.setTerm(testTerm1);
        testCourseTaken1.setGrade(Grade.valueOf("F"));

        CourseTaken testCourseTaken2 = new CourseTaken();
        Course testCourse2 = new Course("Modern Memes","MEME101", "4");
        Term testTerm2 = new Term();
        testTerm2.setSemester(Semester.SUMMER);
        testTerm2.setYear(2017);
        testCourseTaken2.setCourse(testCourse2);
        testCourseTaken2.setTerm(testTerm2);
        testCourseTaken2.setGrade(Grade.B);

        newTestCoursesTaken.add(testCourseTaken1);
        newTestCoursesTaken.add(testCourseTaken2);

        testRequirment.initTakenClass();

    }

    @Test
    void checkGrade(){
        CourseTaken testCourseTaken2 = new CourseTaken();
        Course testCourse2 = new Course("Modern Memes","MEME101", "4");
        Term testTerm2 = new Term();
        testTerm2.setSemester(Semester.SUMMER);
        testTerm2.setYear(2017);
        testCourseTaken2.setCourse(testCourse2);
        testCourseTaken2.setTerm(testTerm2);
        testCourseTaken2.setGrade(Grade.B);
        assertEquals(Grade.B,testCourseTaken2.getGrade());

        }

    @Test
    void contains() {
        CourseTaken testCourseTaken1 = new CourseTaken();
        Course testCourse1 = new Course("Testing Class","TEST247", "3");
        Term testTerm1 = new Term();
        testTerm1.setSemester(Semester.SPRING);
        testTerm1.setYear(2019);
        testCourseTaken1.setCourse(testCourse1);
        testCourseTaken1.setTerm(testTerm1);
        testCourseTaken1.setGrade(Grade.valueOf("B"));
        testCoursesTaken.add(testCourseTaken1);

        assertEquals("TEST247",testCoursesTaken.get(2).getCourse().getID());  // Course CSCE247 Exists
        assertTrue(testCoursesTaken.contains(testCourseTaken1));
    }
}