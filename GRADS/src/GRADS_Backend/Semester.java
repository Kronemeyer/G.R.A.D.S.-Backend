package GRADS_Backend;

import java.io.IOException;

public enum Semester {
    FALL, SPRING, SUMMER;

    /**
     * converts from semester value to string
     * @return the string value of the semester
     */
    public String toValue() {
        switch (this) {
            case FALL:
                return "FALL";
            case SPRING:
                return "SPRING";
            case SUMMER:
                return "SUMMER";
        }
        return null;
    }
    /**
     * converts from string to semester value
     * @param value String value of the semester
     * @return a semester value
     * @throws IOException if semester does not match one of the semester
     */
    public static Semester forValue(String value) throws IOException {
        if (value.equals("FALL")) return FALL;
        if (value.equals("SPRING")) return SPRING;
        if (value.equals("SUMMER")) return SUMMER;
        throw new IOException("Cannot deserialize Semester");
    }
}