package GRADS_Backend;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentRecord {

    private String major;
    private Student student;
    private Advisor advisor;
    private Term termBegan;
    private ArrayList<CourseTaken> coursesTaken;
    private ArrayList<String> notes;


    public StudentRecord() {

    }
    /**
     * @param  major the current major of the student
     * @param student the student who the record should be for
     * @param advisor the advisor of the student
     * @param termBegan the term that the student began at the school
     * @param coursesTaken a list of courses that the student has taken
     * @param notes String list of notes about the student
     */

    public StudentRecord(String major, Student student, Advisor advisor, Term termBegan, ArrayList<CourseTaken> coursesTaken, ArrayList<String> notes) {
        this.major = major;
        this.student = student;
        this.advisor = advisor;
        this.termBegan = termBegan;
        this.coursesTaken = coursesTaken;
        this.notes = notes;

    }

    /**
     * @return the student associated with the record
     */
    @SerializedName("student")
    public Student getStudent() {
        return student;
    }

    /**
     * @param value changes the student to the given value
     */
    @SerializedName("student")
    public void setStudent(Student value) {
        this.student = value;
    }

    /**
     * @return gets the current major of the student
     */
    @SerializedName("major")
    public String getMajor() {
        return major;
    }

    /**
     * @param value changes the major to the given value
     */
    @SerializedName("major")
    public void setMajor(String value) {
        this.major = value;
    }

    /**
     * @return gets the term that the student began
     */
    @SerializedName("termBegan")
    public Term getTermBegan() {
        return termBegan;
    }

    /**
     * @param value changes the term that the student began to the given value
     */
    @SerializedName("termBegan")
    public void setTermBegan(Term value) {
        this.termBegan = value;
    }

    /**
     * @return gets the advisor of the student
     */
    @SerializedName("advisor")
    public Advisor getAdvisor() {
        return advisor;
    }

    /**
     * @param value changes the advisor of the student to the given value
     */
    @SerializedName("advisor")
    public void setAdvisor(Advisor value) {
        this.advisor = value;
    }

    /**
     * @return gets a list of the courses taken by the student
     */
    @SerializedName("coursesTaken")
    public ArrayList<CourseTaken> getCoursesTaken() {
        return coursesTaken;
    }

    /**
     * @param value changes the list of the courses taken to the given list
     */
    @SerializedName("coursesTaken")
    public void setCoursesTaken(ArrayList<CourseTaken> value) {
        this.coursesTaken = value;
    }

    /**
     * @return gets a string list of notes associated with the student
     */
    @SerializedName("notes")
    public ArrayList<String> getNotes() {
        return notes;
    }

    /**
     * @param value changes the list of notes to the given value
     */
    @SerializedName("notes")
    public void setNotes(ArrayList<String> value) {
        this.notes = value;
    }

    /**
     * @param studentID
     */

    public void setGPAs(Student studentID) {
		/*double mCreditsEarned = 0;  // amount of major credits earned
		double mCreditsWorth = 0;  // amount of major credits class is worth
		double oCreditsEarned = 0;  // amount of overall credits earned
		double oCreditsWorth = 0;  // amount of overall credits class is worth
		
		for (int i = 0; i < studentID.record.coursesTaken.list.size(); i++) {
			if (coursesTaken.list.get(i).isMajor()) {
				mCreditsEarned += Double.parseDouble(coursesTaken.list.get(i).getNumCreditsEarned());  // still need to finalize numCreditsEarned
				mCreditsWorth += Double.parseDouble(coursesTaken.list.get(i).getNumCredits());
			} 
			oCreditsEarned += Double.parseDouble(coursesTaken.list.get(i).getNumCreditsEarned());   // still need to finalize numCreditsEarned
			oCreditsWorth += Double.parseDouble(coursesTaken.list.get(i).getNumCredits());
		}
		student.setMajorGPA(mCreditsEarned/mCreditsWorth);
		student.setOverallGPA(oCreditsEarned/oCreditsWorth);
*/
    }

    // CALCULATE OVERALL SCIENCE GPA
    public Double calculateOverallGPA(ArrayList<CourseTaken> courseTaken) {
        Double gpaTotal = 0.0;
        Double runningCreditTotal = 0.0;
        for (CourseTaken course : courseTaken) {
            Double qualityPoints = Double.parseDouble(course.getCourse().getNumCredits());
            Double grade = 0.0;
            if (course.getGrade() != null) {
                Grade classGrade = course.getGrade();
                switch (classGrade) {
                    case A:
                        grade = 4.0 * qualityPoints;
                        break;
                    case B:
                        grade = 3.0 * qualityPoints;
                        break;
                    case C:
                        grade = 2.0 * qualityPoints;
                        break;
                    case D:
                        grade = 1.0 * qualityPoints;
                        break;
                    case F:
                        grade = 0.0 * qualityPoints;
                        break;
                    default:
                        break;
                }
                gpaTotal += grade;
                runningCreditTotal += Double.parseDouble(course.getCourse().getNumCredits());
            } else {
                // no grade for the class so do nothing
            }
        }
        Double overallGPA = gpaTotal / runningCreditTotal;
        return overallGPA;
    }

    public Double calculateCSGPA(ArrayList<CourseTaken> courseTaken) {
        String[] csCourses = {"CSCE145", "CSCE146", "CSCE190", "CSCE211", "CSCE212", "CSCE215",
                "CSCE240", "CSCE247", "CSCE311", "CSCE330", "CSCE350", "CSCE355", "CSCE416", "CSCE490", "CSCE492",
                "CSCE_ELECTIVE", "CSCE317"};
        List csCourseList = Arrays.asList(csCourses);
        Double gpaTotal = 0.0;
        Double runningCreditTotal = 0.0;
        for (CourseTaken course : courseTaken) {
            if (course.getGrade() != null) {
                if (csCourseList.contains(course.getCourse().getID())) {
                    Double qualityPoints = Double.parseDouble(course.getCourse().getNumCredits());
                    Double grade = 0.0;
                    switch (course.getGrade()) {
                        case A:
                            grade = 4.0 * qualityPoints;
                            break;
                        case B:
                            grade = 3.0 * qualityPoints;
                            break;
                        case C:
                            grade = 2.0 * qualityPoints;
                            break;
                        case D:
                            grade = 1.0 * qualityPoints;
                            break;
                        case F:
                            grade = 0.0 * qualityPoints;
                            break;
                        default:
                            break;
                    }
                    gpaTotal += grade;
                    runningCreditTotal += Double.parseDouble(course.getCourse().getNumCredits());
                }
            } else {
                // no grade for the class so do nothing
            }
        }
        Double overallGPA = gpaTotal / runningCreditTotal;
        return overallGPA;
    }

    // CALCULATE COMPUTER ENGINEERING GPA
    public Double calculateCEGPA(ArrayList<CourseTaken> courseTaken) {
        String[] csCourses = {"CSCE145", "CSCE146", "CSCE190", "CSCE211", "CSCE212", "CSCE215", "CSCE240", "CSCE274", "CSCE311",
                "CSCE313", "CSCE317", "CSCE350", "CSCE416", "CSCE490", "CSCE492", "CSCE611", "CSCE330", "CSCE355", "ELCT321",
                "ELCT331", "ELCT101", "ELCT221", "ELCT222", "ELCT371", "CSCE510", "CSCE511", "CSCE512", "CSCE513", "CSCE514", "CSCE515", "CSCE516", "CSCE517", "CSCE518", "CSCE519", "CSCE520", "CSCE521", "CSCE522", "CSCE523", "CSCE524", "CSCE525", "CSCE526", "CSCE527", "CSCE528", "CSCE529", "CSCE530", "CSCE531", "CSCE532", "CSCE533", "CSCE534", "CSCE535", "CSCE536", "CSCE537", "CSCE538", "CSCE539", "CSCE540", "CSCE541", "CSCE542", "CSCE543", "CSCE544", "CSCE545", "CSCE546", "CSCE547", "CSCE548", "CSCE549", "CSCE550", "CSCE551", "CSCE552", "CSCE553", "CSCE554", "CSCE555", "CSCE556", "CSCE557", "CSCE558", "CSCE559", "CSCE560", "CSCE561", "CSCE562", "CSCE563", "CSCE564", "CSCE565", "CSCE566", "CSCE567", "CSCE568", "CSCE569", "CSCE570", "CSCE571", "CSCE572", "CSCE573", "CSCE574", "CSCE575", "CSCE576", "CSCE577", "CSCE578", "CSCE579", "CSCE580", "CSCE581", "CSCE582", "CSCE583", "CSCE584", "CSCE585", "CSCE586", "CSCE587", "CSCE588", "CSCE589", "CSCE590", "CSCE591", "CSCE592", "CSCE593", "CSCE594", "CSCE595", "CSCE596", "CSCE597", "CSCE598", "CSCE599", "CSCE600", "CSCE601", "CSCE602", "CSCE603", "CSCE604", "CSCE605", "CSCE606", "CSCE607", "CSCE608", "CSCE609", "CSCE610", "CSCE611", "CSCE612", "CSCE613", "CSCE614", "CSCE615", "CSCE616", "CSCE617", "CSCE618", "CSCE619", "CSCE620", "CSCE621", "CSCE622", "CSCE623", "CSCE624", "CSCE625", "CSCE626", "CSCE627", "CSCE628", "CSCE629", "CSCE630", "CSCE631", "CSCE632", "CSCE633", "CSCE634", "CSCE635", "CSCE636", "CSCE637", "CSCE638", "CSCE639", "CSCE640", "CSCE641", "CSCE642", "CSCE643", "CSCE644", "CSCE645", "CSCE646", "CSCE647", "CSCE648", "CSCE649", "CSCE650", "CSCE651", "CSCE652", "CSCE653", "CSCE654", "CSCE655", "CSCE656", "CSCE657", "CSCE658", "CSCE659", "CSCE660", "CSCE661", "CSCE662", "CSCE663", "CSCE664", "CSCE665", "CSCE666", "CSCE667", "CSCE668", "CSCE669", "CSCE670", "CSCE671", "CSCE672", "CSCE673", "CSCE674", "CSCE675", "CSCE676", "CSCE677", "CSCE678", "CSCE679", "CSCE680", "CSCE681", "CSCE682", "CSCE683", "CSCE684", "CSCE685", "CSCE686", "CSCE687", "CSCE688", "CSCE689", "CSCE690", "CSCE691", "CSCE692", "CSCE693", "CSCE694", "CSCE695", "CSCE696", "CSCE697", "CSCE698", "CSCE699"};
        List csCourseList = Arrays.asList(csCourses);
        Double gpaTotal = 0.0;
        Double runningCreditTotal = 0.0;
        for (CourseTaken course : courseTaken) {
            if (course.getGrade() != null) {
                if (csCourseList.contains(course.getCourse().getID())) {
                    Double qualityPoints = Double.parseDouble(course.getCourse().getNumCredits());
                    Double grade = 0.0;
                    switch (course.getGrade()) {
                        case A:
                            grade = 4.0 * qualityPoints;
                            break;
                        case B:
                            grade = 3.0 * qualityPoints;
                            break;
                        case C:
                            grade = 2.0 * qualityPoints;
                            break;
                        case D:
                            grade = 1.0 * qualityPoints;
                            break;
                        case F:
                            grade = 0.0 * qualityPoints;
                            break;
                        default:
                            break;
                    }
                    gpaTotal += grade;
                    runningCreditTotal += Double.parseDouble(course.getCourse().getNumCredits());
                }
            } else {
                // class is not used for major gpa calculation
            }
        }
        Double overallGPA = gpaTotal / runningCreditTotal;
        return overallGPA;
    }

    // CALCULATE COMPUTER INFORMATION SYSTEM GPA
    public Double calculateCISGPA(ArrayList<CourseTaken> courseTaken) {
        String[] csCourses = {"CSCE145", "CSCE390", "MGSC390", "MGSC490", "MGSC590", "CSCE146", "CSCE190", "CSCE201",
                "CSCE210", "CSCE215", "CSCE240", "CSCE205", "CSCE311", "CSCE350", "CSCE416", "CSCE490", "CSCE492", "CSCE520",
                "CSCE522", "CSCE317", "CSCE500", "CSCE501", "CSCE502", "CSCE503", "CSCE504", "CSCE505", "CSCE506", "CSCE507", "CSCE508", "CSCE509", "CSCE510", "CSCE511", "CSCE512", "CSCE513", "CSCE514", "CSCE515", "CSCE516", "CSCE517", "CSCE518", "CSCE519", "CSCE520", "CSCE521", "CSCE522", "CSCE523", "CSCE524", "CSCE525", "CSCE526", "CSCE527", "CSCE528", "CSCE529", "CSCE530", "CSCE531", "CSCE532", "CSCE533", "CSCE534", "CSCE535", "CSCE536", "CSCE537", "CSCE538", "CSCE539", "CSCE540", "CSCE541", "CSCE542", "CSCE543", "CSCE544", "CSCE545", "CSCE546", "CSCE547", "CSCE548", "CSCE549", "CSCE550", "CSCE551", "CSCE552", "CSCE553", "CSCE554", "CSCE555", "CSCE556", "CSCE557", "CSCE558", "CSCE559", "CSCE560", "CSCE561", "CSCE562", "CSCE563", "CSCE564", "CSCE565", "CSCE566", "CSCE567", "CSCE568", "CSCE569", "CSCE570", "CSCE571", "CSCE572", "CSCE573", "CSCE574", "CSCE575", "CSCE576", "CSCE577", "CSCE578", "CSCE579", "CSCE580", "CSCE581", "CSCE582", "CSCE583", "CSCE584", "CSCE585", "CSCE586", "CSCE587", "CSCE588", "CSCE589", "CSCE590", "CSCE591", "CSCE592", "CSCE593", "CSCE594", "CSCE595", "CSCE596", "CSCE597", "CSCE598", "CSCE599", "CSCE600", "CSCE601", "CSCE602", "CSCE603", "CSCE604", "CSCE605", "CSCE606", "CSCE607", "CSCE608", "CSCE609", "CSCE610", "CSCE611", "CSCE612", "CSCE613", "CSCE614", "CSCE615", "CSCE616", "CSCE617", "CSCE618", "CSCE619", "CSCE620", "CSCE621", "CSCE622", "CSCE623", "CSCE624", "CSCE625", "CSCE626", "CSCE627", "CSCE628", "CSCE629", "CSCE630", "CSCE631", "CSCE632", "CSCE633", "CSCE634", "CSCE635", "CSCE636", "CSCE637", "CSCE638", "CSCE639", "CSCE640", "CSCE641", "CSCE642", "CSCE643", "CSCE644", "CSCE645", "CSCE646", "CSCE647", "CSCE648", "CSCE649", "CSCE650", "CSCE651", "CSCE652", "CSCE653", "CSCE654", "CSCE655", "CSCE656", "CSCE657", "CSCE658", "CSCE659", "CSCE660", "CSCE661", "CSCE662", "CSCE663", "CSCE664", "CSCE665", "CSCE666", "CSCE667", "CSCE668", "CSCE669", "CSCE670", "CSCE671", "CSCE672", "CSCE673", "CSCE674", "CSCE675", "CSCE676", "CSCE677", "CSCE678", "CSCE679", "CSCE680", "CSCE681", "CSCE682", "CSCE683", "CSCE684", "CSCE685", "CSCE686", "CSCE687", "CSCE688", "CSCE689", "CSCE690", "CSCE691", "CSCE692", "CSCE693", "CSCE694", "CSCE695", "CSCE696", "CSCE697", "CSCE698", "CSCE699"};
        List csCourseList = Arrays.asList(csCourses);
        Double gpaTotal = 0.0;
        Double runningCreditTotal = 0.0;
        for (CourseTaken course : courseTaken) {
            if (course.getGrade() != null) {
                if (csCourseList.contains(course.getCourse().getID())) {
                    Double qualityPoints = Double.parseDouble(course.getCourse().getNumCredits());
                    Double grade = 0.0;
                    switch (course.getGrade()) {
                        case A:
                            grade = 4.0 * qualityPoints;
                            break;
                        case B:
                            grade = 3.0 * qualityPoints;
                            break;
                        case C:
                            grade = 2.0 * qualityPoints;
                            break;
                        case D:
                            grade = 1.0 * qualityPoints;
                            break;
                        case F:
                            grade = 0.0 * qualityPoints;
                            break;
                        default:
                            break;
                    }
                    gpaTotal += grade;
                    runningCreditTotal += Double.parseDouble(course.getCourse().getNumCredits());
                }
            } else {
                // class is not used for major gpa calculation
            }
        }
        Double overallGPA = gpaTotal / runningCreditTotal;
        return overallGPA;
    }

    /**
     * @return gets the network id of the student
     */

    public String getNetworkID() {
        return student.getNetworkID();
    }

    @Override
    public String toString() {
        return "StudentRecord{" +
                "major='" + major + '\'' +
                ", student=" + student +
                ", advisor=" + advisor +
                ", termBegan=" + termBegan +
                ", coursesTaken=" + coursesTaken +
                ", notes=" + notes +
                '}';
    }

}
