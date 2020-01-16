package GRADS_Backend;

import GRADS_Backend.Course;
import GRADS_Backend.Grade;
import GRADS_Backend.Term;
import com.google.gson.annotations.SerializedName;

public class CourseTaken {
    private Course course;
    private Term term;
    private Grade grade;

    /**
     * @return
     */
    @SerializedName("course")
    public Course getCourse() {
        return course;
    }

    /**
     * @param value
     */
    @SerializedName("course")
    public void setCourse(Course value) {
        this.course = value;
    }

    /**
     * @return
     */
    @SerializedName("term")
    public Term getTerm() {
        return term;
    }

    /**
     * @param value
     */
    @SerializedName("term")
    public void setTerm(Term value) {
        this.term = value;
    }

    /**
     * @return
     */
    @SerializedName("grade")
    public Grade getGrade() {
        return grade;
    }

    /**
     * @param value
     */
    @SerializedName("grade")
    public void setGrade(Grade value) {
        this.grade = value;
    }


}
