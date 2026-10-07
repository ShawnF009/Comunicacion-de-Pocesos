import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Escritor {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String linea = reader.readLine();
            if (linea != null && !linea.trim().isEmpty()) {
                int numero = Integer.parseInt(linea.trim());

                for (int i = 1; i <= 10; i++) {
                    System.out.println(numero + i);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error en el proceso Escritor: " + e.getMessage());
        }
    }
}