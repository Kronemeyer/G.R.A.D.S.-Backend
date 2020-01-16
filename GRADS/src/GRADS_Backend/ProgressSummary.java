package GRADS_Backend;

import java.util.ArrayList;

public class ProgressSummary extends StudentRecord {

    // all other attributes contained in parent class
    private User user;  // need studentID, networkId, firstName, lastName, major
    private double graduationPercentage;
    private double majorGPA;
    private double overallGPA;
    private ArrayList<GradRequirement[]> requirements;


    public ProgressSummary(User user, Advisor advisor, Term termBegan, ArrayList<CourseTaken> coursesTaken, ArrayList<String> notes, double graduationPercentage, double majorGPA, double overallGPA) {
        super(user.getStudentRecord().getMajor() ,user.getStudentRecord().getStudent(), advisor,termBegan,coursesTaken, notes);
        this.graduationPercentage = graduationPercentage;
        this.majorGPA = majorGPA;
        this.overallGPA = overallGPA;
    }

    /**
     * default constructor with parameters
     * @param major major of the student
     * @param user the student
     * @param advisor advisor of the student
     * @param termBegan term that the student began
     * @param coursesTaken list of courses that the student has taken
     * @param notes list of notes associated with the student
     */
    public ProgressSummary(String major, Student user, Advisor advisor, Term termBegan, ArrayList<CourseTaken> coursesTaken, ArrayList<String> notes) {
        super(major, user, advisor, termBegan, coursesTaken, notes);
    }

    // default contructor
    public ProgressSummary() {

    }

    public User getUser(){
        return this.user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * gets the percentage that the student is toward graduation
     *
     * @return percentage toward graduation
     */
    public double getGraduationPercentage() {
        return graduationPercentage;
    }

    /**
     * sets the graduationPercentage of the student to the given value
     *
     * @param graduationPercentage the new percentage towards graduation that the student is
     */
    public void setGraduationPercentage(double graduationPercentage) {
        this.graduationPercentage = graduationPercentage;
    }

    /**
     * gets the major GPA of the student
     *
     * @return the major GPA of the student
     */
    public double getMajorGPA() {
        return majorGPA;
    }

    /**
     * gets the overall GPA of the student
     *
     * @return the GPA of the student
     */
    public double getOverallGPA() {
        return overallGPA;
    }

    /**
     * sets the overall GPA of the student
     *
     * @param overallGPA the new value to set overallGPA to
     */
    public void setOverallGPA(double overallGPA) {
        this.overallGPA = overallGPA;
    }

    /**
     * sets the major GPA of the student
     *
     * @param majorGPA the new value to set overallGPA to
     */
    public void setMajorGPA(double majorGPA) { this.majorGPA = majorGPA; }

    public ArrayList<GradRequirement[]> getRequirements() {
        return requirements;
    }

    public void setRequirements(ArrayList<GradRequirement[]> requirements) {
        this.requirements = requirements;
    }

    public void initRequirements() {
        if (this.requirements == null) {
            this.requirements = new ArrayList<GradRequirement[]>();
        }
    }

    public void addTakenRequirements(GradRequirement[] requirements) {
        initRequirements();
        this.requirements.add(requirements);
    }

    public void addGraduationPercentage(){
        graduationPercentage += 1;
    }



}
