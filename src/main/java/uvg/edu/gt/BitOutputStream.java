package uvg.edu.gt;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Closeable;

/**.
 * Esta clase envuelve un OutputStream existente y proporciona capacidades de escritura a nivel de bit.
 */
public class BitOutputStream implements Closeable {
    private OutputStream out;
    private int currentByte = 0;
    private int numBitsFilled = 0;

    /**
     * Construye un nuevo BitOutputStream que envuelve el OutputStream especificado.
     *
     * @param out El flujo de salida al cual escribir bytes
     */
    public BitOutputStream(OutputStream out) {
        this.out = out;
    }

    /**
     * Escribe un solo bit al flujo de salida.
     * Los bits se acumulan hasta formar un byte completo (8 bits) antes de escribirse.
     *
     * @param bit El valor del bit a escribir
     * @throws IOException Si ocurre un error mientras se escribe al flujo
     */
    public void writeBit(int bit) throws IOException {
        currentByte = (currentByte << 1) | bit;
        numBitsFilled++;
        if (numBitsFilled == 8) {
            out.write(currentByte);
            numBitsFilled = 0;
        }
    }

    /**
     * Cierra este flujo de bits y libera cualquier recurso del sistema asociado con él.
     * Si hay bits pendientes que no completan un byte, se realiza un relleno
     *
     * @throws IOException Si ocurre un error al cerrar el flujo
     */
    @Override
    public void close() throws IOException {
        if (numBitsFilled > 0) {
            currentByte <<= (8 - numBitsFilled);
            out.write(currentByte);
        }
        out.close();
    }
}