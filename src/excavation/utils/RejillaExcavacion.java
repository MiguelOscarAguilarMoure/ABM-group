package excavation.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import excavation.ETerreno;
import excavation.Position;
import treasures.Treasure;

/**
 * Encapsula el tablero bidimensional de la excavación: etiquetas de coordenadas,
 * capa lógica de terrenos, estado de resolución de cada casilla y representación visual.
 */
public class RejillaExcavacion {

    public static final String VACIO = "x";
    public static final String SUPOSICION = "?";

    private static final char[] LETRAS = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private final int numColumnas;
    private final int numFilas;
    private final List<String> columnas;
    private final List<String> casillas;
    private final List<ETerreno> terrenos;
    private final boolean[] excavadas;
    private final String cabecera;
    private final int anchoEtiqueta;
    private final int anchoNumFila;
    private int cavarRestantes;

    /**
     * Construye una rejilla con las dimensiones y la capa inicial de terrenos indicadas.
     *
     * @param numColumnas número de columnas del tablero.
     * @param numFilas    número de filas del tablero.
     * @param capaInicial lista de terrenos con longitud {@code numColumnas * numFilas}.
     * @throws IllegalArgumentException si las dimensiones no son válidas o la capa no coincide.
     */
    public RejillaExcavacion(int numColumnas, int numFilas, List<ETerreno> capaInicial) {
        if (numColumnas <= 0 || numFilas <= 0) {
            throw new IllegalArgumentException("Las dimensiones de la rejilla deben ser mayores que cero.");
        }
        int total = numColumnas * numFilas;
        if (capaInicial == null || capaInicial.size() != total) {
            throw new IllegalArgumentException("La capa de terrenos no coincide con el tamaño de la rejilla.");
        }

        this.numColumnas = numColumnas;
        this.numFilas = numFilas;
        this.columnas = new ArrayList<>();
        this.casillas = new ArrayList<>(total);
        this.terrenos = new ArrayList<>(total);
        this.excavadas = new boolean[total];
        this.cavarRestantes = total;

        for (int i = 0; i < capaInicial.size(); i++) {
            ETerreno tipo = capaInicial.get(i);
            if (tipo == null) {
                throw new IllegalArgumentException("La capa inicial no puede contener terrenos nulos.");
            }
            this.terrenos.add(tipo);
            this.casillas.add(tipo.getSimbolo());
        }

        this.anchoEtiqueta = calcularAnchoEtiqueta(numColumnas);
        generarEtiquetas("");
        this.anchoNumFila = String.valueOf(numFilas).length();
        this.cabecera = construirCabecera();
    }

    public int getTotalCasillas() {
        return excavadas.length;
    }

    public int getCavarRestantes() {
        return cavarRestantes;
    }

    /**
     * Convierte una posición (fila, columna) en su índice lineal interno.
     *
     * @param posicion coordenada a traducir.
     * @return índice dentro de la rejilla.
     * @throws IllegalArgumentException si la posición es nula o está fuera de rango.
     */
    public int getIndice(Position posicion) {
        if (posicion == null) {
            throw new IllegalArgumentException("La posición no puede ser nula.");
        }
        int fila = posicion.getFil();
        int columna = columnas.indexOf(posicion.getCol());
        if (fila < 1 || fila > numFilas) {
            throw new IllegalArgumentException("La fila " + fila + " no existe (1-" + numFilas + ").");
        }
        if (columna < 0) {
            throw new IllegalArgumentException("La columna " + posicion.getCol() + " no existe ("
                    + columnas.get(0) + "-" + columnas.get(numColumnas - 1) + ").");
        }
        return (fila - 1) * numColumnas + columna;
    }

    public boolean estaExcavada(int indice) {
        validarIndice(indice);
        return excavadas[indice];
    }

    public ETerreno getTerreno(int indice) {
        validarIndice(indice);
        return terrenos.get(indice);
    }

    public ETerreno getTerreno(Position posicion) {
        return terrenos.get(getIndice(posicion));
    }

    public int contarTerreno(ETerreno buscado) {
        if (buscado == null) {
            throw new IllegalArgumentException("El terreno buscado no puede ser nulo.");
        }
        int cantidad = 0;
        for (int i = 0; i < terrenos.size(); i++) {
            if (terrenos.get(i) == buscado) {
                cantidad++;
            }
        }
        return cantidad;
    }

    /**
     * Reduce el nivel de terreno de una casilla (por ejemplo, de piedra a tierra).
     * Si estaba marcada con suposición ('?'), conserva la marca visual.
     *
     * @param indice    índice de la casilla.
     * @param siguiente terreno al que transiciona.
     */
    public void bajarNivel(int indice, ETerreno siguiente) {
        validarIndice(indice);
        if (siguiente == null) {
            throw new IllegalArgumentException("El terreno siguiente no puede ser nulo.");
        }
        terrenos.set(indice, siguiente);
        if (!casillas.get(indice).equals(SUPOSICION)) {
            casillas.set(indice, siguiente.getSimbolo());
        }
    }

