# ARCHAIOS
## Entrega II. Acceso a ficheros

**Autor:** Adrián Fernández González

---

## Índice

| Sección | Página |
| :--- | :---: |
| Introducción | 2 |
| Registros | 2 |
| Tesoros | 2 |
| Errores | 2 |
| Sistema de guardado | 3 |
| Regiones | 3 |
| Carga dinámica | 4 |
| Licencias | 4 |
| Excavación | 4 |
| Terrenos difíciles | 4 |
| Coste | 4 |
| Niveles | 4 |
| Habilidades | 5 |
| Regiones difíciles | 5 |
| Repositorio de regiones | 5 |
| Atlas | 5 |
| Imperio de las arenas | 5 |

---

# Archaios II. Acceso a ficheros

## Introducción

Esta es la primera ampliación del sistema base de Archaios, añadiendo nuevos lugares con sus tesoros, terrenos especiales y nuevas habilidades para esos terrenos.

Es importante corregir los fallos detectados en cada entrega para que las siguientes funcionen correctamente.

Debe respetarse el nombre de todo lo estipulado, tanto clases como paquetes.

Lee detenidamente este documento antes de empezar. Crear un diagrama en papel ayuda a visualizar las cosas.

Algunos métodos de las clases aquí descritas pueden necesitar la implementación de métodos adicionales en otras clases que no aparecen reflejados en este documento.

Pueden crearse tantas clases y métodos adicionales como sea necesario, tanto dentro como fuera de los paquetes y clases aquí descritos.

Algunos datos se describen superficialmente, ya que su implementación puede realizarse de diversas formas y queda a tu disposición escoger la forma más oportuna. Ten en cuenta que, durante la corrección, has de justificar tu elección de forma razonable.

Aunque se describa un proceso en una sección determinada, su implementación puede ser realizada en otro lugar.

---

## Registros

El sistema dispone de dos registros, dos ficheros en los cuales se almacena en formato textual cierta información relevante.

### Tesoros

En el registro de tesoros se introduce una nueva línea cada vez que se consigue un tesoro incluyendo un registro de tiempo y el código del tesoro:

```text
[aaaa-mm-dd hh:mm:ss] RRR_TTT_NNN_PPPP
```

Este registro es individual para cada partida y se guarda en el directorio `log/register` con el nombre de dicha partida y con extensión `.log`.

Cuando la partida se crea, se crea el fichero y se añade una línea indicándolo:

```text
[aaaa-mm-dd hh:mm:ss] Inicio de la partida.
```

### Errores

El registro de errores almacena la descripción de los errores que suceden, las distintas excepciones incluyendo el registro de tiempo:

```text
[aaa-mm-dd hh:mm:ss] Descripción de la excepción.
```

Tras la descripción, se añade la pila de trazabilidad de la misma.

Este registro es único, solo existe uno compartido entre todas las partidas y se guarda en el directorio `log` con el nombre de `errors.log`.

---

## Sistema de guardado

El sistema de guardado se realiza mediante un archivo JSON. Para ello se vuelca toda la información necesaria para ser recuperada.

Han de respetarse exactamente los nombres y tipos de los elementos presentes en el archivo `ejemplo_guardado.json`.

Si se realizan correctamente las clases, el proceso puede ser realizado automáticamente con **GSON** pasando a JSON el objeto `Archaios`. Recuerda que, para establecer un nombre distinto al de la variable a la hora de generar el JSON se puede usar la anotación `@SerializedName(value="nombre")`.

* **`UID`**: es el código del usuario.
* **`Partida`**: es el nombre de la partida.
* **`Arqueólogos`**: la lista de objetos arqueólogo.
  * **`Nombre`**: El nombre del arqueólogo.
  * **`Nv`**: El nivel actual del arqueólogo.
  * **`Exp`**: La experiencia actual del arqueólogo.
  * **`Terrenos`**: La lista de tipos de terreno que puede excavar.
  * **`Habilidades`**: La lista de habilidades de las que dispone.
* **`Estadísticas`**: Las estadísticas del sistema.
  * **`Tesoros`**: Un objeto de códigos de tesoro con la cantidad de veces que se ha encontrado.
  * **`Lugares`**: Un objeto con los códigos de las regiones y la cantidad de veces que se ha excavado allí.

