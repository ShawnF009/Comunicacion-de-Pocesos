import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.IOException;

public class Tuberia {
    public static void main(String[] args) {
        try {
            ProcessBuilder pb1 = new ProcessBuilder("cmd.exe", "/c", "dir");
            ProcessBuilder pb2 = new ProcessBuilder("cmd.exe", "/c", "findstr", "DIR");

            Process p1 = pb1.start();
            Process p2 = pb2.start();

            try (InputStream salidaP1 = p1.getInputStream();
                 OutputStream entradaP2 = p2.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesLeidos;
                try {
                    while ((bytesLeidos = salidaP1.read(buffer)) != -1) {
                        entradaP2.write(buffer, 0, bytesLeidos);
                    }
                    entradaP2.flush();
                } catch (IOException e) {
                }
            }
            p1.waitFor();
            try (BufferedReader lectorP2 = new BufferedReader(new InputStreamReader(p2.getInputStream()))) {
                String linea;
                while ((linea = lectorP2.readLine()) != null) {
                    System.out.println(linea);
                }
            }
            p2.waitFor();
            System.out.println("Finalizada la ejecución");

        } catch (IOException | InterruptedException e) {
            System.err.println("Error durante la ejecución: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}