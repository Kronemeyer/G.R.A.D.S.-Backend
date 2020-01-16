package GRADS_Backend;

import GRADS_Backend.CustomErrors.*;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class User implements GRADSIntf {

    public String networkId;
    public String networkPassword;
    public String firstName;
    public String lastName;
    public String program;
    public String role;

    public HashMap<Integer, User> userMap;
    public HashMap<Integer, Course> courseMap;
    public HashMap<Integer, StudentRecord> recordMap;

    /**
     *
     */
    public User() {
    }

    /**
     * @param iD
     */
    public User(String iD) {
    }

    /**
     * default constructor for User
     * @param networkID
     * @param firstName
     * @param lastName
     * @param role
     * @param program
     */
    public User(String networkID, String firstName, String lastName, String role, String program) {
        this.networkId = networkID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.program = program;
    }


    /**
     * creates a progress summary based on the provided student's associated record
     * @param user
     */
    public static ProgressSummary generateProgressSummary(User user) throws UnsupportedMajorException {
        try {
            if (user.getRole().equalsIgnoreCase("STUDENT")){
                SummaryFactory summaryFactory = new SummaryFactory();
                ProgressSummary progressSummary = summaryFactory.generateSummaryReport(user);
                return progressSummary;
            } else {
                System.out.println("You are an advisor, please pass a student user ID to generate a progress summary!");
                throw new UnsupportedRoleException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * creates a progress summary based on the provided student's record and prospective course list
     * @param user
     * @param prospectiveClasses
     */
    public static ProgressSummary generateProgressSummary(User user, ArrayList<CourseTaken> prospectiveClasses) throws UnsupportedMajorException {
        try {
            if (user.getRole().equalsIgnoreCase("STUDENT")){
                SummaryFactory summaryFactory = new SummaryFactory();
                ProgressSummary progressSummary = summaryFactory.generateSummaryReport(user, prospectiveClasses);
                return progressSummary;
            } else {
                System.out.println("You are an advisor, please pass a student user ID to generate a progress summary!");
                throw new UnsupportedRoleException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void loadBackend(User user) {
        try {
            user.loadUsers("./res/users.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            user.loadCourses("./res/courses.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            user.loadRecords("./res/students.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * loads users from a file
     * @param usersFile the filename of the users file.
     * @throws Exception when there is a problem reading the file
     */
    @Override
    public void loadUsers(String usersFile) throws Exception {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(usersFile));
            String json = "";
            while (reader.ready()) {
                json += reader.readLine();
            }
            reader.close();
            User[] usersList = new Gson().fromJson(json, User[].class);
            userMap = new HashMap<>();
            for (User users : usersList) {
                userMap.put(users.getNetworkID().hashCode(), users);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * loads the courses from a file
     * @param coursesFile the filename of the users file.
     * @throws Exception
     */
    @Override
    public void loadCourses(String coursesFile) throws Exception {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(coursesFile));
            String json = "";
            while (reader.ready()) {
                json += reader.readLine();
            }
            reader.close();
            Course[] courseList = new Gson().fromJson(json, Course[].class); // couldnt figure out how to gson
            // straight into a map so this is a bit hacky
            // but works
            courseMap = new HashMap<>();
            for (Course course : courseList) {
                courseMap.put(course.getID().hashCode(), course);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * loads records from a file
     * @param recordsFile the filename of the transcripts file.
     * @throws Exception
     */
    @Override
    public void loadRecords(String recordsFile) throws Exception {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(recordsFile));
            Gson gson = new Gson();
            String json = "";
            while (reader.ready()) {
                json += reader.readLine();
            }
            reader.close();
            StudentRecord[] recordList = new Gson().fromJson(json, StudentRecord[].class); // load student records from file
            recordMap = new HashMap<>();
            for (StudentRecord record : recordList) {
                recordMap.put(record.getStudent().getNetworkID().hashCode(), record);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param userId the id of the user to log in.
     * @throws Exception
     */
    @Override
    public void setUser(String userId) throws NoSuchUserException {  // set user should be the very first method in
        try {
            // main after loading the users and records
            if ((userMap.get(userId.hashCode()).getNetworkID()).equalsIgnoreCase(userId)) {
                System.out.println("Loaded user: "+userMap.get(userId.hashCode()).getNetworkID());
                this.setNetworkID(userMap.get(userId.hashCode()).getNetworkID());
                this.setFirstName(userMap.get(userId.hashCode()).getFirstName());
                this.setLastName(userMap.get(userId.hashCode()).getLastName());
                this.setRole(userMap.get(userId.hashCode()).getRole());
                if (!(this.getRole().equalsIgnoreCase("GRADUATE_PROGRAM_COORDINATOR"))) {  // This prevents students from accessing the complete userMap and
                    // recordMap
                    this.getUserMap().clear();  // erases all Users from the general userMap
                    this.userMap.put(this.getNetworkID().hashCode(), this);  // adds the student user to the cleared userMap
                    HashMap<Integer, StudentRecord> temp = new HashMap<>();
                    temp.put(this.getNetworkID().hashCode(), this.getStudentRecord());  // put the studentRecord into temp
                    this.recordMap.clear();  // erases all StudentRecords from general recordMap
                    this.recordMap = temp;  // sets the recordMap to only hold the students record
                }
                this.setProgram(userMap.get(userId.hashCode()).getProgram());
                System.out.println("User-role: "+this.getRole());
            } else {
                throw new NoSuchUserException();
            }
        } catch (Exception e) {
            throw new NoSuchUserException();
        }
    }

    public StudentRecord getStudentRecord() {
        return recordMap.get(this.getNetworkID().hashCode());
    }

    /**
     * @throws Exception
     */
    @Override
    public void clearSession() throws NoActiveUserException {
        if (this.getUser() == null)
            throw new NoActiveUserException();
        this.setNetworkID(null);
        this.setFirstName(null);
        this.setLastName(null);
        this.setRole(null);
        this.setProgram(null);

    }

    /**
     * @return
     */
    @Override
    public String getUser() {
        return this.getNetworkID() + ", " + this.getFirstName() + " " + this.getLastName();
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public List<String> getStudentIDs() throws Exception {
        List<String> idList = new ArrayList<>();
        recordMap.entrySet().stream().forEach(e -> idList.add(e.getKey(), e.getValue().getStudent().getStudentID()));
        return idList;
    }

    /**
     * @param userId the student ID to add a note to.
     * @param note   the note to be appended to the recordMap
     * @throws Exception
     */
    @Override
    public void addNote(String userId, String note) throws Exception {
        ArrayList<String> temp = recordMap.get(userId.hashCode()).getNotes();
        temp.add(note);
        recordMap.get(userId.hashCode()).setNotes(temp);
    }

    /**
     * @param userId the identifier of the student.
     * @return
     * @throws Exception
     */
    @Override
    public StudentRecord getTranscript(String userId) throws Exception {
        if (recordMap.get(userId.hashCode()).getStudent().equals(null)) {
            throw new RecordNotFoundException();
        }
        StudentRecord record = recordMap.get(userId.hashCode());
        return record;
    }

    /**
     * @param userId     the student ID to overwrite.
     * @param transcript the new student record
     * @throws Exception
     */
    @Override
    public void updateTranscript(String userId, StudentRecord transcript) throws Exception {
        if (recordMap.get(userId.hashCode()).equals(null) ||recordMap.get(userId.hashCode()).getStudent().equals(null)) {
            throw new RecordNotFoundException();
        }
        Student student = recordMap.get(userId.hashCode()).getStudent();
        student.setRecord(transcript);
        recordMap.replace(userId.hashCode(), transcript);
    }

    /**
     * Method for a GPC to generate a progress summary for a student based on their student ID
     * @param userId the student to generate the record for.
     * @return
     * @throws Exception
     */
    @Override
    public ProgressSummary generateProgressSummary(String userId) throws Exception {
        return null;
    }

    /**
     * @param userId  the student to generate the record for.
     * @param courses a list of the prospective courses.
     * @return
     * @throws Exception
     */
    @Override
    public ProgressSummary simulateCourses(String userId, List<CourseTaken> courses) throws Exception {
        if (recordMap.get(userId.hashCode()).getStudent().equals(null)) {
            throw new RecordNotFoundException();
        }
        Scanner scanner = new Scanner(System.in);
        ArrayList<CourseTaken> tempCourseList = new ArrayList<>(); //= recordMap.get(userId.hashCode()).getStudent().getRecord().getCoursesTaken();
        //tempCourseList.addAll(courses);
        ProgressSummary simulatedProgress = null;
        if (this.getRole().equals("GRADUATE_PROGRAM_COORDINATOR")) {
            System.out.println("Please enter the Network ID of the student you wish to Generate a report for: ");
            String sId = scanner.nextLine();
            Student student = (Student) this.getUserMap().get(sId.hashCode());
            simulatedProgress = new ProgressSummary(student.getMajor(), student, student.getRecord().getAdvisor(),
                    student.getRecord().getTermBegan(), student.getRecord().getCoursesTaken(),
                    student.getRecord().getNotes());
        } else if (this.getRole().equals("STUDENT")) {
            //simulatedProgress = new ProgressSummary(this, this.recordMap.,);
        }
        System.out.println("What type of record do you want? Enter: ");
        String choice = scanner.nextLine();
        SummaryFactory reportFactory = new SummaryFactory();
        //reportFactory.generateSummaryReport(simulatedProgress, this, tempCourseList, choice);

        return simulatedProgress;
    }

    /**
     * changes the networkID to the given value
     * @param networkID the new value of networkID
     */
    @SerializedName("networkId")
    public void setNetworkID(String networkID) {
        this.networkId = networkID;
    }

    /**
     * gets the networkID
     * @return the networkID
     */
    @SerializedName("networkId")
    public String getNetworkID() {
        return this.networkId;
    }

    /**
     * changes the firstName to the given value
     * @param firstName the new value of firstName
     */
    @SerializedName("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * gets the firstName of the User
     * @return the firstName
     */
    public String getFirstName() {
        return this.firstName;
    }


    /**
     * changes the lastMap to the given value
     * @param lastName
     */
    @SerializedName("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * gets the lastName of the User
     * @return the lastName of the User
     */
    @SerializedName("lastName")
    public String getLastName() {
        return this.lastName;
    }

    /**
     * changes the role to the given value
     * @param role
     */
    @SerializedName("role")
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * gets the role of the User
     * @return the role
     */
    @SerializedName("role")
    public String getRole() {
        return this.role;
    }

    /**
     * changes the program to the given value
     * @param program
     */
    @SerializedName("program")
    public void setProgram(String program) {
        this.program = program;
    }

    /**
     * gets the program of the User
     * @return the program
     */
    @SerializedName("program")
    public String getProgram() {
        return this.program;
    }

    /**
     * changes the network password to the given value
     * @param networkPassword
     */
    public void setNetworkPassword(String networkPassword) {
        this.networkPassword = networkPassword;
    }

    /**
     * gets the network password of the User
     * @return the networkPassword
     */
    public String getNetworkPassword() {
        return this.networkPassword;
    }

    /**
     * gets the userMap
     * @return the userMap
     */
    public HashMap<Integer, User> getUserMap() {
        return this.userMap;
    }

    /**
     * changes the userMap to the given value
     * @param userMap
     */
    public void setUsersMap(HashMap userMap) {
        this.userMap = userMap;
    }

    /**
     * gets the courseMap
     * @return the courseMap
     */
    public HashMap<Integer, Course> getCourseMap() {
        return this.courseMap;
    }

    /**
     * changes the courseMap to the new value
     * @param courseMap the new value of courseMap
     */
    public void setCourseList(HashMap courseMap) {
        this.courseMap = courseMap;
    }

    /**
     * gets the record map
     * @return the record map
     */
    public HashMap getRecordsList() {
        return recordMap;
    }

    /**
     * changes the recordMap to the given value
     * @param recordMap the new value of record map
     */
    public void setRecordsMap(HashMap recordMap) {
        this.recordMap = recordMap;
    }
}
