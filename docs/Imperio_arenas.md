# ARCHAIOS

## Imperios de las arenas

**Autor:** Adrián Fernández González

## Índice

| Sección | Página |
| :--- | :--- |
| Introducción | 2 |
| Añadidos | 2 |
| Terrenos difíciles | 2 |
| Piedra | 2 |
| Arena | 3 |
| Agua | 3 |
| Regiones | 3 |
| Egipto [EGP] | 3 |
| Petra [PTR] | 3 |
| Sumeria [SUM] | 3 |
| Habilidades | 3 |
| Especialización en desierto | 3 |
| Especialista en demoliciones A | 3 |

# Archaios. Imperios de las arenas

## Introducción

Esta es una expansión del sistema base de Archaios, añadiendo nuevos tesoros basados en imperios de lugares desérticos, terrenos especiales y nuevas habilidades para esos terrenos.

Antes de empezar, asegúrate de tener implementada la parte de los terrenos difíciles de la segunda entrega.

Debe respetarse el nombre de todo lo estipulado, tanto clases como paquetes.

Lee detenidamente este documento antes de empezar. Crear un diagrama en papel ayuda a visualizar las cosas.

Algunos métodos de las clases aquí descritas pueden necesitar la implementación de métodos adicionales en otras clases que no aparecen reflejados en este documento.

Pueden crearse tantas clases y métodos adicionales como sea necesario, tanto dentro como fuera de los paquetes y clases aquí descritos.

Algunos datos se describen superficialmente, ya que su implementación puede realizarse de diversas formas y queda a tu disposición escoger la forma más oportuna. Ten en cuenta que, durante la corrección, has de justificar tu elección de forma razonable.

Aunque se describa un proceso en una sección determinada, su implementación puede ser realizada en otro lugar.

## Añadidos

* Tres nuevas regiones: Egipto, Petra y Sumeria.
* Una nueva licencia para acceder a cada uno de los territorios.
* Terrenos principalmente arenosos.
* Nuevas habilidades, incluyendo las que facilitan la excavación en estas regiones.

## Terrenos difíciles

Esta expansión añade tres terrenos difíciles, cada uno con sus características.

### Piedra

* **Símbolo:** `^`
* **Coste:** `1`
* **Siguiente nivel:** Tierra (` `)

### Arena

* **Símbolo:** `~`
* **Coste:** `1-2`

### Agua

* **Símbolo:** `-`
* **Coste:** `2`

## Regiones

Como se ha mencionado antes, las tres nuevas regiones son Egipto, Petra y Sumeria. Cada una tiene una proporción diferente de tipo de terreno.

### Egipto [EGP]

Siguiendo el curso del río Nilo, el gran imperio de la antigüedad que es Egipto se extiende por varios kilómetros entre tierras fértiles y de pura arena del desierto.

Los terrenos de Egipto son **10% agua**, **35% tierra** y **55% arena**.

### Petra [PTR]

Petra, la capital de los nabateos, se esconde entre las montañas, parcialmente excavada en la piedra, construida con esta y tapada por la arena.

Los terrenos de Petra se dividen en **75% piedra** y **25% arena**.

### Sumeria [SUM]

Con una extensión de varios kilómetros, Sumeria se encuentra entre el Tigris y el Éufrates, parte de lo que más tarde sería Mesopotamia. Creada con ladrillos en lo que antes fue terreno fértil y lecho rocoso, ahora ha sido reclamado por las arenas del desierto que lo rodean.

Los terrenos de Sumeria son **30% piedra**, **50% arena** y **20% tierra**.

## Habilidades

Se añaden dos nuevas habilidades de ayuda en terreno.

### Especialización en desierto

Estos arqueólogos están acostumbrados a la arena, por lo que su coste de excavación se fija en **1**.

Esta habilidad es incompatible con cualquier otra de especialización en un terreno.

### Especialista en demoliciones A

Estos arqueólogos están acostumbrados a tener que quitar las piedras, por lo que rompen hasta **5 piedras aleatorias** antes de empezar la excavación, pasando sus casillas a tierra.

Esto se realiza antes del proceso de limpieza y de escaneo.

Otras expansiones pueden añadir más especialistas en demoliciones, cada una con su propia letra, siendo todas compatibles entre sí.