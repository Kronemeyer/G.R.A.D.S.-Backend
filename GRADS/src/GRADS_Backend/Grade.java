package GRADS_Backend;

import java.io.IOException;

public enum Grade {
    A, B, C, D, F, P, EMPTY;


    /**
     * @param value
     * @return
     * @throws IOException
    */
    public static Grade forValue(String value) throws IOException {
        if (value.equals("A")) return A;
        if (value.equals("B")) return B;
        if (value.equals("C")) return C;
        if (value.equals("D")) return D;
        if (value.equals("F")) return F;
        if (value.equals("P")) return P;
        if (value.equals("_")) return EMPTY;
        throw new IOException("Cannot deserialize Grade");
    }
}

