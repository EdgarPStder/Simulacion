import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese los números pseudoaleatorios separados por comas:");
        String input = scanner.nextLine();

        // Convertimos la entrada en una lista de números
        String[] valores = input.split(",");
        List<String> digitos = new ArrayList<>();

        for (String valor : valores) {
            valor = valor.trim();
            if (valor.contains(".")) {
                String[] partes = valor.split("\\.");
                if (partes.length > 1 && partes[1].length() >= 5) {
                    digitos.add(partes[1].substring(0, 5)); // Tomamos 5 dígitos
                }
            }
        }

        // Contadores para cada categoría
        int[] frecuencias = new int[7]; // 7 tipos de combinaciones

        for (String num : digitos) {
            int[] count = new int[10]; // Para contar la frecuencia de cada dígito (0-9)
            for (char c : num.toCharArray()) {
                count[c - '0']++;
            }

            // Contar ocurrencias de cada dígito
            List<Integer> ocurrencias = new ArrayList<>();
            for (int c : count) {
                if (c > 0) ocurrencias.add(c);
            }
            Collections.sort(ocurrencias, Collections.reverseOrder());

            // Clasificación de manos de póker
            if (ocurrencias.equals(Arrays.asList(5))) frecuencias[6]++; // Quintilla (AAAAA)
            else if (ocurrencias.equals(Arrays.asList(4, 1))) frecuencias[5]++; // Póker (AAAAB)
            else if (ocurrencias.equals(Arrays.asList(3, 2))) frecuencias[4]++; // Full House (AABBB)
            else if (ocurrencias.equals(Arrays.asList(3, 1, 1))) frecuencias[3]++; // Tercia (AAABC)
            else if (ocurrencias.equals(Arrays.asList(2, 2, 1))) frecuencias[2]++; // Dos Pares (AABBC)
            else if (ocurrencias.equals(Arrays.asList(2, 1, 1, 1))) frecuencias[1]++; // Un Par (AABCD)
            else frecuencias[0]++; // Todos diferentes (ABCDE)
        }

        // Frecuencias esperadas según teoría de probabilidad
        int n = digitos.size();
        double[] esperadas = {
                0.3024 * n, // Todos diferentes (ABCDE)
                0.5040 * n, // Un Par (AABCD)
                0.1080 * n, // Dos Pares (AABBC)
                0.0720 * n, // Tercia (AAABC)
                0.0090 * n, // Full House (AABBB)
                0.0045 * n, // Póker (AAAAB)
                0.0001 * n  // Quintilla (AAAAA)
        };

        // Cálculo del estadístico X^2
        double chiCuadrado = 0;
        for (int i = 0; i < 7; i++) {
            chiCuadrado += Math.pow(frecuencias[i] - esperadas[i], 2) / esperadas[i];
        }

        // Valor crítico de chi-cuadrado con 6 grados de libertad y 95% de confianza
        double chiCritico = 12.59;

        // Resultados
        System.out.println("\nCategoría | Observado (Oi) | Esperado (Ei)");
        String[] categorias = {"ABCDE", "AABCD", "AABBC", "AAABC", "AABBB", "AAAAB", "AAAAA"};
        for (int i = 0; i < 7; i++) {
            System.out.printf("%-7s\t  | %12d   | %12.4f\n", categorias[i], frecuencias[i], esperadas[i]);
        }

        System.out.println("Todos diferentes (ABCDE)\nUn Par (AABCD)\nDos Pares (AABBC)\nTercia (AAABC)\nFull House (AABBB)\nPóker (AAAAB)\nQuintilla (AAAAA))");

        System.out.printf("\nEstadístico X^2: %.4f\n", chiCuadrado);
        System.out.printf("Valor crítico de X^2: %.2f\n", chiCritico);
        if (chiCuadrado < chiCritico) {
            System.out.println("No se rechaza la hipótesis de que los números siguen una distribución uniforme.");
        } else {
            System.out.println("Se rechaza la hipótesis de que los números siguen una distribución uniforme.");
        }
    }
}
