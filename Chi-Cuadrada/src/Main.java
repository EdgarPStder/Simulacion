import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicitar los valores pseudoaleatorios desde el teclado
        System.out.print("Ingrese los valores separados por comas: ");
        String input = scanner.nextLine();
        String[] tokens = input.split(",");
        int n = tokens.length;

        // Convertir los valores a un array de double
        double[] ri = new double[n];
        for (int i = 0; i < n; i++) {
            ri[i] = Double.parseDouble(tokens[i].trim());
        }

        // Calcular el número de intervalos m = sqrt(n)
        int m = (int) Math.sqrt(n);

        // Determinar los intervalos
        double intervalo = 1.0 / m;
        int[] frecuenciasObservadas = new int[m];
        Arrays.fill(frecuenciasObservadas, 0);

        // Clasificar cada número ri en los intervalos
        for (double num : ri) {
            int index = (int) (num / intervalo);
            if (index == m) index--; // Para evitar problemas con 1.0 exacto
            frecuenciasObservadas[index]++;
        }

        // Calcular la frecuencia esperada Ei
        double Ei = (double) n / m;

        // Calcular el estadístico de prueba Chi-Cuadrada
        double chiCuadrado = 0;
        System.out.println("\nTabla de datos:");
        System.out.println("Intervalo\t\tOi\tEi\t\t(Ei - Oi)^2 / Ei");

        for (int i = 0; i < m; i++) {
            double diferencia = Ei - frecuenciasObservadas[i];
            double termino = (diferencia * diferencia) / Ei;
            chiCuadrado += termino;

            System.out.printf("(%.2f - %.2f)\t%d\t%.2f\t%.4f\n",
                    i * intervalo, (i + 1) * intervalo,
                    frecuenciasObservadas[i], Ei, termino);
        }

        // Valor crítico de Chi-Cuadrada con 95% de confianza y m-1 grados de libertad
        double chiCuadradoTablas = getChiSquareCriticalValue(m - 1);

        System.out.printf("\nValor de Chi-Cuadrada calculado: %.4f\n", chiCuadrado);
        System.out.printf("Valor crítico de Chi-Cuadrada (95%% de confianza): %.4f\n", chiCuadradoTablas);

        // Decisión
        if (chiCuadrado < chiCuadradoTablas) {
            System.out.println("No se puede rechazar que los números siguen una distribución uniforme.");
        } else {
            System.out.println("Se rechaza que los números siguen una distribución uniforme.");
        }

        scanner.close();
    }

    // Método para obtener el valor crítico de Chi-Cuadrada (aproximado para 95% de confianza)
    public static double getChiSquareCriticalValue(int degreesOfFreedom) {
        // Valores críticos comunes para alfa = 0.05 (95% confianza)
        double[] chiTable = {3.841, 5.991, 7.815, 9.488, 11.070, 12.592, 14.067, 15.507, 16.919, 18.307};
        if (degreesOfFreedom >= 1 && degreesOfFreedom <= chiTable.length) {
            return chiTable[degreesOfFreedom - 1];
        }
        return 18.307; // Valor por defecto para df >= 10
    }
}
