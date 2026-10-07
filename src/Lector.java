import org.iesalandalus.programacion.utilidades.Entrada;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class Lector {
    public static void main(String[] args) {
        System.out.print("Introduce un número entero: ");
        int numero = Entrada.entero();

        try {
            String classpath = System.getProperty("java.class.path");
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", classpath, "Escritor");
            pb.directory(new File(System.getProperty("user.dir")));
            Process proceso = pb.start();

            try (BufferedWriter escritorHijo = new BufferedWriter(new OutputStreamWriter(proceso.getOutputStream()))) {
                escritorHijo.write(String.valueOf(numero));
                escritorHijo.newLine();
                escritorHijo.flush();
            }

            System.out.println("\nLos 10 siguientes números generados por el proceso Escritor son:");
            try (BufferedReader lectorHijo = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = lectorHijo.readLine()) != null) {
                    System.out.println(linea);
                }
            }
            proceso.waitFor();

        } catch (IOException | InterruptedException e) {
            System.err.println("Error durante la ejecución del proceso: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}