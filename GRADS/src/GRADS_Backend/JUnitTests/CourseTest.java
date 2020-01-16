package GRADS_Backend.JUnitTests;

import GRADS_Backend.Advisor;
import GRADS_Backend.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {


    //code to run before each test
    private Course testCourse;
    @BeforeEach
    private void setUp(){
        testCourse = new Course("Introduction to Software Engineering", "CSCE247", "3");
    }

    //getters and setters
    @Test
    void getName() {
        assertEquals("Introduction to Software Engineering", testCourse.getName());
    }

    @Test
    void setName() {
        testCourse.setName("Advanced Programming");
        assertEquals("Advanced Programming", testCourse.getName());
    }

    @Test
    void getID() {
        assertEquals("CSCE247", testCourse.getID());
    }

    @Test
    void setID() {
        testCourse.setID("CSCE240");
        assertEquals("CSCE240", testCourse.getID());
    }

    @Test
    void getNumCredits() {
        assertEquals("3", testCourse.getNumCredits());
    }

    @Test
    void setNumCredits() {
        testCourse.setNumCredits("4");
        assertEquals("4",testCourse.getNumCredits());

    }


    @Test
    void setNumCreditsEarned() {
        testCourse.setNumCreditsEarned("3");
        assertEquals("3", testCourse.getNumCreditsEarned());
    }

    @Test
    void getNumCreditsEarned() {
        testCourse.setNumCreditsEarned("3");
        assertEquals("3",testCourse.getNumCreditsEarned());
    }

}