El proceso de guardado se realiza en varias ocasiones:

* Cuando se crea una partida nueva.
* Al contratar un nuevo arqueólogo.
* Al licenciar un arqueólogo.
* Al terminar la excavación.
* Tras la ganancia de experiencia del arqueólogo tras la excavación.

Este último doble guardado se realiza para penalizar al usuario en caso de cerrar el sistema cuando sube de nivel un arqueólogo y no le gustan las habilidades, perdiendo así los puntos de experiencia sin perder los tesoros encontrados, ya que perdería la consistencia entre el sistema y el museo.

---

## Regiones

Ampliando las regiones, se añaden todas las regiones básicas, **Grecia**, **Roma** e **Iberia** y las tres presentes en la expansión **Imperios de las arenas**, descritos en su propio archivo.

---

## Carga dinámica

Para facilitar el proceso de carga de los tesoros y no tener que realizarlo manualmente desde el Excel y ponerlo en el código, se realizará de forma automática desde los archivos JSON facilitados.

Este proceso ha de realizarse antes de inicializar el sistema, en el método `preInit` descrito en la primera entrega.

---

## Licencias

Las licencias son permisos que se solicitan para que un arqueólogo concreto pueda acceder a ciertas regiones, aquellas presentes en las expansiones.

Cada arqueólogo puede tener tantas licencias como quiera, desbloqueando las distintas regiones, pero el coste aumenta con cada una que disponga.

Estas se almacenan guardando el código de la región de la cual dispone de licencia.

Comprar la primera licencia de un arqueólogo cuesta **20 puntos**, las siguientes **10 más** por cada una que tenga.

El menú ha de solicitar el arqueólogo al que comprarle una licencia para luego listar aquellas regiones difíciles para las que no disponga ya de una licencia.

---

## Excavación

Ahora que los arqueólogos son los que definen las regiones a las que ir, en el proceso de excavación, se invierte el orden de los menús: primero se pide arqueólogo y luego la región.

Solo se mostrarán las regiones básicas y aquellas para las que tenga licencia.

---

## Terrenos difíciles

En el sistema base, solo había un tipo de terreno, la tierra, representado por un punto (`.`) con un coste de acciones fijo, `1`. Ahora, se añadirán distintos tipos para los territorios de las nuevas regiones que añaden las expansiones.

Para implementar esto, es necesario modificar la rejilla y el sistema de excavación para añadir la posibilidad de otros terrenos con coste de acción mayores o varios niveles.

### Coste

El coste de los terrenos puede ser **fijo** o **variable**, siendo este último aleatorio entre un rango.

### Niveles

Un terreno que disponga de varios niveles, en vez de pasar a `x` o el tesoro, tendrá otros por el medio.

Por ejemplo, una piedra, al romperla, pasa a tierra, el terreno básico, y será necesario excavar esa posición nuevamente para ver qué hay debajo.

---

## Habilidades

Hay habilidades que pueden reducir el coste de excavar un terreno concreto, por lo que el arqueólogo y las habilidades han de tener un método para que, dado un terreno (el símbolo), diga el coste de excavarlo.

También hay habilidades que pueden cambiar un tipo de terreno determinado por el básico, por lo que hay que preparar también las clases para que, dado un símbolo de un terreno, indique el número de casillas de ese tipo que se cambian.

---

## Regiones difíciles

Aquellas regiones que tengan algún tipo de terreno difícil serán marcadas como tal con una interfaz con los métodos que sean necesarios.

---

## Repositorio de regiones

El repositorio de regiones dispondrá de métodos para obtener las regiones básicas y las difíciles por separado, además de los que ya disponía.

---

## Atlas

El atlas indicará que la región es difícil y a qué expansión pertenece:

```text
Dificil
Región difícil. Imperio de las arenas
```

> **Nota:** Ten en cuenta que en este ejemplo se ha obviado el resto de información que ya muestra.

---

## Imperio de las arenas

Como se ha mencionado antes, se implementará todo lo estipulado en la expansión de **Imperio de las arenas**, incluyendo regiones, habilidades y tipos de terrenos.