    /**
     * Marca una casilla como completamente excavada y actualiza su símbolo visible.
     *
     * @param indice       índice de la casilla.
     * @param simboloFinal símbolo del tesoro encontrado o {@link #VACIO}.
     */
    public void resolverCasilla(int indice, String simboloFinal) {
        validarIndice(indice);
        if (simboloFinal == null) {
            throw new IllegalArgumentException("El símbolo final no puede ser nulo.");
        }
        if (!excavadas[indice]) {
            excavadas[indice] = true;
            cavarRestantes--;
        }
        casillas.set(indice, simboloFinal);
    }

    /**
     * Marca una casilla sin excavar con el indicador de suposición ('?').
     *
     * @param indice índice de la casilla.
     */
    public void marcarSuposicion(int indice) {
        validarIndice(indice);
        if (!excavadas[indice]) {
            casillas.set(indice, SUPOSICION);
        }
    }

    /**
     * Sustituye al azar hasta {@code cantidadMaxima} casillas de un terreno por tierra.
     *
     * @param tipoObjetivo   terreno a sustituir.
     * @param cantidadMaxima límite máximo de casillas a transformar.
     * @param random         generador aleatorio.
     */
    public void cambiarATierra(ETerreno tipoObjetivo, int cantidadMaxima, Random random) {
        if (tipoObjetivo == null || random == null) {
            throw new IllegalArgumentException("El tipo de terreno y el generador aleatorio no pueden ser nulos.");
        }
        if (cantidadMaxima <= 0) {
            return;
        }

        List<Integer> candidatas = new ArrayList<>();
        for (int i = 0; i < terrenos.size(); i++) {
            if (terrenos.get(i) == tipoObjetivo) {
                candidatas.add(i);
            }
        }
        Collections.shuffle(candidatas, random);

        int limite = Math.min(cantidadMaxima, candidatas.size());
        for (int i = 0; i < limite; i++) {
            int indice = candidatas.get(i);
            terrenos.set(indice, ETerreno.TIERRA);
            casillas.set(indice, ETerreno.TIERRA.getSimbolo());
        }
    }

    /**
     * Devuelve el símbolo oculto de una casilla según tenga tesoro o esté vacía.
     *
     * @param indice  índice de la casilla.
     * @param tesoros mapa de tesoros enterrados.
     * @return código de rareza del tesoro o {@link #VACIO}.
     */
    public String obtenerSimboloOculto(int indice, Map<Integer, Treasure> tesoros) {
        validarIndice(indice);
        if (tesoros == null) {
            throw new IllegalArgumentException("El mapa de tesoros no puede ser nulo.");
        }
        Treasure tesoro = tesoros.get(indice);
        return (tesoro == null) ? VACIO : tesoro.getRarezaTesoro().getCode();
    }

    /**
     * Dibuja la rejilla tal como la ve el jugador durante la partida.
     *
     * @return representación textual del tablero visible.
     */
    public String dibujarVisible() {
        return dibujar(casillas);
    }

    /**
     * Dibuja la rejilla con todas sus casillas descubiertas.
     *
     * @param tesoros mapa de tesoros enterrados en el tablero.
     * @return representación textual del tablero revelado.
     */
    public String dibujarDescubierta(Map<Integer, Treasure> tesoros) {
        if (tesoros == null) {
            throw new IllegalArgumentException("El mapa de tesoros no puede ser nulo.");
        }
        List<String> reveladas = new ArrayList<>(casillas.size());
        for (int i = 0; i < casillas.size(); i++) {
            reveladas.add(obtenerSimboloOculto(i, tesoros));
        }
        return dibujar(reveladas);
    }

    private int calcularAnchoEtiqueta(int totalColumnas) {
        int ancho = 1;
        int capacidad = LETRAS.length;
        while (capacidad < totalColumnas) {
            ancho++;
            capacidad *= LETRAS.length;
        }
        return ancho;
    }

    private void generarEtiquetas(String actual) {
        if (actual.length() == anchoEtiqueta) {
            columnas.add(actual);
            return;
        }
        for (int i = 0; i < LETRAS.length && columnas.size() < numColumnas; i++) {
            generarEtiquetas(actual + LETRAS[i]);
        }
    }

    private String construirCabecera() {
        StringBuilder sb = new StringBuilder(espacios(anchoNumFila));
        for (int i = 0; i < columnas.size(); i++) {
            sb.append(' ').append(columnas.get(i));
        }
        return sb.toString();
    }

    private String dibujar(List<String> simbolos) {
        StringBuilder sb = new StringBuilder(cabecera);
        for (int fila = 0; fila < numFilas; fila++) {
            sb.append(System.lineSeparator());
            sb.append(String.format("%" + anchoNumFila + "d", fila + 1));
            for (int col = 0; col < numColumnas; col++) {
                sb.append(' ').append(centrar(simbolos.get(fila * numColumnas + col)));
            }
        }
        return sb.toString();
    }

    private String centrar(String simbolo) {
        int relleno = anchoEtiqueta - simbolo.length();
        int izquierda = relleno / 2;
        return espacios(izquierda) + simbolo + espacios(relleno - izquierda);
    }

    private String espacios(int cantidad) {
        if (cantidad <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(cantidad);
        for (int i = 0; i < cantidad; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }

    private void validarIndice(int indice) {
        if (indice < 0 || indice >= excavadas.length) {
            throw new IllegalArgumentException("Índice de casilla fuera de rango: " + indice);
        }
    }
}