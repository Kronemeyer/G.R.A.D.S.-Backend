package GRADS_Backend.JUnitTests;

import GRADS_Backend.Advisor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdvisorTest {

    Advisor testAdvisor;

    //sets up a new advisor for each case
    @BeforeEach
    public void setUp(){
        testAdvisor = new Advisor("test_program", "John", "Doe");
    }

    //tests getters and setters

    @Test
    void getProgram() {

        assertEquals("test_program", testAdvisor.getProgram());

    }

    @Test
    void setProgram() {

        testAdvisor.setProgram("math");
        assertEquals("math", testAdvisor.getProgram());

    }

    @Test
    void getFirstName() {

        assertEquals("John", testAdvisor.getFirstName());

    }

    @Test
    void setFirstName() {

        testAdvisor.setFirstName("Dan");
        assertEquals("Dan", testAdvisor.getFirstName());

    }

    @Test
    void getLastName() {

        assertEquals("Doe", testAdvisor.getLastName());
    }

    @Test
    void setLastName() {

        testAdvisor.setLastName("Dave");
        assertEquals("Dave", testAdvisor.getLastName());

    }

    @Test
    void toString1() {
    }


}