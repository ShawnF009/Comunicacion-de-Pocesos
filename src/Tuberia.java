import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Tuberia {
    public static void main(String[] args) {
        try {
            ProcessBuilder pb1 = new ProcessBuilder("cmd.exe", "/c", "dir");
            ProcessBuilder pb2 = new ProcessBuilder("cmd.exe", "/c", "findstr", ".java");

            List<Process> procesos = ProcessBuilder.startPipeline(List.of(pb1, pb2));
            Process procesoFinal = procesos.get(procesos.size() - 1);

            try (BufferedReader lector = new BufferedReader(new InputStreamReader(procesoFinal.getInputStream()))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    System.out.println(linea);
                }
            }
            procesoFinal.waitFor();
            System.out.println("Finalizada la ejecución");

        } catch (Exception e) {
            System.err.println("Se ha producido un error durante la ejecución de la tubería: " + e.getMessage());
        }
    }
}