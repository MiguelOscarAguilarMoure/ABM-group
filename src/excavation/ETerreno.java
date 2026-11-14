package excavation;

import java.util.Random;

public enum ETerreno {
    TIERRA('.', 1, 1, null),
    PIEDRA('^', 1, 1, TIERRA),
    ARENA('~', 1, 2, null),
    AGUA('-', 2, 2, null);

    private static final Random RANDOM = new Random();

    private final char simbolo;
    private final int costeMinimo;
    private final int costeMaximo;
    private final ETerreno siguienteNivel;

    ETerreno(char simbolo, int costeMinimo, int costeMaximo, ETerreno siguienteNivel) {
        if (costeMinimo < 1 || costeMaximo < costeMinimo) {
            throw new IllegalArgumentException(
                    "Rango de coste inválido para el terreno " + simbolo + ": [" + costeMinimo + ", " + costeMaximo
                            + "]");
        }
        this.simbolo = simbolo;
        this.costeMinimo = costeMinimo;
        this.costeMaximo = costeMaximo;
        this.siguienteNivel = siguienteNivel;
    }

    public char getSimbolo() {
        return simbolo;
    }

    public int getCosteMinimo() {
        return costeMinimo;
    }

    public int getCosteMaximo() {
        return costeMaximo;
    }

    public ETerreno getSiguienteNivel() {
        return siguienteNivel;
    }

    public boolean tieneSiguienteNivel() {
        return siguienteNivel != null;
    }

    public int calcularCoste() {
        if (costeMinimo == costeMaximo) {
            return costeMinimo;
        }
        return RANDOM.nextInt(costeMaximo - costeMinimo + 1) + costeMinimo;
    }

    public static ETerreno obtenerPorSimbolo(char simbolo) {
        ETerreno[] terrenos = ETerreno.values();
        for (int i = 0; i < terrenos.length; i++) {
            if (terrenos[i].getSimbolo() == simbolo) {
                return terrenos[i];
            }
        }
        throw new IllegalArgumentException("No existe ningún tipo de terreno con el símbolo: " + simbolo);
    }
}