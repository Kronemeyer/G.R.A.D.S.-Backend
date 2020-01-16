package GRADS_Backend.JUnitTests;

import GRADS_Backend.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProgressSummaryTest {
    private ProgressSummary testSummary;
    private Student testStudent;
    private User adminUser;
    private StudentRecord testRecord;
    private ArrayList<GradRequirement[]> requirementsList;
    GradRequirement [] testRequirementList = new GradRequirement[3];


    @BeforeEach
    private void setUp(){
        //Used only to set up the student record
        adminUser = new User();

        testRecord = new StudentRecord();

        Advisor testAdvisor = new Advisor("COMPUTER_SCIENCE","Aaron","Jackson");
        testRecord.setAdvisor(testAdvisor);

        testRecord.setTermBegan(new Term(Semester.FALL,2019));

        Course course1 = new Course("Test Course 1", "TEST101","3");
        Course course2 = new Course("Test Course 2", "TEST102", "3");
        Course course3 = new Course("Test Course 3", "TEST103","3");

        CourseTaken courseTaken1 = new CourseTaken();
        courseTaken1.setCourse(course1);
        courseTaken1.setGrade(Grade.A);
        courseTaken1.setTerm(new Term(Semester.FALL,2019));

        CourseTaken courseTaken2 = new CourseTaken();
        courseTaken2.setCourse(course2);
        courseTaken2.setGrade(Grade.B);
        courseTaken2.setTerm(new Term(Semester.FALL,2019));

        CourseTaken courseTaken3 = new CourseTaken();
        courseTaken3.setCourse(course3);
        courseTaken3.setGrade(Grade.C);  // this student is just getting worse and worse
        courseTaken3.setTerm(new Term(Semester.SPRING,2019));

        ArrayList<CourseTaken> testCoursesTakenByStudent = new ArrayList<>();
        testCoursesTakenByStudent.add(courseTaken1);
        testCoursesTakenByStudent.add(courseTaken2);
        testRecord.setCoursesTaken(testCoursesTakenByStudent);

        testStudent = new Student("11111","jSmith", "John", "Smith", "COMPUTER_SCIENCE");
        testRecord.setStudent(testStudent);
        testRecord.setMajor(testStudent.getMajor());

        ArrayList<String> notes = new ArrayList<>();
        notes.add("First semester was fine.");
        notes.add("Second semester was not so fine.");
        testRecord.setNotes(notes);

        testSummary = new ProgressSummary();
        testSummary.setUser(testStudent);
        testSummary.setGraduationPercentage(90.00);
        testSummary.setMajorGPA(3.0);
        testSummary.setOverallGPA(2.5);

        ArrayList<Course> requirement1 = new ArrayList<>();
        requirement1.add(course1);
        ArrayList<Course> requirement2 = new ArrayList<>();
        requirement2.add(course2);
        requirement2.add(course3);
        GradRequirement testRequirement1 = new GradRequirement("Test Requirements", requirement1, testCoursesTakenByStudent);
        GradRequirement testRequirement2 = new GradRequirement("Test Requirements 2",requirement2, testCoursesTakenByStudent);
        testRequirementList = new GradRequirement[3];
        testRequirementList[0] = testRequirement1;
        testRequirementList[1] = testRequirement2;
        requirementsList = new ArrayList<>();
        requirementsList.add(testRequirementList);
    }

    @Test
    void getUser() {
        assertEquals("John",testSummary.getUser().getFirstName());
    }

    @Test
    void setUser() {
        Student newTestStudent = new Student("22222","mFarron", "Mark", "Farron", "COMPUTER_SCIENCE");
        testSummary.setUser(newTestStudent);
        assertEquals("mFarron",testSummary.getUser().getNetworkID());
    }

    @Test
    void getGraduationPercentage() {
        assertEquals(90.00,testSummary.getGraduationPercentage());
    }

    @Test
    void setGraduationPercentage() {
        testSummary.setGraduationPercentage(92.50);
        assertEquals(92.50, testSummary.getGraduationPercentage());
    }

    @Test
    void getMajorGPA() {
        assertEquals(3.0,testSummary.getMajorGPA());
    }

    @Test
    void getOverallGPA() {
        assertEquals(2.5,testSummary.getOverallGPA());
    }

    @Test
    void setOverallGPA() {
        testSummary.setOverallGPA(2.0);
       assertEquals(2.0,testSummary.getOverallGPA());

    }

    @Test
    void setMajorGPA() {
        testSummary.setMajorGPA(2.0);
        assertEquals(2.0,testSummary.getMajorGPA());
    }

    @Test
    void getRequirements() {
        assertNotNull(this.requirementsList);
    }

    @Test
    void setRequirements() {
        Course course = new Course("Test Course 4", "TEST104","3");
        ArrayList<Course> requirement = new ArrayList<>();
        requirement.add(course);
        GradRequirement testRequirement = new GradRequirement("Test Requirements", requirement);
    }

    @Test
    void initRequirements() {
        testSummary.setRequirements(null);

        testSummary.initRequirements();

        assertFalse(testSummary.getRequirements() == null);


    }

    @Test
    void addTakenRequirements() {


        ArrayList<GradRequirement[]> listBefore = testSummary.getRequirements();
        //testSummary.addTakenRequirements();

        testSummary.addTakenRequirements(testRequirementList);
        assertFalse(listBefore == testSummary.getRequirements());
        //assertNotEquals(listBefore, testSummary.getRequirements());

    }

    @Test
    void addGraduationPercentage() {

        double gradBefore = testSummary.getGraduationPercentage();
        testSummary.addGraduationPercentage();
        assertEquals(gradBefore + 1, testSummary.getGraduationPercentage());

    }
}