package GRADS_Backend.CustomErrors;

public class ErrorLoadingRequirementsException extends Exception{

    private int id;

    /**
     *
     */
    public ErrorLoadingRequirementsException() { id = 8; }

    /**
     * @return
     */
    public String toString() { return "ErrorLoadingRequirementsException[" + id + "]"; }
}
