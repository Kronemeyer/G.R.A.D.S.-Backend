package GRADS_Backend;

import GRADS_Backend.CustomErrors.ErrorLoadingRequirementsException;
import GRADS_Backend.CustomErrors.NoClassesTakenException;
import GRADS_Backend.CustomErrors.RequirementCheckListNotFoundException;
import GRADS_Backend.CustomErrors.UnsupportedMajorException;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.*;

public class SummaryFactory {
    private static GradRequirement[] generalGradRequirements;
    private static GradRequirement[] ceGradRequirements;
    private static GradRequirement[] cisGradRequirements;
    private static GradRequirement[] csGradRequirements;

    /**
     * generates a summary report for a student
     * @param user user to generate the summary for
     * @return the newly generated summary
     */
    public ProgressSummary generateSummaryReport(User user) throws UnsupportedMajorException, ErrorLoadingRequirementsException {
        try {
            loadRequirements();
        } catch (Exception e) {
            System.out.println("Error occurred loading the requirements: "+e.getStackTrace());
            throw new ErrorLoadingRequirementsException();
        }

        // params: String major, Student user, Advisor advisor, Term termBegan, ArrayList<CourseTaken> coursesTaken, ArrayList<String> notes
        ProgressSummary progressSummary = new ProgressSummary(user.getStudentRecord().getMajor(), user.getStudentRecord().getStudent(),
                user.getStudentRecord().getAdvisor(), user.getStudentRecord().getTermBegan(), user.getStudentRecord().getCoursesTaken(),
                user.getStudentRecord().getNotes());

        try {
            progressSummary.setMajorGPA(calculateMajorGPA(user.getStudentRecord().getCoursesTaken(), user.getStudentRecord().getMajor()));
            progressSummary.setOverallGPA(calculateOverallGPA(user.getStudentRecord().getCoursesTaken()));
        } catch (Exception e) {
            System.out.println("Student has not yet taken classes and therefore does not yet have a GPA!");
            progressSummary.setMajorGPA(0.0);
            progressSummary.setOverallGPA(0.0);
            e.printStackTrace();
        }
        double rollingPassing = 0;
        try {
            // check general requirements
            rollingPassing = checkGeneralRequirements(user.getStudentRecord().getCoursesTaken());
            progressSummary.addTakenRequirements(generalGradRequirements);

            // check major specific requirements
            switch (user.getStudentRecord().getMajor()) {
                case "COMPUTER_SCIENCE":
                    rollingPassing += checkCSRequirements(user.getStudentRecord().getCoursesTaken());
                    progressSummary.addTakenRequirements(csGradRequirements);
                    progressSummary.setGraduationPercentage((rollingPassing/16.0)*100);
                    break;
                case "COMPUTER_INFORMATION_SYSTEMS":
                    rollingPassing += checkCISRequirements(user.getStudentRecord().getCoursesTaken());
                    progressSummary.addTakenRequirements(cisGradRequirements);
                    progressSummary.setGraduationPercentage((rollingPassing/18.0)*100);
                    break;
                case "COMPUTER_ENGINEERING":
                    rollingPassing +=  checkCERequirements(user.getStudentRecord().getCoursesTaken());
                    progressSummary.addTakenRequirements(ceGradRequirements);
                    progressSummary.setGraduationPercentage((rollingPassing/16.0)*100);
                    break;
                default:
                    System.out.println("[-] Student has an unsupported major");
                    throw new UnsupportedMajorException();
            }
        } catch (Exception e) {
            System.out.println("Student has not yet taken classes and therefore does not yet have a GPA!");
            progressSummary.setMajorGPA(0.0);
            progressSummary.setOverallGPA(0.0);
            e.printStackTrace();
        }
        return progressSummary;
    }

