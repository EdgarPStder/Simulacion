# Simulación — Generadores y pruebas de números pseudoaleatorios

![Java](https://img.shields.io/badge/Java-007396?style=flat-square&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/GUI-Swing-5382A1?style=flat-square)
![Python](https://img.shields.io/badge/Python-3776AB?style=flat-square&logo=python&logoColor=white)
![NumPy](https://img.shields.io/badge/NumPy-013243?style=flat-square&logo=numpy&logoColor=white)

Implementaciones de la materia **Simulación** (Ingeniería en Sistemas Computacionales, UAT — UAM Mante): los principales **generadores de números pseudoaleatorios**, las **pruebas estadísticas** que verifican su calidad y una **simulación Monte Carlo** que los aplica a un problema real.

La mayoría de los generadores tiene interfaz gráfica en Java Swing: se capturan la semilla y los parámetros, y la tabla muestra cada iteración paso a paso.

---

## Contenido

### Generadores de números pseudoaleatorios

| Proyecto | Método | Interfaz |
|---|---|---|
| [`CuadradosMedios`](CuadradosMedios/src/Main.java) | Cuadrados medios (von Neumann) | Swing |
| [`ProductosMedios`](ProductosMedios/src/Main.java) | Productos medios (dos semillas) | Swing |
| [`MultiplicadorConstante`](MultiplicadorConstante/src/Main.java) | Multiplicador constante | Swing |
| [`AlgoritmoLineal`](AlgoritmoLineal/src/Main.java) | Congruencial lineal: `Xₙ₊₁ = (a·Xₙ + c) mod m` | Swing |
| [`CongruencialMultiplicativo`](CongruencialMultiplicativo/src/Main.java) | Congruencial multiplicativo: `Xₙ₊₁ = (a·Xₙ) mod m` | Swing |
| [`PromedioRangos`](PromedioRangos/src/Main.java) | Generación por secuencias con rangos y promedios | Swing |

### Pruebas estadísticas

| Proyecto | Prueba | Qué verifica | Interfaz |
|---|---|---|---|
| [`Chi-Cuadrada`](Chi-Cuadrada/src/Main.java) | χ² con m = √n intervalos, 95 % de confianza | Uniformidad | Consola |
| [`Kolmogorov-Smirnov`](Kolmogorov-Smirnov/src/Main.java) | K-S con nivel de confianza seleccionable | Uniformidad | Swing |
| [`Poker`](Poker/src/Main.java) | Póker sobre 5 dígitos decimales | Independencia | Consola |
| [`CorridasArribaAbajo`](CorridasArribaAbajo/src/program/corridas/arriba/y/abajo/ProgramCorridasArribaYAbajo.java) | Corridas ascendentes y descendentes (estadístico z, 95 %) | Independencia | Consola |

### Aplicación

| Proyecto | Descripción |
|---|---|
| [`Montecarlo`](Montecarlo/montecarlo.py) | Simula la evolución de un portafolio de inversión con retornos generados por un congruencial lineal, y reporta el mejor y el peor escenario al final del periodo |

---

## Cómo ejecutar

**Requisitos:** JDK 17 o superior. Para Monte Carlo: Python 3.10+ y NumPy.

Cada carpeta es un proyecto independiente de IntelliJ IDEA (`.iml`), salvo `CorridasArribaAbajo`, que es de NetBeans. Puedes abrirlos en su IDE o compilarlos desde la terminal:

```bash
# Proyectos con Main.java (por ejemplo, el congruencial lineal)
javac -d out AlgoritmoLineal/src/Main.java
java -cp out Main

# Prueba de corridas (tiene paquete)
javac -d out CorridasArribaAbajo/src/program/corridas/arriba/y/abajo/ProgramCorridasArribaYAbajo.java
java -cp out program.corridas.arriba.y.abajo.ProgramCorridasArribaYAbajo

# Simulación Monte Carlo
pip install numpy
python Montecarlo/montecarlo.py
```

Las carpetas `out/` y `build/` se generan al compilar y están en `.gitignore`.

---

## Autor

**Edgar Gael Pesina Santander** — Ingeniería en Sistemas Computacionales, Universidad Autónoma de Tamaulipas.
GitHub: [@EdgarPStder](https://github.com/EdgarPStder)

## Licencia

Código bajo licencia [MIT](LICENSE).
