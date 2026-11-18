# Requisitos

## Clase de terrenos (Enum)

Símbolo asociado, coste mínimo y máximo, siguiente nivel.

|Tipo de terreno|Símbolo|Cose|
|---------------|-------|----|
|Tierra|`.`|1|
|Piedra|`^`|1-2|
|Agua|`~`|2|

## Clase de registros

Un metodo para apuntar tesoro y otro para apuntare una excepcion con su pila (stacktrace)

## Tareas

Puntos requisitos de rejilla y tesoros

* Enum de terrenos con símbolo, coste (fijo o rango) y siguiente nivel.
* Rejilla con una capa de terreno por casilla, aparte de lo que ve el jugador.
* Rellenar la rejilla según las proporciones de la región; las básicas, todo tierra.
* Cobrar las acciones según el coste del terreno, pidiendo el coste al arqueólogo.
* Niveles: la piedra pasa a tierra y hay que volver a excavar esa casilla.
* Llamar a las habilidades que cambian terreno (demoliciones) antes de la limpieza y el
escaneo.
* Registro de tesoros: crear `log/register/<partida>.log` al crear la partida con
`[aaaa-mm-dd hh:mm:ss] Inicio de la partida`. y añadir una línea por tesoro
encontrado.
* Registro de errores: `log/errors.log` compartido, con la fecha, la descripción y la pilade cada excepción.
