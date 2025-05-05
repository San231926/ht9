package uvg.edu.gt;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n----- COMPRESOR / DESCOMPRESOR HUFFMAN -----");
            System.out.println("1. Comprimir archivo");
            System.out.println("2. Descomprimir archivo");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Intente de nuevo.");
                continue;
            }

            try {
                if (opcion == 1) {
                    System.out.print("Ruta del archivo .txt a comprimir (Ejemplo: prueba.txt): ");
                    String inputTxt = scanner.nextLine();
                    System.out.print("Nombre para el archivo .huff (Ejemplo: salida.huff): ");
                    String outputHuff = scanner.nextLine();
                    System.out.print("Nombre para el archivo .hufftree (Ejemplo: salida.hufftree): ");
                    String outputTree = scanner.nextLine();

                    HuffmanCompressor.compress(inputTxt, outputHuff, outputTree);
                    System.out.println("Archivo comprimido exitosamente.");

                } else if (opcion == 2) {
                    System.out.print("Ruta del archivo .huff (Ejemplo: salida.huff): ");
                    String inputHuff = scanner.nextLine();
                    System.out.print("Ruta del archivo .hufftree (Ejemplo: salida.hufftree): ");
                    String inputTree = scanner.nextLine();
                    System.out.print("Nombre para el archivo de salida (.txt, Ejemplo: pruebaDecoded.txt)) : ");
                    String outputTxt = scanner.nextLine();

                    HuffmanDecompressor.decompress(inputHuff, inputTree, outputTxt);
                    System.out.println("Archivo descomprimido exitosamente.");

                } else if (opcion == 3) {
                    System.out.println("Saliendo del programa");
                    break;
                } else {
                    System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}