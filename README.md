# Simulacón de evacuación de una sala de cine ante emergencias por modelo basado en agentes (MBA)

A fin de simular la evacuación de una sala de cine, se realiza el presente proyecto el cual cuenta con las siguientes funcionalidades:
* Generación automática de simulaciones con 3 experimentos diferentes.
  * Sala con 2 puertas.
  * Sala con 4 puertas.
  * Sala con 4 puertas y las personas no colisionan entre si.
* Persistencia de las simulaciones en una Base de datos.
* Graficación estadística de los resultados obtenidos.

## Tecnologías utilizadas

* Motor de simulacion: NetLogo
* Ejecución de las simulaciones: Java
* Base de Datos: PostgreSQL
* Gráficos: Python/Matplotlib

## Instrucciones de uso

1. Iniciar la Base de Datos.
2. Ejecutar el proyecto Java.
3. Para la graficación, ejecutar `/scripts/main.py`.

## Documentación recomendada

* [Netlogo Getting Started](https://ccl.northwestern.edu/netlogo/bind/article/getting-started-with-netlogo.html)
* [Programacion con NetLogo](https://ccl.northwestern.edu/netlogo/docs/programming.html)
* [NetLogo Controlling API](https://github.com/NetLogo/NetLogo/wiki/Controlling-API)
* [Matplotlib](https://matplotlib.org/stable/tutorials/introductory/quick_start.html#sphx-glr-tutorials-introductory-quick-start-py)