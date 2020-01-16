package GRADS_Backend;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GradRequirement {
    private String name;
    private boolean passed = false;
    private ArrayList<Course> requiredClasses;
    private ArrayList<CourseTaken> takenClasses;

    public GradRequirement (String requirementTitle, ArrayList<Course> requiredCourses) {
        this.name = requirementTitle;
        this.requiredClasses = requiredCourses;
        //this.finalRequiredClasses = (ArrayList) requiredClasses.clone();
        this.takenClasses = new ArrayList<CourseTaken>();
    }

    public GradRequirement (String requirementTitle, ArrayList<Course> requiredCourses, ArrayList<CourseTaken> courseTaken) {
        this.name = requirementTitle;
        this.requiredClasses = requiredCourses;
        //this.finalRequiredClasses = (ArrayList) requiredClasses.clone();
        this.takenClasses = courseTaken;
    }



    @SerializedName("name")
    public String getName() {
        return name;
    }

    @SerializedName("name")
    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("requiredClasses")
    public ArrayList<Course> getRequiredClasses() {
        return requiredClasses;
    }

    @SerializedName("requiredClasses")
    public void setRequiredClasses(ArrayList<Course> requiredClasses) {
        this.requiredClasses = requiredClasses;
    }

    @SerializedName("passed")
    public boolean isPassed() {
        return passed;
    }

    @SerializedName("passed")
    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    @SerializedName("takenClasses")
    public ArrayList<CourseTaken> getTakenClasses() {
        return takenClasses;
    }

    @SerializedName("takenClasses")
    public void setTakenClasses(ArrayList<CourseTaken> takenClasses) {
        this.takenClasses = takenClasses;
    }

    @SerializedName("takenClasses")
    public void addTakenClass(CourseTaken course) {
        this.takenClasses.add(course);
    }

    @SerializedName("takenClasses")
    public void initTakenClass() {
        this.takenClasses = new ArrayList<CourseTaken>();
    }

    public boolean contains(Course course) {
         if (course == null) {
             return false;
         }
         for (int i = 0; i < requiredClasses.size(); i++) {
             Course requiredCourse = requiredClasses.get(i);
             if (requiredCourse.getID().split("-").length > 1) { // range of course numbers in course ID
                String[] parse = requiredCourse.getID().split("-");
                String prefix = parse[0].substring(0,4);
                int courseNum;
                if (course.getID().substring(4).contains("L")) {
                    int indexToRemove = 4 + course.getID().substring(4).indexOf("L");
                    courseNum = Integer.parseInt(course.getID().substring(4, indexToRemove));
                } else {
                    courseNum = Integer.parseInt(course.getID().substring(4));
                }
                int lower = Integer.parseInt(parse[0].substring(4));
                int upper = Integer.parseInt(parse[1]);
                if (requiredCourse.getID().contains(prefix) && courseNum>=lower && courseNum<=upper) {
                    requiredClasses.remove(i);
                    return true;
                }
             } else if (course.getID().equalsIgnoreCase(requiredCourse.getID())) { // single course number
                 requiredClasses.remove(i);
                 return true;
             }
         }
         return false;
    }

}
