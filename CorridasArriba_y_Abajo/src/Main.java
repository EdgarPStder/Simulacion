import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicitar al usuario la longitud de la secuencia
        System.out.print("Ingrese la longitud de la secuencia (n): ");
        int n = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer del scanner

        // Solicitar la secuencia de números decimales (ingresados por comas)
        System.out.println("Ingrese " + n + " números decimales separados por comas:");
        String input = scanner.nextLine();

        // Dividir la cadena por comas y convertirla en un arreglo de decimales
        String[] partes = input.split(",");
        double[] secuenciaNumeros = new double[n];

        // Convertir los elementos de la cadena a decimales
        for (int i = 0; i < n; i++) {
            secuenciaNumeros[i] = Double.parseDouble(partes[i].trim());  // Usar trim() para eliminar posibles espacios adicionales
        }

        // Construir la secuencia de 1s y 0s
        String secuenciaBinaria = construirSecuenciaBinaria(secuenciaNumeros);
        System.out.println("Secuencia binaria construida: " + secuenciaBinaria);

        // Calcular el número de corridas
        int corridas = contarCorridas(secuenciaBinaria);
        System.out.println("Número de corridas observadas: " + corridas);

        // Calcular el valor esperado de corridas (uc0)
        double uc0 = (2.0 * n - 1) / 3.0;
        System.out.println("Valor esperado de corridas (uc0): " + uc0);

        // Calcular la varianza del número de corridas (O^2co)
        double varianza = (16.0 * n - 29) / 90.0;
        System.out.println("Varianza del número de corridas (O^2co): " + varianza);

        // Calcular el valor del estadístico Z0
        double Z0 = Math.abs(corridas - uc0) / Math.sqrt(varianza);
        System.out.println("Valor del estadístico Z0: " + Z0);

        // Definir el valor crítico Z_alpha/2 para un nivel de significancia de 0.05 (Z = 1.96)
        double Z_critico = 1.96;
        if (Z0 > Z_critico) {
            System.out.println("Conclusión: Los números del conjunto no son independientes.");
        } else {
            System.out.println("Conclusión: No se puede rechazar la hipótesis de que los números son independientes.");
        }
    }

    // Método para construir la secuencia binaria
    public static String construirSecuenciaBinaria(double[] secuenciaNumeros) {
        StringBuilder secuenciaBinaria = new StringBuilder();

        // El primer número siempre tiene valor "1" (no tiene número anterior)
        secuenciaBinaria.append("1");

        // Recorrer la secuencia y comparar cada número con el anterior
        for (int i = 1; i < secuenciaNumeros.length; i++) {
            if (secuenciaNumeros[i] > secuenciaNumeros[i - 1]) {
                secuenciaBinaria.append("1");  // Si el número es mayor, se coloca un "1"
            } else {
                secuenciaBinaria.append("0");  // Si el número es menor o igual, se coloca un "0"
            }
        }

        return secuenciaBinaria.toString();
    }

    // Método para contar el número de corridas
    public static int contarCorridas(String secuencia) {
        int corridas = 1;  // Al menos una corrida siempre existe
        for (int i = 1; i < secuencia.length(); i++) {
            // Si el valor actual es diferente al anterior, hay una nueva corrida
            if (secuencia.charAt(i) != secuencia.charAt(i - 1)) {
                corridas++;
            }
        }
        return corridas;
    }
}
