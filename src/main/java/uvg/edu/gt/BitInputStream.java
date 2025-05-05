package uvg.edu.gt;

import java.io.IOException;
import java.io.InputStream;
import java.io.Closeable;

/**
 * Esta clase envuelve un InputStream existente y proporciona capacidades de lectura a nivel de bit.
 */
public class BitInputStream implements Closeable {
    private InputStream in;
    private int currentByte;
    private int numBitsRemaining;

    /**
     * Construye un nuevo BitInputStream que envuelve el InputStream especificado.
     *
     * @param in Esta es la entrada del cual se lee bites
     */
    public BitInputStream(InputStream in) {
        this.in = in;
        currentByte = 0;
        numBitsRemaining = 0;
    }

    /**
     * Lee un solo bit del flujo de entrada.
     * Los bits se leen desde el más significativo hasta el menos significativo dentro de cada byte.
     *
     * @return El valor del bit leído
     * @throws IOException Si ocurre un error de E/S mientras se lee del flujo
     */
    public int readBit() throws IOException {
        if (currentByte == -1) return -1;

        if (numBitsRemaining == 0) {
            currentByte = in.read();
            if (currentByte == -1) return -1;
            numBitsRemaining = 8;
        }

        numBitsRemaining--;
        return (currentByte >> numBitsRemaining) & 1;
    }

    /**
     * Cierra este flujo de bits y libera cualquier recurso del sistema asociado con él.
     *
     * @throws IOException Si ocurre un error al cerrar el flujo
     */
    @Override
    public void close() throws IOException {
        in.close();
    }
}