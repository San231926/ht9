package uvg.edu.gt;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Closeable;

public class BitOutputStream implements Closeable {
    private OutputStream out;
    private int currentByte = 0;
    private int numBitsFilled = 0;

    public BitOutputStream(OutputStream out) {
        this.out = out;
    }

    public void writeBit(int bit) throws IOException {
        currentByte = (currentByte << 1) | bit;
        numBitsFilled++;
        if (numBitsFilled == 8) {
            out.write(currentByte);
            numBitsFilled = 0;
        }
    }

    @Override
    public void close() throws IOException {
        if (numBitsFilled > 0) {
            currentByte <<= (8 - numBitsFilled);
            out.write(currentByte);
        }
        out.close();
    }
}
