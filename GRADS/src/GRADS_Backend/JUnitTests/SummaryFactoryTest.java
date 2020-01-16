package GRADS_Backend.JUnitTests;

import GRADS_Backend.CourseTaken;
import GRADS_Backend.GradRequirement;
import GRADS_Backend.ProgressSummary;
import GRADS_Backend.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SummaryFactoryTest {

    private User user;
    private String userId = "cis_perfect_student";
    //sets up variables for each test case
    @BeforeEach
    public void setUp() {
        user = new User();
        User.loadBackend(user);
        try {
            user.setUser(userId);
        } catch (Exception e) {
            System.out.println("There was an error while setting the backend user!");
            e.printStackTrace();
            return;
        }
    }

    @Test
    void generateSummaryReport() { // testing progress report generation
        try {
            ProgressSummary summary = User.generateProgressSummary(user);
            assertEquals(summary.getOverallGPA(), 4.0);
            assertEquals(summary.getMajorGPA(), 4.0);
            assertEquals(summary.getGraduationPercentage(), 50.0);
            ArrayList<GradRequirement[]> requirements = summary.getRequirements();
            for (GradRequirement requirement : requirements.get(0)) {
                assertFalse(requirement.isPassed());
            }
            for (GradRequirement requirement : requirements.get(1)) {
                assertEquals(true, requirement.isPassed());
            }
        } catch (Exception e) {
            System.out.println("There was an exception while generating the progress summary: " + e.toString());
            e.printStackTrace();
        }
    }

    @Test
    void generateSummaryReport1() { // testing prospective progress report generation
        try {
            ArrayList<CourseTaken> prospectiveCourses = loadProspectiveCourses("./res/prospective_classes/CIS_PROSPECTIVE_CLASSES.json");
            ProgressSummary summary = User.generateProgressSummary(user, prospectiveCourses);
            assertEquals(summary.getOverallGPA(), 4.0);
            assertEquals(summary.getMajorGPA(), 4.0);
            assertEquals(summary.getGraduationPercentage(), 66.66666666666666);
            ArrayList<GradRequirement[]> requirements = summary.getRequirements();
            boolean isFirst = true;
            for (GradRequirement requirement : requirements.get(0)) {
                if (isFirst) {
                    assertEquals(true, requirement.isPassed());
                    isFirst = false;
                } else {
                    assertFalse(requirement.isPassed());
                }

            }
            for (GradRequirement requirement : requirements.get(1)) {
                assertEquals(true, requirement.isPassed());
            }
        } catch (Exception e) {
            System.out.println("There was an exception while generating the progress summary: " + e.toString());
            e.printStackTrace();
        }
    }

    @Test
    void checkCISRequirements() {
        user = new User();
        User.loadBackend(user);
        try {
            user.setUser("cis_perfect_student");
        } catch (Exception e) {
            System.out.println("There was an error while setting the backend user!");
            e.printStackTrace();
            return;
        }
        try {
            ProgressSummary summary = User.generateProgressSummary(user);
            ArrayList<GradRequirement[]> CISRequirements = summary.getRequirements();
            // checking to make sure all of the CIS requirements have been set as satisfied in the progress summary
            GradRequirement[] cisRequirements = CISRequirements.get(1);
            for (GradRequirement requirement : cisRequirements) {
                assertEquals(true, requirement.isPassed());
            }
        } catch (Exception e) {
            System.out.println("There was an exception while generating the progress summary: " + e.toString());
            e.printStackTrace();
        }
    }

    @Test
    void checkGeneralRequirements() {
        // TODO implement test - for now this is a place holder explanation of how to test this method
        /*
            To test this method, a perfect general requirements student could be made with the minimum number of qualifications
            to pass all of the general requirements. This will act as a test to see if the logic for determining if a students classes
            can in fact satisfy the requirement. Additionally, a student with one less class than the required amount (in terms of credit
            or number of credits) could be used to ensure that they do not pass each of the general requirements.
         */
    }


    @Test
    void checkCSRequirements() {
        // TODO implement test - for now this is a place holder explanation of how to test this method
        /*
            To test this method, a perfect CS requirements student could be made with the minimum number of qualifications
            to pass all of the CS requirements. This will act as a test to see if the logic for determining if a students classes
            can in fact satisfy the requirement. Additionally, a student with one less class than the required amount (in terms of credit
            or number of credits) could be used to ensure that they do not pass each of the general requirements.
         */
    }

    @Test
    void checkCERequirements() {
        // TODO implement test - for now this is a place holder explanation of how to test this method
        /*
            To test this method, a perfect CE requirements student could be made with the minimum number of qualifications
            to pass all of the CE requirements. This will act as a test to see if the logic for determining if a students classes
            can in fact satisfy the requirement. Additionally, a student with one less class than the required amount (in terms of credit
            or number of credits) could be used to ensure that they do not pass each of the general requirements.
         */
    }

    @Test
    void loadRequirements() {
        // TODO implement test - for now this is a place holder explanation of how to test this method
        /*
            To test the loading of requirements, requirements can be loaded from a file and compared against a list of requirements
            given to this test case manually to ensure that all requirements are loaded.
         */
    }

    @Test
    void importGradRules() {
        // TODO implement test - for now this is a place holder explanation of how to test this method
        /*
            To test the loading of graduation requirements, requirements can be loaded from a file or files in order to compare them against
            a list of requirements given to this test case manually to ensure that all grad rules are imported (CS, CIS, CE, General).
         */
    }

    @Test
    void requirementCheckResults() {
        // TODO implement test - for now this is a place holder explanation of how to test this method

    }

    @Test
    void calculateOverallGPA() {
        // TODO implement test - for now this is a place holder explanation of how to test this method
        /*
            To test this method, a student with a known GPA (calculated by hand) can be loaded and have their GPA calculated by the backend
            system. The backend calculated GPA can then be compared with the hand calculated, verified correct value in order to determine
            if it is functioning correctly.
         */
    }

    @Test
    void calculateMajorGPA() {
        // TODO implement test - for now this is a place holder explanation of how to test this method
        /*
            To test this method, a student with a known Major GPA (calculated by hand) can be loaded and have their Major GPA calculated by the
            backend system. The backend calculated Major GPA can then be compared with the hand calculated, verified correct value in order to
            determine if it is functioning correctly.
         */
    }

    // reads classes from a file to create a prospective progress report
    public static ArrayList<CourseTaken> loadProspectiveCourses(String filename){
        ArrayList<CourseTaken> prospectiveCourses = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String json = "";
            while (reader.ready()) {
                json += reader.readLine();
            }
            reader.close();
            CourseTaken[] prospectiveCourseList = new Gson().fromJson(json, CourseTaken[].class); // couldnt figure out how to gson
            for (CourseTaken course : prospectiveCourseList) {
                prospectiveCourses.add(course);
            }
            return prospectiveCourses;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}