    /**
     * generates a prospective progress report for the student
     * @param prospectiveCourses list of prospective classes to merge with taken classes for summary report generation
     * @param user user to generate the summary for
     * @return the newly generated prospective summary
     */
    public ProgressSummary generateSummaryReport(User user,  ArrayList<CourseTaken> prospectiveCourses) throws UnsupportedMajorException, ErrorLoadingRequirementsException {
        try {
            loadRequirements();
        } catch (Exception e) {
            System.out.println("Error occurred loading the requirements: "+e.getStackTrace());
            throw new ErrorLoadingRequirementsException();
        }

        // adding the prospective classes to the list of classes already taken by the user
        ArrayList<CourseTaken> prospectiveCoursesTaken = user.getStudentRecord().getCoursesTaken();
        for (CourseTaken course : prospectiveCourses) {
            prospectiveCoursesTaken.add(course);
        }

        ProgressSummary progressSummary = new ProgressSummary(user.getStudentRecord().getMajor(), user.getStudentRecord().getStudent(),
                user.getStudentRecord().getAdvisor(), user.getStudentRecord().getTermBegan(), prospectiveCoursesTaken,
                user.getStudentRecord().getNotes());

        try {
            progressSummary.setMajorGPA(calculateMajorGPA(prospectiveCoursesTaken, user.getStudentRecord().getMajor()));
            progressSummary.setOverallGPA(calculateOverallGPA(prospectiveCoursesTaken));
        } catch (Exception e) {
            System.out.println("Student has not yet taken classes and therefore does not yet have a GPA!");
            progressSummary.setMajorGPA(0.0);
            progressSummary.setOverallGPA(0.0);
            e.printStackTrace();
        }


        // setting the graduatuon percentage
        double rollingPassing = 0;
        try {
            // check general requirements
            rollingPassing = checkGeneralRequirements(prospectiveCoursesTaken);
            progressSummary.addTakenRequirements(generalGradRequirements);

            // check major specific requirements
            switch (user.getStudentRecord().getMajor()) {
                case "COMPUTER_SCIENCE":
                    rollingPassing += checkCSRequirements(prospectiveCoursesTaken);
                    progressSummary.addTakenRequirements(csGradRequirements);
                    progressSummary.setGraduationPercentage((rollingPassing/16.0)*100);
                    break;
                case "COMPUTER_INFORMATION_SYSTEMS":
                    rollingPassing += checkCISRequirements(prospectiveCoursesTaken);
                    progressSummary.addTakenRequirements(cisGradRequirements);
                    progressSummary.setGraduationPercentage((rollingPassing/18.0)*100);
                    break;
                case "COMPUTER_ENGINEERING":
                    rollingPassing +=  checkCERequirements(prospectiveCoursesTaken);
                    progressSummary.addTakenRequirements(ceGradRequirements);
                    progressSummary.setGraduationPercentage((rollingPassing/16.0)*100);
                    break;
                default:
                    System.out.println("[-] Student has an unsupported major");
                    throw new UnsupportedMajorException();
            }
        } catch (Exception e) {
            System.out.println("Student has not yet taken classes and therefore does not yet have a GPA!");
            progressSummary.setMajorGPA(0.0);
            progressSummary.setOverallGPA(0.0);
            e.printStackTrace();
        }

        return progressSummary;
    }

