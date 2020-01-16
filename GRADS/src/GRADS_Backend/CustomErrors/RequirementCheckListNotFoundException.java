package GRADS_Backend.CustomErrors;

public class RequirementCheckListNotFoundException extends Exception {

    private int id;

    /**
     *
     */
    public RequirementCheckListNotFoundException() {
        id = 5;
    }

    /**
     * @return
     */
    public String toString() {
        return "RequirementCheckListNotFoundException[" + id + "]";
    }
}