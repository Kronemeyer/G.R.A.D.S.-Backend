package GRADS_Backend;

import com.google.gson.annotations.SerializedName;

public class Advisor {

    public Advisor() {
    }

    public Advisor(String program, String firstName, String lastName) {
        this.program = program;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private String program;

    private String firstName;
    private String lastName;

    /**
     * gets the program
     *
     * @return current value of program
     */
    @SerializedName("program")
    public String getProgram() {
        return program;
    }

    /**
     * changes program to the given value
     *
     * @param value the value to change program to
     */
    @SerializedName("program")
    public void setProgram(String value) {
        this.program = value;
    }

    /**
     * gets the first name of the advisor
     *
     * @return the first name of the advisor
     */
    @SerializedName("firstName")
    public String getFirstName() {
        return firstName;
    }

    /**
     * changes the first name to the given value
     *
     * @param value the value to change the first name to
     */
    @SerializedName("firstName")
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * gets the last name
     *
     * @return the last name of the advisor
     */
    @SerializedName("lastName")
    public String getLastName() {
        return lastName;
    }

    /**
     * changes last name to the given value
     *
     * @param value the value to change the last name to
     */
    @SerializedName("lastName")
    public void setLastName(String value) {
        this.lastName = value;
    }

    @Override
    public String toString() {
        return "Advisor{" +
                "program='" + program + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
