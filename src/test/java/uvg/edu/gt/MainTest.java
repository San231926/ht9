package uvg.edu.gt;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testCompressFunctionality() throws Exception {
        // Crear un archivo de prueba
        String inputText = "Texto de prueba para compresión.";
        File inputFile = new File("testInput.txt");
        Files.writeString(inputFile.toPath(), inputText);

        // Archivos de salida
        File compressedFile = new File("testOutput.huff");
        File treeFile = new File("testOutput.hufftree");

        try {
            // Llamar a la función de compresión
            HuffmanCompressor.compress(inputFile.getPath(), compressedFile.getPath(), treeFile.getPath());

            // Verificar que los archivos de salida existan
            assertTrue(compressedFile.exists(), "El archivo comprimido no fue creado.");
            assertTrue(treeFile.exists(), "El archivo del árbol no fue creado.");
        } finally {
            // Limpiar archivos de prueba
            inputFile.delete();
            compressedFile.delete();
            treeFile.delete();
        }
    }

    @Test
    void testInvalidInputHandling() {
        // Probar manejo de excepciones con entradas inválidas
        Exception exception = assertThrows(Exception.class, () -> {
            HuffmanCompressor.compress("archivoInexistente.txt", "output.huff", "output.hufftree");
        });
        assertNotNull(exception.getMessage(), "No se lanzó una excepción para un archivo inexistente.");
    }


}