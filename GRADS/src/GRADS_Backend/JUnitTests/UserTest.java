package GRADS_Backend.JUnitTests;

import GRADS_Backend.*;
import GRADS_Backend.CustomErrors.UnsupportedMajorException;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User testUser = new User("jdoe", "John", "Doe", "student", "testProgram");

    @Test
    void generateProgressSummary() {



        SummaryFactory testSummary = new SummaryFactory();
        try {
            ProgressSummary tester =
                    testSummary.generateSummaryReport(testUser);
            assertEquals(tester, testUser.generateProgressSummary(testUser));

        } catch (Exception e) {

        }

    }

    @Test
    void loadBackend() {


        //StudentRecord[] recordList = new Gson().fromJson(json, StudentRecord[].class);


        try{
            HashMap<Integer, Course> tempMap2;

            tempMap2 = testUser.getCourseMap();

            testUser.loadCourses("./res/courses.json");

            //StudentRecord[] recordList = new Gson().fromJson(json, StudentRecord[].class);
            HashMap<Integer, User> tempMap1;

            tempMap1 = testUser.getUserMap();

            testUser.loadUsers("./res/users.json");

            HashMap<Integer, StudentRecord> tempRecordMap;

            tempRecordMap = testUser.getRecordsList();

            testUser.loadRecords("./res/students.json");

            //StudentRecord[] recordList = new Gson().fromJson(json, StudentRecord[].class);

            assertNotEquals(testUser.getRecordsList(), tempRecordMap);
            assertNotEquals(testUser.getCourseMap(), tempMap2);
            assertNotEquals(testUser.getUserMap(), tempMap1);


        }
        catch (Exception e){

        }



    }

    @Test
    void loadUsers() {
//TODO
        try{
            HashMap<Integer, User> tempMap;

            tempMap = testUser.getUserMap();

            testUser.loadUsers("./res/users.json");

            //StudentRecord[] recordList = new Gson().fromJson(json, StudentRecord[].class);

            assertNotEquals(testUser.getUserMap(), tempMap);

        }
        catch (Exception e){

        }
    }

    @Test
    void loadCourses() {
        //TODO reads from file
        try{
            HashMap<Integer, Course> tempMap;

            tempMap = testUser.getCourseMap();

            testUser.loadCourses("./res/courses.json");

            //StudentRecord[] recordList = new Gson().fromJson(json, StudentRecord[].class);

            assertNotEquals(testUser.getCourseMap(), tempMap);

        }
        catch (Exception e){

        }

    }

    @Test
    void loadRecords() {
        //TODO reads from file

        try{
             HashMap<Integer, StudentRecord> tempRecordMap;

             tempRecordMap = testUser.getRecordsList();

            testUser.loadRecords("./res/students.json");

            //StudentRecord[] recordList = new Gson().fromJson(json, StudentRecord[].class);

            assertNotEquals(testUser.getRecordsList(), tempRecordMap);

        }
        catch (Exception e){

        }
    }

    @Test
    void setUser() {

        try{


        }
        catch (Exception e){

        }
    }

    @Test
    void getStudentRecord() {

        //TODO fix
        HashMap<Integer, StudentRecord> tempMap = new HashMap<Integer, StudentRecord>();

        tempMap.put(testUser.getStudentRecord().hashCode(), testUser.getStudentRecord());

        StudentRecord tempRecord = tempMap.get(testUser.getNetworkID().hashCode());

        assertEquals(tempRecord, testUser.getStudentRecord());

    }

    @Test
    void clearSession() {

        try {
            User tempUser = new User("jdoe", "John", "Doe", "student", "testProgram");;

            //tempUser = testUser;

            //System.out.println(tempUser.getFirstName());
            tempUser.clearSession();
            //System.out.println(tempUser.getFirstName());

            assertTrue(tempUser.getNetworkID() == null);
            assertTrue(tempUser.getFirstName() == null);
            assertTrue(tempUser.getLastName() == null);
            assertTrue(tempUser.getRole() == null);
            assertTrue(tempUser.getProgram() == null);
            //assertTrue(tempUser.getNetworkID().equals(testUser.getNetworkID()));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    void getUser() {

        assertEquals("jdoe, John Doe", testUser.getUser());

    }

    @Test
    void getStudentIDs() {
    }

    @Test
    void addNote() {
//TODO fix
        try {
            HashMap<Integer, StudentRecord> tempMap = new HashMap<Integer, StudentRecord>();
            StudentRecord tempRecord = new StudentRecord();

            tempMap.put("jdoe".hashCode(), tempRecord);

            ArrayList<String> temp = tempMap.get("jdoe".hashCode()).getNotes();


            temp.add("nice");

            tempMap.get("jdoe").setNotes(temp);
            testUser.addNote("jdoe", "nice");

            assertEquals(testUser.getStudentRecord().getNotes(), tempMap.get("jdoe").getNotes());
        }
        catch (Exception e)
        {
            //e.printStackTrace();
        }
    }

    @Test
    void getTranscript() {

        try {

            HashMap<Integer, StudentRecord> tempMap = new HashMap<Integer, StudentRecord>();


            StudentRecord tempRecord = tempMap.get("jdoe".hashCode());

            //tempMap.put(3, tempRecord);


            assertEquals(testUser.getTranscript("jdoe"), tempRecord);

            //assertEquals(tempRecord, );


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void updateTranscript() {



        try{
            StudentRecord tempRecord = new StudentRecord();

            StudentRecord oldRecord = new StudentRecord();

            oldRecord = testUser.getStudentRecord();

            testUser.updateTranscript("someUserId", tempRecord);

            assertFalse(testUser.getRecordsList().equals(oldRecord));

            //assertEquals();


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Test
    void generateProgressSummary1() {

        try {
            testUser.setRole("STUDENT");
            testUser.generateProgressSummary(testUser);

            SummaryFactory summaryFactory = new SummaryFactory();
            ProgressSummary progressSummary = summaryFactory.generateSummaryReport(testUser);

            assertEquals(progressSummary, testUser.generateProgressSummary(testUser));


        }
        catch (Exception e)
        {

        }


    }

    @Test
    void simulateCourses() {
        //lots of user inputs



    }

    @Test
    void setNetworkID() {
        testUser.setNetworkID("testNetworkID");
        assertEquals("testNetworkID", testUser.getNetworkID());
    }

    @Test
    void getNetworkID() {
        testUser.setNetworkID("testNetworkID");
        assertEquals("testNetworkID", testUser.getNetworkID());
    }

    @Test
    void setFirstName() {
        testUser.setFirstName("testFirstName");
        assertEquals("testFirstName", testUser.getFirstName());
    }

    @Test
    void getFirstName() {
        testUser.setFirstName("testFirstName");
        assertEquals("testFirstName", testUser.getFirstName());
    }

    @Test
    void setLastName() {
        testUser.setLastName("testLastName");
        assertEquals("testLastName", testUser.getLastName());
    }

    @Test
    void getLastName() {
        testUser.setLastName("testLastName");
        assertEquals("testLastName", testUser.getLastName());
    }

    @Test
    void setRole() {
        testUser.setRole("testRole");
        assertEquals("testRole", testUser.getRole());
    }

    @Test
    void getRole() {
        testUser.setRole("testRole");
        assertEquals("testRole", testUser.getRole());
    }

    @Test
    void setProgram() {
        testUser.setProgram("testProgram");
        assertEquals("testProgram", testUser.getProgram());
    }

    @Test
    void getProgram() {
        testUser.setProgram("testProgram");
        assertEquals("testProgram", testUser.getProgram());
    }

    @Test
    void setNetworkPassword() {
        testUser.setNetworkPassword("newPassword");
        assertEquals("newPassword", testUser.getNetworkPassword());
    }

    @Test
    void getNetworkPassword() {

        testUser.setNetworkPassword("newPassword");
        assertEquals("newPassword", testUser.getNetworkPassword());
    }

    @Test
    void getUserMap() {

        HashMap<Integer, User> testMap = new HashMap<Integer, User>();
        testMap.put(2014, new User("foo"));
        testUser.setUsersMap(testMap);
        assertEquals(testMap, testUser.getUserMap());
    }

    @Test
    void setUsersMap() {
        HashMap<Integer, User> testMap = new HashMap<Integer, User>();
        testMap.put(2014, new User("foo"));
        testUser.setUsersMap(testMap);
        assertEquals(testMap, testUser.getUserMap());
    }

    @Test
    void getCourseMap() {

        HashMap<Integer, Course> testMap = new HashMap<Integer, Course>();
        testMap.put(2014, new Course("Stat", "Math224", "3"));
        testUser.setCourseList(testMap);
        assertEquals(testMap, testUser.getCourseMap());
    }

    @Test
    void setCourseList() {
        HashMap<Integer, Course> testMap = new HashMap<Integer, Course>();
        testMap.put(2014, new Course("Stat", "Math224", "3"));
        testUser.setCourseList(testMap);
        assertEquals(testMap, testUser.getCourseMap());

    }

    @Test
    void getRecordsList() {

        HashMap<Integer, StudentRecord> testMap = new HashMap<Integer, StudentRecord>();
        testMap.put(2014, new StudentRecord());
        testUser.setRecordsMap(testMap);
        assertEquals(testMap, testUser.getRecordsList());
    }

    @Test
    void setRecordsMap() {

        HashMap<Integer, StudentRecord> testMap = new HashMap<Integer, StudentRecord>();
        testMap.put(2014, new StudentRecord());
        testUser.setRecordsMap(testMap);
        assertEquals(testMap, testUser.getRecordsList());
    }
}