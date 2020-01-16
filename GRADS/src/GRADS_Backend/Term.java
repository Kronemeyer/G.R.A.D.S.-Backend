package GRADS_Backend;

import com.google.gson.annotations.SerializedName;

public class Term {

    private Semester semester;
    private int year;

    public Term()
    {

    }

    public Term(Semester semester, int year) {
        this.semester = semester;
        this.year = year;
    }

    /**
     * gets the semester
     * @return the semester
     */
    @SerializedName("semester")
    public Semester getSemester() {
        return semester;
    }

    /**
     * changes the semester to the given value
     * @param value the value to change semester to
     */
    @SerializedName("semester")
    public void setSemester(Semester value) {
        this.semester = value;
    }

    /**
     * gets the year
     * @return int value of year
     */
    @SerializedName("year")
    public int getYear() {
        return year;
    }

    /**
     * changes year to the given value
     * @param value value to change year to
     */
    @SerializedName("year")
    public void setYear(int value) {
        this.year = value;
    }

    @Override
    public String toString() {
        return "Term{" +
                "semester=" + semester +
                ", year=" + year +
                '}';
    }

}