    public static double checkGeneralRequirements(ArrayList<CourseTaken> courseTaken) throws NoClassesTakenException {
        if (courseTaken == null || courseTaken.size() <= 0) {
            throw new NoClassesTakenException();
        }
        double numberRequirementsPassed = 0;
        System.out.println("\nChecking general requirements...");
        for (int i =0 ; i<generalGradRequirements.length; i++) {
            generalGradRequirements[i].initTakenClass();
            switch (generalGradRequirements[i].getName()) {
                case "CAROLINA_CORE_CMS": // one class requirement
                    for (CourseTaken currCourse : courseTaken) {
                        if (generalGradRequirements[i].contains(currCourse.getCourse())) {
                            generalGradRequirements[i].setPassed(true);
                            generalGradRequirements[i].addTakenClass(currCourse);
                            numberRequirementsPassed++;
                        }
                    }
                    break;
                case "CAROLINA_CORE_VSR":  // one class requirement
                    for (CourseTaken currCourse : courseTaken) {
                        if (generalGradRequirements[i].contains(currCourse.getCourse())) {
                            generalGradRequirements[i].setPassed(true);
                            generalGradRequirements[i].addTakenClass(currCourse);
                            numberRequirementsPassed++;
                        }
                    }
                    break;
                case "GEN_ED_ENGL":  // one class requirement
                    for (CourseTaken currCourse : courseTaken) {
                        if (generalGradRequirements[i].contains(currCourse.getCourse())) {
                            generalGradRequirements[i].setPassed(true);
                            generalGradRequirements[i].addTakenClass(currCourse);
                            numberRequirementsPassed++;
                        }
                    }
                    break;
                case "CAROLINA_CORE_GFL": // one class requirement
                    for (CourseTaken currCourse : courseTaken) {
                        String currentCourse = currCourse.getCourse().getID();
                        if (generalGradRequirements[i].contains(currCourse.getCourse())) {
                            generalGradRequirements[i].setPassed(true);
                            generalGradRequirements[i].addTakenClass(currCourse);
                            numberRequirementsPassed++;
                        }
                    }
                    break;
                case "CAROLINA_CORE_GSS": // 3 credit hours requirement
                    int creditSum = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (generalGradRequirements[i].contains(currCourse.getCourse())) {
                            creditSum += Integer.parseInt(currCourse.getCourse().getNumCredits());
                            generalGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (creditSum>3){
                        generalGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CAROLINA_CORE_INF": // 3 credit hours requirement
                    creditSum = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (generalGradRequirements[i].contains(currCourse.getCourse())) {
                            creditSum += Integer.parseInt(currCourse.getCourse().getNumCredits());
                            generalGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (creditSum>3){
                        generalGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CAROLINA_CORE_GHS": // 3 credit hours requirement
                    creditSum = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (generalGradRequirements[i].contains(currCourse.getCourse())) {
                            creditSum += Integer.parseInt(currCourse.getCourse().getNumCredits());
                            generalGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (creditSum>3){
                        generalGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CAROLINA_CORE_AIU": // 3 credit hours requirement
                    creditSum = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (generalGradRequirements[i].contains(currCourse.getCourse())) {
                            creditSum += Integer.parseInt(currCourse.getCourse().getNumCredits());
                            generalGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (creditSum>3){
                        generalGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CAROLINA_CORE_CMW": // both classes required
                    int expectedNum = 2;
                    int actual = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (generalGradRequirements[i].contains(currCourse.getCourse())) {
                            actual++;
                            generalGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (actual >= expectedNum) {
                        generalGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                default:
                    System.out.println("Requirement not found: "+generalGradRequirements[i].getName());
                    break;
            }
        }
       // rollingGraduationPercentage = (double)numberRequirementsPassed/9.0;
        System.out.println("done!");
        return numberRequirementsPassed;
    }


    public static double checkCSRequirements(ArrayList<CourseTaken> courseTaken) throws NoClassesTakenException {
        if (courseTaken == null || courseTaken.size() <= 0) {
            throw new NoClassesTakenException();
        }
        double numberRequirementsPassed = 0;
        System.out.println("\nChecking CS requirments...");
        for (int i=0; i<csGradRequirements.length; i++) {
            csGradRequirements[i].initTakenClass();
            switch (csGradRequirements[i].getName()) {
                case "CSCE_CAROLINA_CORE_ARP": // one class requirement
                    int expectedNum = 2;
                    int actual = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (csGradRequirements[i].contains(currCourse.getCourse())) {
                            actual++;
                            csGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (actual >= expectedNum) {
                        csGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CSCE_GEN_ED_REQUIRED": // all required classes for this requirement
                    int num = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (csGradRequirements[i].contains(currCourse.getCourse())) {
                            num++;
                            csGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (num >= csGradRequirements[i].getRequiredClasses().size()) {
                        csGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CSCE_GEN_ED_LAB":
                    for (CourseTaken currCourse : courseTaken) {
                        if (csGradRequirements[i].contains(currCourse.getCourse())) {
                            csGradRequirements[i].setPassed(true);
                            csGradRequirements[i].addTakenClass(currCourse);
                            numberRequirementsPassed++;
                        }
                    }
                    break;
                case "CSCE_GEN_ED_LIBARTS":
                    int creditSum = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (csGradRequirements[i].contains(currCourse.getCourse())) {
                            creditSum += Integer.parseInt(currCourse.getCourse().getNumCredits());
                            csGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (creditSum>9){
                        csGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CSCE_LOWER_DIV_COMPUTING": // 3 credit hours required
                    num = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (csGradRequirements[i].contains(currCourse.getCourse())) {
                            num++;
                            csGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (num >= csGradRequirements[i].getRequiredClasses().size()) {
                        csGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CSCE_REQUIRED":
                    num = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (csGradRequirements[i].contains(currCourse.getCourse())) {
                            num++;
                            csGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (num >= csGradRequirements[i].getRequiredClasses().size()) {
                        csGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CSCE_ELECTIVE":
                    creditSum = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (csGradRequirements[i].contains(currCourse.getCourse())) {
                            creditSum += Integer.parseInt(currCourse.getCourse().getNumCredits());
                            csGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (creditSum>9){
                        csGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                default:
                    System.out.println("Requirement not found: "+csGradRequirements[i].getName());
                    break;
            }
        }
        System.out.println("done!");
        return numberRequirementsPassed;
    }

    public static double checkCERequirements(ArrayList<CourseTaken> courseTaken) throws NoClassesTakenException {
        if (courseTaken == null || courseTaken.size() <= 0) {
            throw new NoClassesTakenException();
        }
        double numberRequirementsPassed = 0;
        System.out.println("\nChecking CE requirments...");
        for (int i =0 ; i<ceGradRequirements.length; i++) {
            ceGradRequirements[i].initTakenClass();
            switch (ceGradRequirements[i].getName()) {
                case "CAROLINA_CORE_GFL": // one class requirement
                    int num = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (ceGradRequirements[i].contains(currCourse.getCourse())) {
                            num++;
                            ceGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (num >= ceGradRequirements[i].getRequiredClasses().size()) {
                        ceGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CPE_CAROLINA_CORE_ARP": // all required classes for this requirement
                    num = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (ceGradRequirements[i].contains(currCourse.getCourse())) {
                            num++;
                            ceGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (num >= ceGradRequirements[i].getRequiredClasses().size()) {
                        ceGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CPE_GEN_ED_REQUIRED":
                    num = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (ceGradRequirements[i].contains(currCourse.getCourse())) {
                            num++;
                            ceGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (num >= ceGradRequirements[i].getRequiredClasses().size()) {
                        ceGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CPE_LOWER_DIV_COMPUTING":
                    num = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (ceGradRequirements[i].contains(currCourse.getCourse())) {
                            num++;
                            ceGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (num >= ceGradRequirements[i].getRequiredClasses().size()) {
                        ceGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CPE_REQUIRED": // 3 credit hours required
                    num = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (ceGradRequirements[i].contains(currCourse.getCourse())) {
                            num++;
                            ceGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (num >= ceGradRequirements[i].getRequiredClasses().size()) {
                        ceGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CPE_ELECTIVE":
                    int creditSum = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (ceGradRequirements[i].contains(currCourse.getCourse())) {
                            String course = currCourse.getCourse().getNumCredits();
                            creditSum += Integer.parseInt(course);
                            ceGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (creditSum>9){
                        ceGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CPE_EE":
                    num = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (ceGradRequirements[i].contains(currCourse.getCourse())) {
                            num++;
                            ceGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (num >= ceGradRequirements[i].getRequiredClasses().size()) {
                        ceGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                default:
                    System.out.println("Requirement not found: "+ceGradRequirements[i].getName());
                    break;
            }
        }
        System.out.println("done!");
        return numberRequirementsPassed;
    }

    public static double checkCISRequirements(ArrayList<CourseTaken> courseTaken) throws NoClassesTakenException {
        if (courseTaken == null || courseTaken.size() <= 0) {
            throw new NoClassesTakenException();
        }
        double numberRequirementsPassed = 0;
        System.out.println("\nChecking CIS requirments...");
        for (int i =0 ; i<cisGradRequirements.length; i++) {
            cisGradRequirements[i].initTakenClass();
            switch (cisGradRequirements[i].getName()) {
                case "CIS_CAROLINA_CORE_ARP": // 2 class requirement
                    int expectedNum = 2;
                    int actual = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (cisGradRequirements[i].contains(currCourse.getCourse())) {
                            actual++;
                            cisGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (actual >= expectedNum) {
                        cisGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CIS_CAROLINA_CORE_SCI": // all required classes for this requirement
                    expectedNum = 2;
                    actual = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (cisGradRequirements[i].contains(currCourse.getCourse())) {
                            actual++;
                            cisGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (actual >= expectedNum) {
                        cisGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CIS_GEN_ED_MATH": // one math and two stats classes
                    int mathCount = 0;
                    int statCount = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (cisGradRequirements[i].contains(currCourse.getCourse())) {
                            if (currCourse.getCourse().getID().toLowerCase().contains("MATH".toLowerCase())) {
                                mathCount++;
                            } else if (currCourse.getCourse().getID().toLowerCase().contains("STAT".toLowerCase())) {
                                statCount++;
                            }
                            cisGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (mathCount >= 1 && statCount >= 2) {
                        cisGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CIS_GEN_ED_LIBARTS": // 9 credit requirement
                    //cisGradRequirements[i].initTakenClass();
                    int creditSum = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (cisGradRequirements[i].contains(currCourse.getCourse())) {
                            String course = currCourse.getCourse().getNumCredits();
                            creditSum += Integer.parseInt(course);
                            cisGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (creditSum>9){
                        cisGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CIS_LOWER_DIV_COMPUTING": // all required
                    int num = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (cisGradRequirements[i].contains(currCourse.getCourse())) {
                            num++;
                            cisGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (num >= cisGradRequirements[i].getRequiredClasses().size()) {
                        cisGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CIS_REQUIRED": // all required
                    num = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (cisGradRequirements[i].contains(currCourse.getCourse())) {
                            num++;
                            cisGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (num >= cisGradRequirements[i].getRequiredClasses().size()) {
                        cisGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CIS_ELECTIVE": // 3 credits required
                    creditSum = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (cisGradRequirements[i].contains(currCourse.getCourse())) {
                            creditSum += Integer.parseInt(currCourse.getCourse().getNumCredits());
                            cisGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (creditSum>=3){
                        cisGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CIS_BIM_REQUIRED": // all required
                    num = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (cisGradRequirements[i].contains(currCourse.getCourse())) {
                            num++;
                            cisGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (num >= cisGradRequirements[i].getRequiredClasses().size()) {
                        cisGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                case "CIS_BIM_ELECTIVE": // 2 or more courses required
                    num = 0;
                    for (CourseTaken currCourse : courseTaken) {
                        if (cisGradRequirements[i].contains(currCourse.getCourse())) {
                            num++;
                            cisGradRequirements[i].addTakenClass(currCourse);
                        }
                    }
                    if (num >= 2) {
                        cisGradRequirements[i].setPassed(true);
                        numberRequirementsPassed++;
                    }
                    break;
                default:
                    System.out.println("Requirement not found: "+cisGradRequirements[i].getName());
                    break;
            }
        }
        System.out.println("done!");
        return numberRequirementsPassed;
    }


    public static void loadRequirements() throws RequirementCheckListNotFoundException {
        // loading requirements for the user
        generalGradRequirements = importGradRules("./res/requirements/GENERAL_RULES.json");
        ceGradRequirements =  importGradRules("./res/requirements/COMPUTER_ENGINEERING.json");
        cisGradRequirements =  importGradRules("./res/requirements/COMPUTER_INFORMATION_SYSTEMS.json");
        csGradRequirements =  importGradRules("./res/requirements/COMPUTER_SCIENCE.json");
        if (generalGradRequirements == null || ceGradRequirements == null || cisGradRequirements == null || csGradRequirements == null) {
            throw new RequirementCheckListNotFoundException();
        }
    }


    public static GradRequirement[] importGradRules(String generalRules) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(generalRules));
            String json = "";
            while (reader.ready()) {
                json += reader.readLine();
            }
            reader.close();
            GradRequirement[] gradRequirements = new Gson().fromJson(json, GradRequirement[].class);
            //System.out.println("[+] Imported rules!");
            return gradRequirements;
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return null;
    }

    /**
     * gets data from json and returns a list of courses from this information
     * @param student the student to get the checklist for
     * @return the requiredCoursesCheckList
     */
    public ArrayList<Course> requirementCheckResults(Student student) throws Exception{
        String major = student.getMajor();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(major + ".txt"));
                String json = "";
                while (reader.ready()) {
                    json += reader.readLine();
                }
                reader.close();
                ArrayList compSciCheckList = new Gson().fromJson(json, ArrayList.class);    // couldnt figure out how to gson
                // straight into a map so this is a bit hacky
                // but works
                return compSciCheckList;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
        } return null;

    }

    // CALCULATE OVERALL SCIENCE GPA
    public static Double calculateOverallGPA(ArrayList<CourseTaken> courseTaken) throws NoClassesTakenException {
        if (courseTaken.size() <= 0) {
            throw new NoClassesTakenException();
        }
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

    public static Double calculateMajorGPA(ArrayList<CourseTaken> courseTaken, String studentMajor) throws NoClassesTakenException {
        if (courseTaken == null || courseTaken.size() <= 0) {
            throw new NoClassesTakenException();
        }
        String[] applicableCourses = null;
        String[] csCourses = {"CSCE145", "CSCE146", "CSCE190", "CSCE211", "CSCE212", "CSCE215",
                "CSCE240", "CSCE247", "CSCE311", "CSCE330", "CSCE350", "CSCE355", "CSCE416", "CSCE490", "CSCE492",
                "CSCE_ELECTIVE", "CSCE317"};
        String[] ceCourses = {"CSCE145", "CSCE146", "CSCE190", "CSCE211", "CSCE212", "CSCE215", "CSCE240", "CSCE274", "CSCE311",
                "CSCE313", "CSCE317", "CSCE350", "CSCE416", "CSCE490", "CSCE492", "CSCE611", "CSCE330", "CSCE355", "ELCT321",
                "ELCT331", "ELCT101", "ELCT221", "ELCT222", "ELCT371", "CSCE510", "CSCE511", "CSCE512", "CSCE513", "CSCE514", "CSCE515", "CSCE516", "CSCE517", "CSCE518", "CSCE519", "CSCE520", "CSCE521", "CSCE522", "CSCE523", "CSCE524", "CSCE525", "CSCE526", "CSCE527", "CSCE528", "CSCE529", "CSCE530", "CSCE531", "CSCE532", "CSCE533", "CSCE534", "CSCE535", "CSCE536", "CSCE537", "CSCE538", "CSCE539", "CSCE540", "CSCE541", "CSCE542", "CSCE543", "CSCE544", "CSCE545", "CSCE546", "CSCE547", "CSCE548", "CSCE549", "CSCE550", "CSCE551", "CSCE552", "CSCE553", "CSCE554", "CSCE555", "CSCE556", "CSCE557", "CSCE558", "CSCE559", "CSCE560", "CSCE561", "CSCE562", "CSCE563", "CSCE564", "CSCE565", "CSCE566", "CSCE567", "CSCE568", "CSCE569", "CSCE570", "CSCE571", "CSCE572", "CSCE573", "CSCE574", "CSCE575", "CSCE576", "CSCE577", "CSCE578", "CSCE579", "CSCE580", "CSCE581", "CSCE582", "CSCE583", "CSCE584", "CSCE585", "CSCE586", "CSCE587", "CSCE588", "CSCE589", "CSCE590", "CSCE591", "CSCE592", "CSCE593", "CSCE594", "CSCE595", "CSCE596", "CSCE597", "CSCE598", "CSCE599", "CSCE600", "CSCE601", "CSCE602", "CSCE603", "CSCE604", "CSCE605", "CSCE606", "CSCE607", "CSCE608", "CSCE609", "CSCE610", "CSCE611", "CSCE612", "CSCE613", "CSCE614", "CSCE615", "CSCE616", "CSCE617", "CSCE618", "CSCE619", "CSCE620", "CSCE621", "CSCE622", "CSCE623", "CSCE624", "CSCE625", "CSCE626", "CSCE627", "CSCE628", "CSCE629", "CSCE630", "CSCE631", "CSCE632", "CSCE633", "CSCE634", "CSCE635", "CSCE636", "CSCE637", "CSCE638", "CSCE639", "CSCE640", "CSCE641", "CSCE642", "CSCE643", "CSCE644", "CSCE645", "CSCE646", "CSCE647", "CSCE648", "CSCE649", "CSCE650", "CSCE651", "CSCE652", "CSCE653", "CSCE654", "CSCE655", "CSCE656", "CSCE657", "CSCE658", "CSCE659", "CSCE660", "CSCE661", "CSCE662", "CSCE663", "CSCE664", "CSCE665", "CSCE666", "CSCE667", "CSCE668", "CSCE669", "CSCE670", "CSCE671", "CSCE672", "CSCE673", "CSCE674", "CSCE675", "CSCE676", "CSCE677", "CSCE678", "CSCE679", "CSCE680", "CSCE681", "CSCE682", "CSCE683", "CSCE684", "CSCE685", "CSCE686", "CSCE687", "CSCE688", "CSCE689", "CSCE690", "CSCE691", "CSCE692", "CSCE693", "CSCE694", "CSCE695", "CSCE696", "CSCE697", "CSCE698", "CSCE699"};
        String[] cisCourses = {"CSCE145", "CSCE390", "MGSC390", "MGSC490", "MGSC590", "CSCE146", "CSCE190", "CSCE201",
                "CSCE210", "CSCE215", "CSCE240", "CSCE205", "CSCE311", "CSCE350", "CSCE416", "CSCE490", "CSCE492", "CSCE520",
                "CSCE522", "CSCE317", "CSCE500", "CSCE501", "CSCE502", "CSCE503", "CSCE504", "CSCE505", "CSCE506", "CSCE507", "CSCE508", "CSCE509", "CSCE510", "CSCE511", "CSCE512", "CSCE513", "CSCE514", "CSCE515", "CSCE516", "CSCE517", "CSCE518", "CSCE519", "CSCE520", "CSCE521", "CSCE522", "CSCE523", "CSCE524", "CSCE525", "CSCE526", "CSCE527", "CSCE528", "CSCE529", "CSCE530", "CSCE531", "CSCE532", "CSCE533", "CSCE534", "CSCE535", "CSCE536", "CSCE537", "CSCE538", "CSCE539", "CSCE540", "CSCE541", "CSCE542", "CSCE543", "CSCE544", "CSCE545", "CSCE546", "CSCE547", "CSCE548", "CSCE549", "CSCE550", "CSCE551", "CSCE552", "CSCE553", "CSCE554", "CSCE555", "CSCE556", "CSCE557", "CSCE558", "CSCE559", "CSCE560", "CSCE561", "CSCE562", "CSCE563", "CSCE564", "CSCE565", "CSCE566", "CSCE567", "CSCE568", "CSCE569", "CSCE570", "CSCE571", "CSCE572", "CSCE573", "CSCE574", "CSCE575", "CSCE576", "CSCE577", "CSCE578", "CSCE579", "CSCE580", "CSCE581", "CSCE582", "CSCE583", "CSCE584", "CSCE585", "CSCE586", "CSCE587", "CSCE588", "CSCE589", "CSCE590", "CSCE591", "CSCE592", "CSCE593", "CSCE594", "CSCE595", "CSCE596", "CSCE597", "CSCE598", "CSCE599", "CSCE600", "CSCE601", "CSCE602", "CSCE603", "CSCE604", "CSCE605", "CSCE606", "CSCE607", "CSCE608", "CSCE609", "CSCE610", "CSCE611", "CSCE612", "CSCE613", "CSCE614", "CSCE615", "CSCE616", "CSCE617", "CSCE618", "CSCE619", "CSCE620", "CSCE621", "CSCE622", "CSCE623", "CSCE624", "CSCE625", "CSCE626", "CSCE627", "CSCE628", "CSCE629", "CSCE630", "CSCE631", "CSCE632", "CSCE633", "CSCE634", "CSCE635", "CSCE636", "CSCE637", "CSCE638", "CSCE639", "CSCE640", "CSCE641", "CSCE642", "CSCE643", "CSCE644", "CSCE645", "CSCE646", "CSCE647", "CSCE648", "CSCE649", "CSCE650", "CSCE651", "CSCE652", "CSCE653", "CSCE654", "CSCE655", "CSCE656", "CSCE657", "CSCE658", "CSCE659", "CSCE660", "CSCE661", "CSCE662", "CSCE663", "CSCE664", "CSCE665", "CSCE666", "CSCE667", "CSCE668", "CSCE669", "CSCE670", "CSCE671", "CSCE672", "CSCE673", "CSCE674", "CSCE675", "CSCE676", "CSCE677", "CSCE678", "CSCE679", "CSCE680", "CSCE681", "CSCE682", "CSCE683", "CSCE684", "CSCE685", "CSCE686", "CSCE687", "CSCE688", "CSCE689", "CSCE690", "CSCE691", "CSCE692", "CSCE693", "CSCE694", "CSCE695", "CSCE696", "CSCE697", "CSCE698", "CSCE699"};

        try {
            switch (studentMajor) {
                case "COMPUTER_SCIENCE":
                    applicableCourses = csCourses;
                    break;
                case "COMPUTER_INFORMATION_SYSTEMS":
                    applicableCourses = cisCourses;
                    break;
                case "COMPUTER_ENGINEERING":
                    applicableCourses = ceCourses;
                    break;
                default:
                    System.out.println("[-] Student has an unsupported major");
                    throw new UnsupportedMajorException();
            }
        } catch (Exception e) {
            System.out.println("Error while determining user major!");
            e.printStackTrace();
        }
        if (applicableCourses != null) {
            List csCourseList = Arrays.asList(applicableCourses);
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
            DecimalFormat df = new DecimalFormat("#.00");
            double overallGPA = gpaTotal / runningCreditTotal;
            return overallGPA;
        } else {
            System.out.println("Class array null when determining user major!");
            return -1.0;
        }

    }
    @Override
    public String toString() {
        return super.toString();
    }
}
