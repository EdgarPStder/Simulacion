import numpy as np

def generar_numeros_aleatorios(semilla, a, c, m, cantidad):
    numeros_aleatorios = []
    x = semilla
    
    for _ in range(cantidad):
        x = (a * x + c) % m
        r = x / (m - 1)
        numeros_aleatorios.append(r)
    
    return numeros_aleatorios

def simula_portfolio():
    # Función auxiliar para manejar entrada de números decimales con coma o punto
    def input_float(mensaje):
        return float(input(mensaje).replace(',', '.'))
    
    # Pedir los parámetros de la simulación
    inicial = input_float("Ingrese el monto inicial: ")
    retorno_anual = input_float("Ingrese el retorno anual esperado (en decimal, ej. 0.07): ")
    volatilidad_anual = input_float("Ingrese la volatilidad anual (en decimal, ej. 0.15): ")
    tiempo_anios = int(input("Ingrese la cantidad de años: "))
    simulaciones = int(input("Ingrese el número de simulaciones: "))
    
    # Pedir los parámetros del generador de números aleatorios
    semilla = int(input("Ingrese la semilla: "))
    a = int(input("Ingrese el valor de 'a': "))
    c = int(input("Ingrese el valor de 'c': "))
    m = int(input("Ingrese el valor de 'm': "))

    # Generar números aleatorios con el GLC
    cantidad_numeros = tiempo_anios * simulaciones
    numeros_aleatorios = generar_numeros_aleatorios(semilla, a, c, m, cantidad_numeros)
    
    # Convertir a un array de numpy y darle forma (tiempo_anios, simulaciones)
    retornos = np.array(numeros_aleatorios).reshape(tiempo_anios, simulaciones)
    
    # Ajustar los retornos para que tengan la media y desviación estándar deseadas
    retornos = retorno_anual + retornos * volatilidad_anual
    
    valor_portfolio = inicial * (1 + retornos).cumprod(axis=0)

    mejor_caso = valor_portfolio.max(axis=1)[-1]
    peor_caso = valor_portfolio.min(axis=1)[-1]

    return valor_portfolio, mejor_caso, peor_caso

# Ejecutar la simulación
valor_portfolio, mejor_caso, peor_caso = simula_portfolio()

# Mostrar resultados
print(f"Mejor caso al final del período: {mejor_caso:.2f}€")
print(f"Peor caso al final del período: {peor_caso:.2f}€")
