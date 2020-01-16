package GRADS_Backend.CustomErrors;

public class UnsupportedMajorException extends Exception {


    private int id;

    /**
     *
     */
    public UnsupportedMajorException() {
        id = 6;
    }

    /**
     * @return
     */
    public String toString() {
        return "RequirementCheckListNotFoundException[" + id + "]";
    }
}