package GRADS_Backend;

import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class Student extends User {

    private String studentId;
    private String major;
    private String startingSemester;
    private transient String adminName = "GRADUATE_PROGRAM_COORDINATOR";
    private transient String denialMessage = "Insufficient user privilege.";
    private StudentRecord record;


    public Student() {
    }

    /**
     * default constructor of a student
     *
     * @param studentId the id of a student
     */
    public Student(String studentId, String networkId, String firstName, String lastName) {
        super();
        this.studentId = studentId;
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setNetworkID(networkId);
        this.setRole("STUDENT");
    }
    public Student(String studentId, String networkId, String firstName, String lastName, String major) {
        super();
        this.studentId = studentId;
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setNetworkID(networkId);
        this.major = major;
        this.setRole("STUDENT");
    }

    /**
     * constructor of a student
     *
     * @param studentId the id of a student
     */
    public Student(String studentId, String networkId, String firstName, String lastName, StudentRecord record, String major, String startingSemester) {
        super();
        this.studentId = studentId;
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setNetworkID(networkId);
        this.setRecord(record);
        this.setMajor(major);
        this.setStartingSemester(startingSemester);
        this.setRole("STUDENT");
    }

    /**
     * gets the student id
     *
     * @return the id of the student
     */
    @SerializedName("studentId")
    public String getStudentID() {
        return studentId;
    }

    /**
     * changes the to the given value
     * @param studentId
     */
    @SerializedName("studentId")
    public void setStudentID(String studentId) {
        if (role != "STUDENT")
        this.studentId = studentId;
    }

    /**
     * gets the network id
     * @return
     */
    @SerializedName("networkId")
    public String getNetworkID() {
        return super.getNetworkID();
    }

    /**
     * changes the network id to the given value
     * @param value
     */
    @SerializedName("networkId")
    public void setNetworkID(String value) {
        if (role != "STUDENT")
        super.setNetworkID(value);
    }

    /**
     * gets the first name
     * @return
     */
    @SerializedName("firstName")
    public String getFirstName() {
        return super.getFirstName();
    }

    /**
     * changes the first name to the given value
     * @param value
     */
    @SerializedName("firstName")
    public void setFirstName(String value) {
        if (role != "STUDENT")
        super.setFirstName(value);
    }

    /**
     * gets the last name
     * @return
     */
    @SerializedName("lastName")
    public String getLastName() {
        return super.getLastName();
    }

    /**
     * changes the last name to the given value
     * @param value
     */
    @SerializedName("lastName")
    public void setLastName(String value) {

        if (role != "STUDENT")
        super.setLastName(value);
    }

    /**
     * gets the major
     * @return
     */
    public String getMajor() {
        return major;
    }

    /**
     * Gets the starting semester of the student
     *
     * @return the starting semester of the student
     */
    public String getStartingSemester() {
        return startingSemester;
    }

    /**
     * Gets the student's academic record
     *
     * @return StudentRecord object
     */
    public StudentRecord getRecord() {
        return record;
    }

    /**
     * Gets the Student ID of the student
     *
     * @return Student ID as String
     */
    public String getStudentId() {
        return studentId;
    }


    /**
     * sets the student id to a new id if done by qualified user role
     *
     * @param ID the new id of a user
     */
    public void setID(String ID) {
        if (super.getRole().equals(adminName)) {
            studentId = ID;
        } else {
            System.out.println(denialMessage);
        }
    }

    /**
     * sets the major of a student to a new major if done by qualified user role
     *
     * @param newMajor the new major to be assigned to a user
     */
    public void setMajor(String newMajor) {
        if (super.getRole().equals(adminName)) {
            major = newMajor;
        } else {
            System.out.println(denialMessage);
        }
    }

    /**
     * sets the starting semester of a student to a given value if done by a qualified user
     *
     * @param newStart the new starting semester of a user
     */
    public void setStartingSemester(String newStart) {
       // if (super.getRole().equals(adminName)) {
            startingSemester = newStart;
       // } else {
        //    System.out.println(denialMessage);
      //  }
    }

    /**
     * changes the record to the given value
     * @param transcript
     */
    public void setRecord(StudentRecord transcript) {
        this.record = transcript;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", major='" + major + '\'' +
                ", startingSemester='" + startingSemester + '\'' +
                ", adminName='" + adminName + '\'' +
                ", denialMessage='" + denialMessage + '\'' +
                ", record=" + record +
                '}';
    }
}
