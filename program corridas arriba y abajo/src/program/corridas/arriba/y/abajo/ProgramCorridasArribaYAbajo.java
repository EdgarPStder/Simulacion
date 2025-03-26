package program.corridas.arriba.y.abajo;
import java.util.Scanner;
public class ProgramCorridasArribaYAbajo {
    public static void main(String[] args) {
       

        // Ejercicio 4
        Scanner scanner = new Scanner(System.in);

        // Pedir los valores al usuario para la generacion congruencial
        System.out.println("Cantidad de veces quieres que se ejecute: ");
        System.out.print("Ingrese cuÃ¡ntos numeros desea generar: ");
        int n = scanner.nextInt(); // Numero de valores a generar

        System.out.println("Semilla");
        System.out.print("Ingrese el valor de x: ");
        int x = scanner.nextInt();

        System.out.println("Constante de multiplicacion");
        System.out.print("Ingrese el valor de a: ");
        int a = scanner.nextInt();

        System.out.println("Constante aditiva");
        System.out.print("Ingrese el valor de c: ");
        int c = scanner.nextInt();

        System.out.println("Modulo");
        System.out.print("Ingrese el valor de m: ");
        int m = scanner.nextInt();

        System.out.println("\nNÃºmeros generados por el metodo congruencial:");
        // GeneraciÃ³n de nÃºmeros congruenciales y redondeo
        double[] r = new double[n];  // Arreglo para almacenar los nÃºmeros generados

        for (int i = 0; i < n; i++) {
            x = (a * x + c) % m; // Metodo congruencial lineal
            double resultado = (double) x / (m - 1); // Normalizar el valor
            r[i] = Double.parseDouble(String.format("%.4f", resultado));  // Redondear y almacenar
            System.out.println("x = " + x + "      " + "r = " + r[i]);
        }

        // Ingreso del nÃºmero maximo de corridas permitidas
        System.out.print("\nIngrese el nÃºmero mÃ¡ximo de corridas permitidas: ");
        int maxCorridas = scanner.nextInt();

        // Convertir en secuencia de 1s y 0s
        int[] S = new int[n - 1];  // Secuencia de 1s y 0s
        for (int i = 0; i < n - 1; i++) {
            if (r[i] < r[i + 1]) {
                S[i] = 1;  // Ascendente
            } else {
                S[i] = 0;  // Descendente
            }
        }

        // Contamos las corridas (cambios entre 1s y 0s)
        int C = 1;  // Contador de corridas (inicia con 1 porque al menos hay una corrida)
        for (int i = 1; i < S.length; i++) {
            if (S[i] != S[i - 1]) {
                C++;  // Se incrementa la corrida cuando hay un cambio entre 1 y 0
            }
        }

        // Calcular valor esperado y realizar prueba de hipotesis
        double p = 0.5;  // Probabilidad de ascender o descender
        double esperado = (2.0 * (n - 1) * p * (1 - p)) / (1 + 1);
        double varianza = (2.0 * (n - 1) * p * (1 - p)) / ((1 + 1) * (1 + 1));
        double z = (C - esperado) / Math.sqrt(varianza);

        // Nivel de aceptacion 95% (z critico para 95% es 1.96)
        boolean aceptable = Math.abs(z) <= 1.96;

        // Imprimir resultados
        System.out.println("\nSecuencia de numeros generados (r): ");
        for (int i = 0; i < n; i++) {
            System.out.print(r[i] + " ");
        }
        System.out.println();

        System.out.println("\nSecuencia de 1s y 0s (S): ");
        for (int i = 0; i < n - 1; i++) {
            System.out.print(S[i] + " ");
        }
        System.out.println();

        System.out.println("\nNumero de corridas (C): " + C);
        System.out.println("Valor esperado de corridas: " + esperado);
        System.out.println("Valor calculado de z: " + z);

        // Verificar si el valor z estÃ¡ dentro del intervalo de aceptaciÃ³n
        if (aceptable) {
            System.out.println("\nLa secuencia es aceptable con un nivel de confianza del 95%.");
        } else {
            System.out.println("\nLa secuencia no es aceptable con un nivel de confianza del 95%.");
        }

        // Verificar si el nÃºmero de corridas estÃ¡ dentro del lÃ­mite establecido
        if (C <= maxCorridas) {
            System.out.println("El numero de corridas esta dentro del limite permitido.");
        } else {
            System.out.println("El numero de corridas excede el limite permitido.");
        }

        scanner.close();
    }
}

    
    

