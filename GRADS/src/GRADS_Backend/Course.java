package GRADS_Backend;

import com.google.gson.annotations.SerializedName;

public class Course {

    private String name;
    private String id;
    private String numCredits;
    private String numCreditsEarned;
    private Term term;

    /*public Course(String id, String numCredits) {
        this.name = "n/a";
        this.id = id;
        this.numCredits = numCredits;
    }*/

    public Course(String name, String id, String numCredits) {
        this.name = name;
        this.id = id;
        this.numCredits = numCredits;
    }

    /**
     * gets the name
     * @return the name
     */
    @SerializedName("name")
    public String getName() {
        return name;
    }

    /**
     * changes the name to the given value
     * @param value the name to set the course to
     */
    @SerializedName("name")
    public void setName(String value) {
        this.name = value;
    }

    /**
     * gets the id
     * @return the id of the requested course
     */
    @SerializedName("id")
    public String getID() {
        return id;
    }

    /**
     * changes the id to the given value
     * @param value the id to set the course to
     */
    @SerializedName("id")
    public void setID(String value) {
        this.id = value;
    }

    /**
     * gets the number of credits
     * @return the number of credits the course is worth
     */
    @SerializedName("numCredits")
    public String getNumCredits() {
        return numCredits;
    }

    /**
     * @param value the number to set the credits the course is worth
     */
    @SerializedName("numCredits")
    public void setNumCredits(String value) {
        this.numCredits = value;
    }

    /**
     * sets the number of credits earned to the given value
     *
     * @param numCreditsEarned the new value of numCreditsEarned
     */
    public void setNumCreditsEarned(String numCreditsEarned) {
        this.numCreditsEarned = numCreditsEarned;
    }

    /**
     * gets the number of credits earned
     *
     * @return the number of credits earned
     */
    public String getNumCreditsEarned() {
        return numCreditsEarned;
    }

}
